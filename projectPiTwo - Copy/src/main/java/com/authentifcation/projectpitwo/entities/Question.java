package com.authentifcation.projectpitwo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Question implements Serializable {
    @Id

    Long idques;
    String question;
    String rep1 ;
    String rep2 ;
    String rep3 ;
    String repCorrect ;

    @JoinColumn(name = "quiz_id")
    @ManyToOne
    @JsonIgnore
    Quiz quiz;




}
