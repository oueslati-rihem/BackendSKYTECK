package com.authentifcation.projectpitwo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Tentative implements Serializable {
    @Id
    long idT;
    long note;
    long nb ;

    @ManyToOne
    User user;
    @ManyToOne
    Quiz quiz;


}
