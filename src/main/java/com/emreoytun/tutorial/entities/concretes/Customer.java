package com.emreoytun.tutorial.entities.concretes;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customers")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name", length = 20, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 5, max = 20)
    private String userName;

    @Column(name = "first_name", nullable = false)
    @NotNull
    @NotBlank
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull
    @NotBlank
    private String lastName;

}
