package com.union.design.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@EnableWebSecurity
public class MultiWebSecurityConfig {


    @Bean
    public UserDetailsService userDetailsService() {
        // ensure the passwords are encoded properly

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode).roles("USER").build());
        manager.createUser(users.username("admin").password("password").passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()::encode).roles("USER", "ADMIN").build());
        return manager;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authenticationProvider.supports(UsernamePasswordAuthenticationToken.class);
        return new ProviderManager(authenticationProvider);
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .anyRequest().hasRole("ADMIN")
                    );
        }
    }


    @Configuration
    public static class LoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();
            http.formLogin().disable();
            http.sessionManagement().disable();
            http.logout().disable();

            http.authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/**").authenticated().and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("logout", "POST"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        log.info("{} logout success", userDetails.getUsername());
                    });

        }
    }

}
