package com.cms.japi.metadata.internal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter
    private Integer id;


    @Getter
    @NotNull
    private String username;


    @Getter
    private String email;

    //ovde valjda treba da ide hashovana vrednost !!!!!!

    private String password;
}
