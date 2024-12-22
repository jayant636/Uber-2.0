package com.project.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rider")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double rating;


}
