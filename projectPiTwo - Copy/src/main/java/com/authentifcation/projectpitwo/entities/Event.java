package com.authentifcation.projectpitwo.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event implements Serializable {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numEvent;
    String nom;
    String description;
    Date date;
    int nombreDePlace;
    String PhotoUrl;
    String Link;
    @ManyToOne
    @JoinColumn(name = "Id", nullable = false)
    User user;

}