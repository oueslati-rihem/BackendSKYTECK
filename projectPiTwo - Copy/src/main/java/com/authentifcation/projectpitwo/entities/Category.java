package com.authentifcation.projectpitwo.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Integer  categoryId;
    String nameCategory;
    String description;

    @ManyToOne()
    @ToString.Exclude
    Offer offer;


}
