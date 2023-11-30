package com.peti9engineeringlab.repositories;

import com.peti9engineeringlab.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByNameContainingIgnoreCase(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Pet p WHERE p.id = :id")
    void deletePetById(Long id);
}
