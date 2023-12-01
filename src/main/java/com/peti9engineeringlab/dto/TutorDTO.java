package com.peti9engineeringlab.dto;

import java.time.LocalDate;
import java.util.List;

public record TutorDTO(
        Long id,
        String name,
        String nickName,
        LocalDate birthDate,
        List<PetDTO> pets
) {
}
