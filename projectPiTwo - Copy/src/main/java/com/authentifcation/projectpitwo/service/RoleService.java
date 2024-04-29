package com.authentifcation.projectpitwo.service;


import com.authentifcation.projectpitwo.dao.RoleDao;
import com.authentifcation.projectpitwo.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }

}
