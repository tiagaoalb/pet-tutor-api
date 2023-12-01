package com.peti9engineeringlab.repositories;

import com.peti9engineeringlab.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByNameContainingIgnoreCase(String name);
    void deletePetById(Long id);
}
