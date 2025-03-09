package lis.shop.billion.service;

import lis.shop.billion.controller.dto.UserDto;
import lis.shop.billion.controller.registerDto.RegisterUser;
import lis.shop.billion.entity.Role;
import lis.shop.billion.entity.User;
import lis.shop.billion.entity.UserRoleId;
import lis.shop.billion.entity.UserRoles;
import lis.shop.billion.exception.ResourceNotFoundException;
import lis.shop.billion.repository.RoleRepository;
import lis.shop.billion.repository.UserRepository;
import lis.shop.billion.repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRolesRepository userRolesRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {

        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return user;
    }

    @Transactional
    public User saveUser(RegisterUser registerUser) {
        Optional<User> byEmail = userRepository.findByEmail(registerUser.email());
        if (byEmail.isPresent()) {
            throw new ResourceNotFoundException("User has already been created with email: %s".formatted(registerUser.email()));
        }

        // Создаем нового пользователя
        User user = new User();
        user.setUsername(registerUser.username());
        user.setEmail(registerUser.email());
        user.setPassword(passwordEncoder.encode(registerUser.password()));
        User save = userRepository.save(user);

        // Проверяем, существует ли уже роль ROLE_CUSTOMER
        Role roleCustomer = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_CUSTOMER");
                    return roleRepository.save(newRole); // Сохраняем роль
                });

        // Создаем UserRoles
        UserRoles userRoles = new UserRoles();
        userRoles.setId(new UserRoleId(save.getId(), roleCustomer.getId())); // Используем ID существующей роли
        userRoles.setUser(user);
        userRoles.setRole(roleCustomer);
        userRolesRepository.save(userRoles);

        return save;
    }

    public UserDto getUserDtoByEmail(String email) {
        User userByEmail = findByEmail(email);

        UserDto userDto = new UserDto(
                userByEmail.getId(),
                null,
                null,
                null,
                email,
                userByEmail.getCreatedAt(),
                null);
        return userDto;
    }
}

