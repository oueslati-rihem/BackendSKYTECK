package com.authentifcation.projectpitwo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Poste {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long postId ;
    String postName ;
     String description ;
    LocalDate createdDate ;
     String image;
    @Enumerated(EnumType.ORDINAL)
    TypePoste typePoste ;
    @ElementCollection
    Map<Reaction,Integer> reactions=new HashMap<>() ;
@JsonIgnore
     @ManyToOne
     Room room ;
    @JsonIgnore
     @OneToMany(mappedBy = "poste")
    List<Comment> comments ;

     //user




}
