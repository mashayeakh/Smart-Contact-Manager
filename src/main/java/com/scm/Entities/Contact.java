package com.scm.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CONTACT")
public class Contact {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;

    @Column(nullable = false)
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Column(nullable = false)
    private String nickName;

    @Column(unique = true, nullable = false)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

//    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @NotNull
    private Long phoneNumber;

    @Column(nullable = false)
    private String work;

    @Column(length = 5000)
    private String description;

    //    private User user;
    @ManyToOne
    private RegisteredUser rUser;


}
