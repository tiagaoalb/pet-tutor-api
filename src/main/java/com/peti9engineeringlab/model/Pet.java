package com.peti9engineeringlab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peti9engineeringlab.dto.PetDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tb_pets")
@Data
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "pet_breed")
    private String petBreed;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String color;

    private Double weight;

    @Column(name = "vaccine_date")
    private LocalDate vaccineDate;

    @Column(name = "vaccine_type")
    private String vaccineType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    public Pet (PetDTO dto) {

        this.id = dto.id();
        this.name = dto.name();
        this.petBreed = dto.petBreed();
        this.birthDate = dto.birthDate();
        this.color = dto.color();
        this.weight = dto.weight();
        this.vaccineDate = dto.vaccineDate();
        this.vaccineType = dto.vaccineType();

        if (dto.tutorName() != null) {
            this.tutor = new Tutor(dto.tutorName());
        }
    }

    public PetDTO toPetDTO() {
        return new PetDTO(
            this.getId(),
            this.getName(),
            this.getPetBreed(),
            this.getBirthDate(),
            this.getColor(),
            this.getWeight(),
            this.getVaccineDate(),
            this.getVaccineType(),
            this.tutor.getName()
        );
    }

}
