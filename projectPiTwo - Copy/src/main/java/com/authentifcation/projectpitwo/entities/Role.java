package com.authentifcation.projectpitwo.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class Role {

    @Id
    private String roleName;
    private String roleDescription;


}
