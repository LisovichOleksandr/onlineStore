package lis.shop.billion.service;

import lis.shop.billion.controller.dto.UserDto;
import lis.shop.billion.controller.registerDto.RegisterUser;
import lis.shop.billion.entity.*;
import lis.shop.billion.exception.ResourceNotFoundException;
import lis.shop.billion.repository.RoleRepository;
import lis.shop.billion.repository.UserDetailsCustomRepository;
import lis.shop.billion.repository.UserRepository;
import lis.shop.billion.repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Сервіс для управління користувачами (User).
 * Відповідає за:
 * - реєстрацію нових користувачів;
 * - пошук за email або username;
 * - перетворення користувача на DTO;
 * - присвоєння ролі ROLE_CUSTOMER новим користувачам.
 *
 * Всі методи виконуються у межах транзакції (@Transactional).
 */


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    // Репозиторії для доступу до користувачів, ролей і зв’язків між ними
    private final UserRepository userRepository;
    private final UserDetailsCustomRepository userDetailsCustomRepository;
    private final RoleRepository roleRepository;
    private final UserRolesRepository userRolesRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Знайти користувача за email.
     * Якщо користувача не знайдено — кидає ResourceNotFoundException.
     *
     * @param email електронна адреса користувача
     * @return знайдений об’єкт User
     */
    public User findByEmail(String email) {

        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return user;
    }

    /**
     * Зареєструвати нового користувача.
     * Перевіряє, чи email вже зайнятий, зберігає користувача,
     * і призначає йому роль ROLE_CUSTOMER.
     *
     * @param registerUser DTO з даними для реєстрації
     * @return збережений об’єкт User
     */
    @Transactional
    public User saveUser(RegisterUser registerUser) {
        Optional<User> byEmail = userRepository.findByEmail(registerUser.email());
        if (byEmail.isPresent()) {
            throw new ResourceNotFoundException("User has already been created with email: %s".formatted(registerUser.email()));
        }

        // Створюємо нового користувача
        User user = new User();
        user.setUsername(registerUser.username());
        user.setEmail(registerUser.email());
        user.setPassword(passwordEncoder.encode(registerUser.password()));
        User save = userRepository.save(user);

        // Отримуємо або створюємо роль ROLE_CUSTOMER
        Role roleCustomer = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_CUSTOMER");
                    return roleRepository.save(newRole); // Сохраняем роль
                });

        // Створюємо запис зв'язку користувача з роллю
        UserRoles userRoles = new UserRoles();
        userRoles.setId(new UserRoleId(save.getId(), roleCustomer.getId()));
        userRoles.setUser(user);
        userRoles.setRole(roleCustomer);
        userRolesRepository.save(userRoles);

        return save;
    }

    /**
     * Отримати DTO користувача за email.
     * DTO не містить пароля та чутливих даних.
     *
     * @param email email користувача
     * @return об’єкт UserDto
     */
    public UserDto getUserDtoByEmail(String email) {
        User userByEmail = findByEmail(email);
        UserDto defaultData = new UserDto(userByEmail.getId(), userByEmail.getUsername(), null, null,
                null, userByEmail.getEmail(), userByEmail.getCreatedAt(), null);
        Optional<UserDetails> byUserId = this.userDetailsCustomRepository
                .findByUserId(userByEmail.getId());
        if (byUserId.isEmpty()) {
            return defaultData;
        }
        UserDetails userDetails = byUserId.get();
        // TODO Доробити, протестувати,
        // TODO Провірити foreign key в таблиці users
        UserDto userDto = new UserDto(
                userByEmail.getId(),
                userByEmail.getUsername(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getAge(),
                email,
                userByEmail.getCreatedAt(),
                userDetails.getPhotoUrl());
        return userDto;
    }

    @Transactional
    public void saveUserDetails(String email, UserDto userDto) {
        User byEmail = userRepository.findByEmail(email).orElseThrow( () ->
                new UsernameNotFoundException("Користувач з таким емайлом відсутній"));
        UserDetails userDetails = userDetailsCustomRepository.findByUserId(byEmail.getId())
                .orElseGet(() -> UserDetails.builder()
                        .user(byEmail)
                        .build());

        userDetails.setFirstName(userDto.firstName());
        userDetails.setLastName(userDto.lastName());
        userDetails.setAge(userDto.age());

        userDetailsCustomRepository.save(userDetails);
    }

    @Transactional
    public void savePhoto(String email, String fileName) {
        this.userDetailsCustomRepository.savePhotoNameByEmail(email, fileName);
    }

    public String findPhotoNameByEmail(String email) {
        String photoNameByEmail = this.userDetailsCustomRepository.findPhotoNameByEmail(email);
        if (photoNameByEmail == null) {
            throw new RuntimeException("Фото відсутнє");
        }
        return photoNameByEmail;
    }
}

