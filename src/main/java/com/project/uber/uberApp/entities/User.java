package com.project.uber.uberApp.entities;


import com.project.uber.uberApp.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;
    private String password;


    //This will create another table specifically for roles only
    @ElementCollection(fetch = FetchType.LAZY)
    //It'll tell hibernate that we are storing the role as it is
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

}
