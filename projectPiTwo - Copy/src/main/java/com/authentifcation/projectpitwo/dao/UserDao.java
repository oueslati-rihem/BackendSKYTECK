package com.authentifcation.projectpitwo.dao;

import com.authentifcation.projectpitwo.entities.Role;
import com.authentifcation.projectpitwo.entities.User;
import jakarta.persistence.NamedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, String> {
    Optional<User> findByUserName(String userName);


    Optional<User> findById(Integer id);

    // User findByEmailId(@Param("userName") String userName);


    @Query("select u.userName from User u where u.role = 'Admin'")
    List<String> getAllAdmin();

  //  User findByUserName(String userName);
  User findByActivationToken(String activationToken);

    long countByRole(Role role);


}
