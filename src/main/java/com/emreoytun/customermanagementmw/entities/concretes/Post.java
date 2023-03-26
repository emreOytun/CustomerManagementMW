package com.emreoytun.customermanagementmw.entities.concretes;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post", nullable = false)
    private String post;

    @ManyToOne() // TODO: @ManyToOne(cascade = CascadeType.MERGE ...) 
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
