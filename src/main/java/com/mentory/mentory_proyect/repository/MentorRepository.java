package com.mentory.mentory_proyect.repository;

import com.mentory.mentory_proyect.model.MentorModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<MentorModel, Long> {
    boolean existsByEmailUsuario(String emailUsuario);

    boolean existsByPhoneUsuario(String phoneUsuario);

    // método para buscar usuario por email
    Optional<MentorModel> findByEmailUsuario(String emailUsuario);
}