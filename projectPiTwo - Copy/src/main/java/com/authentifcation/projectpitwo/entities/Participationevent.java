package com.authentifcation.projectpitwo.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Participationevent implements Serializable {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long IdPart;
    @ManyToOne
    @JoinColumn(name = "NumEvent", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "Id", nullable = false)
    User user;
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status = ParticipationStatus.WAITING;



}
