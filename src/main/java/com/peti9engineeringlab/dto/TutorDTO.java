package com.peti9engineeringlab.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record TutorDTO(
        Long id,
        String name,
        String nickName,
        LocalDate birthDate,
        List<PetDTO> pets
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TutorDTO tutorDTO = (TutorDTO) o;
        return Objects.equals(name, tutorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
