package lis.shop.billion.securityConfiguration;

import lis.shop.billion.securityConfiguration.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Конфігураційний клас безпеки для Spring Security.
 *
 * Цей клас налаштовує фільтрацію запитів, автентифікацію, авторизацію,
 * політику створення сесій, CORS та підключення JWT-фільтра.
 *
 * Основні компоненти:
 * - {@link JwtFilter} — фільтр, який перевіряє JWT-токен у запитах.
 * - {@code SecurityFilterChain} — ланцюжок фільтрів безпеки, де вказано, які запити дозволені, а які потребують авторизації.
 * - {@code PasswordEncoder} — кодування паролів з використанням BCrypt.
 * - {@code CorsConfigurationSource} — конфігурація CORS для дозволу запитів з фронтенду.
 *
 * Важливі правила авторизації:
 * - `/api/auth/**`, `/swagger-ui/**` та `/v3/api-docs/**` — відкриті для всіх.
 * - `/api/admin/**` — тільки для користувачів з роллю `ADMIN`.
 * - `/api/users/**` — тільки для користувачів з роллю `CUSTOMER`.
 * - `/api/products/**` — доступні для всіх.
 * - Усі інші запити вимагають автентифікації.
 *
 * Використовується Stateless-політика сесій, тобто жодні сесії на сервері не зберігаються —
 * автентифікація здійснюється виключно через JWT.
 *
 * Закоментовані методи `authenticationManager` і `authenticationProvider` можуть бути використані
 * для явної конфігурації менеджера автентифікації, якщо потрібно.
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasRole("CUSTOMER")
                        .requestMatchers("/api/products/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000")); // Разрешенные источники
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Применяем CORS ко всем путям
        return source;
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
//        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                .authenticationProvider(authenticationProvider)
//                .build();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
}
