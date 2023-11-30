package com.peti9engineeringlab.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.peti9engineeringlab.dto.PetDTO;
import com.peti9engineeringlab.dto.TutorDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tutors")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String name;

    private String nickName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    public Tutor(TutorDTO dto) {
        this.id = dto.id();
        this.name = dto.name();
        this.nickName = dto.nickName();
        this.birthDate = dto.birthDate();

        dto.pets().forEach(p -> {
            Pet pet = new Pet(p);
            pet.setTutor(this);
            pets.add(pet);
        });
    }

    public Tutor(String s) {
        this.name = s;
    }

    public TutorDTO toTutorDTO() {
        List<PetDTO> petsDTO = new ArrayList<>();
        this.pets.forEach(p -> {
            PetDTO petDTO = new PetDTO(
                    p.getId(),
                    p.getName(),
                    p.getPetBreed(),
                    p.getBirthDate(),
                    p.getColor(),
                    p.getWeight(),
                    p.getVaccineDate(),
                    p.getVaccineType(),
                    this.name
            );
            petsDTO.add(petDTO);
        });
        return new TutorDTO(this.id, this.name, this.nickName, this.birthDate, petsDTO);
    }

}
