package com.mentory.mentory_proyect.repository;

import com.mentory.mentory_proyect.model.MentorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MentorRepository extends JpaRepository<MentorModel, Long> {
    boolean existsByEmailUsuario(String email);
    boolean existsByPhoneUsuario(String phone);
    Optional<MentorModel> findByEmailUsuario(String email);

     List<MentorModel> findByEspecialidadAndCicloAcademico(String especialidad, String cicloAcademico);
}
