package com.authentifcation.projectpitwo.service;


import com.authentifcation.projectpitwo.configuration.CustomerUserDetailsService;
import com.authentifcation.projectpitwo.configuration.JwtRequestFilter;
import com.authentifcation.projectpitwo.dao.UserDao;
import com.authentifcation.projectpitwo.entities.JwtRequest;
import com.authentifcation.projectpitwo.entities.JwtResponse;

import com.authentifcation.projectpitwo.entities.User;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        try {
            authenticate(userName, userPassword);
        } catch (Exception e) {
            handleFailedLogin(userName);

            throw e; // Re-throw the exception after handling failed login
        }

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userDao.findByUserName(userName).orElse(null);
        return new JwtResponse(user, newGeneratedToken);
    }
   /* public Integer loadUserById(Integer id) {
        Optional<User> user = userDao.findByUserName(jwtRequestFilter.getCurrentid());

        if (user.isPresent()) {
            User existuser = user.get();
           return existuser.getId();
        } else {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
    }*/

   public UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
       User user = userRepository.findById(id)
               .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
       if (user != null) {
           return org.springframework.security.core.userdetails.User.builder()
                   .username(user.getUserName())
                   .password(user.getUserPassword())
                   .authorities(getAuthority(user)) // Assuming authorities are mapped from user roles
                   .build();
       } else {
           throw new UsernameNotFoundException("User not found with id: " + id);
       }
   }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)
            ) {
                private final Integer userId = user.getId();

                public Integer getUserId() {
                    return userId;
                }
            };
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            // Check if the user is banned
            User user = userDao.findByUserName(userName).orElse(null);
            if (user != null && user.isBanned() ) {
                throw new Exception("USER_DISABLED");
            }else {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
            }

            // If the user is not banned, proceed with authentication
           // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS");
        }
    }


    private void handleFailedLogin(String userName) {
        User user = userDao.findByUserName(userName).orElse(null);

        if (user != null) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            userDao.save(user);

            if (user.getFailedLoginAttempts() >= 3) {
                user.setBanned(true);
                userDao.save(user);
            }

        }


        userDao.save(user);

    }

   public void unbanUserAndResetAttempts(String userName) throws Exception {
        // Find the user by username
        Optional<User> optionalUser = userDao.findByUserName(userName);

        // Check if the user exists
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Update the user's banned status and login attempts
            user.setBanned(false);  // Unban the user
            user.setFailedLoginAttempts(0);  // Reset login attempts

            // Save the updated user
            userDao.save(user);
        } else {
            // Handle case when user is not found
            throw new Exception("User with username " + userName + " not found");
        }
    }



}
