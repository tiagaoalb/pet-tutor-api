package com.peti9engineeringlab.repositories;

import com.peti9engineeringlab.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    List<Tutor> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
}
