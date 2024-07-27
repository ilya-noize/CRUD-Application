package com.example.crud_application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.crud_application.entity.PersonLimit.MAX_EMAIL_LENGTH;
import static com.example.crud_application.entity.PersonLimit.MAX_FIRSTNAME_LENGTH;
import static com.example.crud_application.entity.PersonLimit.MAX_LASTNAME_LENGTH;

@Entity
@Table(name = "person")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(
            name = "email",
            unique = true,
            nullable = false,
            length = MAX_EMAIL_LENGTH
    )
    private String email;

    @Column(
            name = "firstname",
            nullable = false,
            length = MAX_FIRSTNAME_LENGTH

    )
    private String firstname;
    @Column(
            name = "lastname",
            nullable = false,
            length = MAX_LASTNAME_LENGTH
    )
    private String lastname;
    @Column(
            name = "middlename",
            length = MAX_LASTNAME_LENGTH
    )
    private String middlename;
}
