package com.authentifcation.projectpitwo.repository;

import com.authentifcation.projectpitwo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    long countByBanned(boolean banned);
}
