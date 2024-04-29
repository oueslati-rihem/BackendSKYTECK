package com.authentifcation.projectpitwo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Participation implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long  participationId;

    Integer nbparticipation;
    String statut;
    String titreoffer;
    String descriptionoffer;
    @ManyToOne
    @JoinColumn(name = "offre_id", nullable = false)
    @JsonIgnore
    private Offer offer;

}
