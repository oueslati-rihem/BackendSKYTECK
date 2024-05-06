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

public class Quiz implements Serializable {
    @Id

    Long idQ;
    String title ;
    Double moyenneScores;
    @ManyToOne
    @JoinColumn(name = "Cours_id")
    @JsonIgnore
    Cours cours;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="quiz")
    private Set<Question> Questions;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="quiz")
    private Set<Tentative> Tentatives;
}
