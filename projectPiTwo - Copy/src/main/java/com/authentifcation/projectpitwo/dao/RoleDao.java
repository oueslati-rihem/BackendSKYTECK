package com.authentifcation.projectpitwo.dao;


import com.authentifcation.projectpitwo.entities.Role;
import com.authentifcation.projectpitwo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

    Role findByroleName(String roleName);
}
