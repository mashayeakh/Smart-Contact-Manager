package com.scm.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "R_USERS")
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Email
    @NotEmpty(message = "Email must not be empty")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "Password must not be empty")
    private String password;

    //    @Column(nullable = false)
    @NotEmpty(message = "About must not be empty")

    private String about;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean enabled;

    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contact> contacts = new ArrayList<>();

}
