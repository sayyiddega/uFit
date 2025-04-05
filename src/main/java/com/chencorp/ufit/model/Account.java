package com.chencorp.ufit.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "`account`") // Use backticks as 'account' is a reserved keyword
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_depan", nullable = false, length = 100)
    private String namaDepan;

    @Column(name = "nama_belakang", nullable = false, length = 100)
    private String namaBelakang;

    @Column(name = "gender", nullable = false, length = 50)
    private String gender;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "birthplace", nullable = false, length = 100)
    private String birthplace;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;
}
