package com.proyecto.proyectowallet.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity

public class WebSecurityConfig  {
    @Autowired
    private UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    */

   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/register", "/register/**", "/css/**", "/js/**", "/","/api/getUsers").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/**")
                .permitAll()
                .requestMatchers(HttpMethod.PUT).permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                );

        return http.build();
    }
    public void configure(AuthenticationManagerBuilder builder) throws Exception{
       builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

*/

}

