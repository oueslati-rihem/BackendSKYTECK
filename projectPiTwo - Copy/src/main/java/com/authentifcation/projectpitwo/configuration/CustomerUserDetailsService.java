package com.authentifcation.projectpitwo.configuration;

import com.authentifcation.projectpitwo.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private Optional<com.authentifcation.projectpitwo.entities.User> userDatails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}", username);
        userDatails = userDao.findByUserName(username);
        if (!Objects.isNull(userDatails)) {
            return new User(userDatails.get().getUserName(), userDatails.get().getUserPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public Optional<com.authentifcation.projectpitwo.entities.User> getUserDatails() {
        return userDatails;
    }
}