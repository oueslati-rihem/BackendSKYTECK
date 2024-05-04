 package com.authentifcation.projectpitwo.configuration;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.http.HttpHeaders;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
 import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
 import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

 import org.springframework.security.config.http.SessionCreationPolicy;
 import org.springframework.security.core.userdetails.UserDetailsService;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


 @Configuration
 @EnableWebSecurity
 @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration  {
  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Autowired
  private UserDetailsService jwtService;

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
         return config.getAuthenticationManager();
     }

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http.csrf().disable()
                 .authorizeHttpRequests()
                 .requestMatchers("**","/authenticate", "/registerNewUser","/updateStudents","/forgotPassword","/getUsers","/upload","/image","/updatePassword","/activate/**","/unban/**" , "/activate-account","/userRoleStatistics" ,"/bannedUserStatistics" ,"/userDetail" , "/add/**").permitAll()
                 .and()
                 .authorizeHttpRequests().requestMatchers(HttpHeaders.ALLOW).permitAll()
                 .anyRequest()
                 .authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                 .and()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                 .build();
     }

  @Bean
  public PasswordEncoder passwordEncoder() {
   return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
   authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
  }
}
