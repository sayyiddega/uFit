package com.chencorp.ufit.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "`group`") 
public class MasterGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer active;
}