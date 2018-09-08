package com.ims.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ims.user.service.UserService;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = UserService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

 @Autowired 
 private UserDetailsService userDetailsService;
 
 @Autowired
 public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {  
	 System.out.println("in WebSecurityConfig --  configAuthentication() ");
	 auth.userDetailsService(userDetailsService);
 } 
 
 @Override
 protected void configure(HttpSecurity http) throws Exception {
	 System.out.println("in WebSecurityConfig --  configure()");
   http.authorizeRequests()
  .antMatchers("/users/addUser.htm").access("hasRole('1')")
  //.antMatchers("/hello","/welcome").access("hasRole('2')")
  .anyRequest().permitAll()
  .and()
    .formLogin().loginPage("/403")
    .usernameParameter("username")
  .and()
    .logout().logoutSuccessUrl("/login?logout") 
   .and()
   .exceptionHandling().accessDeniedPage("/403")
  .and()
    .csrf();
 }
 
 @Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
	 System.out.println("in WebSecurityConfig --  passwordencoder()");
     return new BCryptPasswordEncoder();
    }
}