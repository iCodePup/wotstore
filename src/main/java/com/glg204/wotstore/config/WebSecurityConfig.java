package com.glg204.wotstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Configuration commentée de la sécurité.
 * <p>
 * Voir https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter pour mise à jour
 * de la configuration. La classe WebSecurityConfigurerAdapter est dépréciée.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/auth/login").anonymous()
                .requestMatchers(HttpMethod.GET, "/auth/me").authenticated()
                .requestMatchers(HttpMethod.GET, "/thinginstore/available").hasAuthority("ROLE_CLIENT")
                .requestMatchers(HttpMethod.GET, "/thinginstore").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/thinginstore").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/thinginstore/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/thing/type").hasAnyAuthority("ROLE_CLIENT", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/client").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/client/**").hasAuthority("ROLE_CLIENT")
                .requestMatchers(HttpMethod.POST, "/client/**").hasAuthority("ROLE_CLIENT")
                .requestMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll();
        //.anyRequest().permitAll();
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable();
        http.cors().configurationSource(c -> {
            CorsConfiguration corsCfg = new CorsConfiguration();
            corsCfg.applyPermitDefaultValues();
            corsCfg.addAllowedOriginPattern("*");
            corsCfg.addAllowedMethod(CorsConfiguration.ALL);
            return corsCfg;
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
