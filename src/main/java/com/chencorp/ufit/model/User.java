package com.chencorp.ufit.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`user`") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 25)
    private String username;

    @Column(length = 255)
    private String password;

    private Integer level;

    private Integer active;

    private LocalDateTime endDt;

    private Integer login;
}