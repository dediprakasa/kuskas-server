package com.deltadirac.kuskas.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.deltadirac.kuskas.annotation.UniqueEmail;
import com.deltadirac.kuskas.annotation.UniqueUsername;

// import com.deltadirac.kuskas.annotation.UniqueEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    @UniqueUsername
    @NotBlank(message = "{username.blank}")
    private String username;

    @NotBlank(message = "{password.blank}")
    private String password;

    @Email
    @Column(unique = true)
    @UniqueEmail
    @NotBlank(message = "{email.blank}")
    private String email;

    private Instant createdAt;
    private boolean enabled;
}
