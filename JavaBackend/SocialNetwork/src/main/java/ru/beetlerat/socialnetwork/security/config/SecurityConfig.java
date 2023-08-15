package ru.beetlerat.socialnetwork.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.beetlerat.socialnetwork.security.filters.JwtRequestFilter;
import ru.beetlerat.socialnetwork.security.service.SSSecurityUserService;

@EnableWebSecurity
@Configuration
// Разрешить настройку прав доступа через аннотацию @PreAuthorize внутри контроллера
@EnableMethodSecurity
public class SecurityConfig {
    // Доступ к БД
    private final SSSecurityUserService securityUsersService;
    // Фильтр авторизующий запрос по токену
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(SSSecurityUserService securityUsersService, JwtRequestFilter jwtRequestFilter) {
        this.securityUsersService = securityUsersService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Настройка передачи токена в запросе
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests(auth -> {// Лямбда выражение описывающие права доступа к страницам сервиса
                    auth.antMatchers("/api/users").authenticated();
                    auth.anyRequest().permitAll(); // Разрешить всем доступ ко всем запросам
                })
                // Отключаем стандартные сессии
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Если пользователь попытался совершить запрос,
                // на который у него нет прав
                // то сгенерировать исключение UNAUTHORIZED
                .exceptionHandling()
                .authenticationEntryPoint(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                )
                .and()
                // Добавляем созданный фильтр перед фильтром UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Создание и настройка объекта предоставляющего доступ к сервису взаимодействия с хранимыми пользователями
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(securityUsersService);

        return provider;
    }

    // Метод возвращающий шифратор
    // Все шифраторы реализуют интерфейс PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // Метод возвращающий AuthenticationManager
    // AuthenticationManager проверяет корректность данных безопасности
    // Данный боб понадобиться при авторизации
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
