package ru.geekbrains.antasyuk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    public void authConfig(AuthenticationManagerBuilder auth,
                           PasswordEncoder passwordEncoder,
                           UserAuthService userAuthService) throws Exception {
        //in memory provaider
        auth.inMemoryAuthentication()
                .withUser("mem_user")
                .password(passwordEncoder.encode("mem_user")) //шифрование пароля в оперативной памяти
                .roles("SUPER_ADMIN")
                .and()
                .withUser("mem_guest")
                .password(passwordEncoder.encode("mem_guest"))
                .roles("GUEST");

        auth.userDetailsService(userAuthService); // auth for users in list
    }

    @Configuration
    public static class UiWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/**/*.css", "/**/*.js").permitAll()
                    .antMatchers("/product/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                    .antMatchers("/user/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                    .antMatchers("/category/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                    .antMatchers("/role/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                    .antMatchers("/access_denied").authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login_processing")
                    .defaultSuccessUrl("/product")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access_denied");
        }
    }
}
