package com.coderscampus.babytracker.config;

import com.coderscampus.babytracker.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api/**") // Only if you have stateless API endpoints
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/error", "/logout-success", "/api/parent/update-username", "/webjars/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/")
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/dashboard", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout-success")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                );
        return http.build();
    }
}

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/", "/login", "/error", "/api/parent/update-username").permitAll()
//                         .anyRequest().authenticated()
//                 )
//                 .oauth2Login(oauth -> oauth
//                // .loginPage("/")
//                         .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
//                         .failureUrl("/login?error=true")//redirect on error
//                         .defaultSuccessUrl("/dashboard", true)
//                 )
//                 .logout(logout -> logout
//                         .logoutSuccessUrl("/")
//                         .permitAll()
//                 );
//         return http.build();
//     }
// }

//method below will change the behavior of security filter chain to only allow authenticated users to enter into the home pa

                //tell the http object what type of request you would like to authorize
        //old starter login code is below
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/").permitAll();
//                    auth.anyRequest().authenticated();
//                })
//                //below add google login with form
//                .oauth2Login(withDefaults())
//                .formLogin(withDefaults())
//                .build();
//    }
//
//
//}
