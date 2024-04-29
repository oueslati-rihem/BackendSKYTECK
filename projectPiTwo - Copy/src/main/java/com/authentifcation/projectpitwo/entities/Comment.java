package com.authentifcation.projectpitwo.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Comment {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long commentId ;
    String text ;
    Instant createdDate ;
    @ManyToOne
    @JsonIgnore
    Poste poste ;

    //user

}
