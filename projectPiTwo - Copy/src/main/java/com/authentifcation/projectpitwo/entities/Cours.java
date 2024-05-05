package com.authentifcation.projectpitwo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity

public class Cours implements Serializable {

    @Id
    Long idC ;
    String title ;
    String path ;
     double averageRating;
     int totalRatings;
    @JoinColumn(name = "user_id")

    @ManyToOne
    User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="cours")
    private Set<Quiz> Quizs;


}

