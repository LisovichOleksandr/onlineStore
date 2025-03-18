package lis.shop.billion.securityConfiguration;

import lis.shop.billion.entity.User;
import lis.shop.billion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реалізація інтерфейсу {@link UserDetailsService}, яка використовується Spring Security
 * для завантаження користувача за email (username).
 *
 * Цей сервіс викликається автоматично під час автентифікації, коли потрібно отримати
 * дані користувача з бази даних.
 *
 * - {@code loadUserByUsername(String username)} шукає користувача в базі даних за email.
 * - Якщо користувач не знайдений — викидається {@link UsernameNotFoundException}.
 * - Якщо користувач знайдений — повертається об'єкт {@link User}, який реалізує {@link UserDetails}.
 *
 * Примітка: клас {@code User} має реалізовувати інтерфейс {@code UserDetails}, щоб Spring міг використовувати його
 * для перевірки прав доступу, ролей і паролів.
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        System.out.println("User from db >> %s".formatted(user));
        return user;
    }
}
