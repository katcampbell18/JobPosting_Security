package com.example.demo.configurations;

import com.example.demo.repositories.UserRepository;
import com.example.demo.service.SSUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


  @Autowired
  SSUserDetailsService ssUserDetailsService;

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return new SSUserDetailsService(userRepository);
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http
            .authorizeRequests()
            .antMatchers("/", "/h2-console/**", "/register", "/css/**", "/js/**")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login").permitAll().permitAll()
            .and()
            .httpBasic();
    http
            .csrf().disable();
    http
            .headers().frameOptions().disable();
  }

  @Bean
  public static BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception
//  {
//    auth
//            .userDetailsService(userDetailsServiceBean()).passwordEncoder(encoder());
//    auth.inMemoryAuthentication().withUser("admin")
//            .password(passwordEncoder().encode("password")).authorities
//            ("ADMIN");
//  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth)throws Exception{
    auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(encoder());
  }

}