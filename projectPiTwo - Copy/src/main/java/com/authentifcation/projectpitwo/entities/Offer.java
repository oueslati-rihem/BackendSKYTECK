package com.authentifcation.projectpitwo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offer implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long  offerId;
    String titre;
    String description;
    LocalDate dateSoumission;
    String statut;
    @Column(name = "image_url")
    String imageUrl;

    @Transient
    MultipartFile imageFile;



    @OneToMany(mappedBy = "offer")
    @ToString.Exclude
    @JsonIgnore
    List<Category> Categories;

    @ManyToOne
    @ToString.Exclude

    //@JoinColumn(name = "tuteur_id")
    User tuteur;




    //LocalDate dateShipped;
    // String shippingId;
}
