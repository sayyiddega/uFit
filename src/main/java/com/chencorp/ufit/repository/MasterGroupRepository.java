package com.chencorp.ufit.repository;

import com.chencorp.ufit.model.MasterGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface MasterGroupRepository extends JpaRepository<MasterGroup, Integer> {
    Optional<MasterGroup> findByName(String name); 
    Optional<MasterGroup> findById(Integer id); 
    
}