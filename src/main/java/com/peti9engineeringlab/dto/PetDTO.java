package com.peti9engineeringlab.dto;

import java.time.LocalDate;
import java.util.Objects;

public record PetDTO (
        Long id,
        String name,
        String petBreed,
        LocalDate birthDate,
        String color,
        Double weight,
        LocalDate vaccineDate,
        String vaccineType,
        String tutorName
)
{

}
