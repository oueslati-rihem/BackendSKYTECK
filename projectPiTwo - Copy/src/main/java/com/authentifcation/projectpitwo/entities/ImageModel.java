package com.authentifcation.projectpitwo.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageModel {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id ;
    String name ;
    String type ;
    @Column(length = 50000000)
    byte[] pivByte;


    public ImageModel(String originalFilename, String contentType, byte[] bytes) {
    }
}
