package com.authentifcation.projectpitwo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long roomId ;
    String name ;
    private String Description ;
    private LocalDate createdDate ;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imageUrl;
    @Enumerated(EnumType.ORDINAL)
    private TypeRoom typeRoom ;
    private Long capacity ;
    @JsonIgnore
    @OneToMany(mappedBy = "room")
    List<Poste> posts ;

    //user
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(name = "user_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();
}