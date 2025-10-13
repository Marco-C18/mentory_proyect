package com.mentory.mentory_proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mentory.mentory_proyect.model.AprendizModel;
import java.util.Optional;

@Repository
public interface AprendizRepository extends JpaRepository<AprendizModel, Long> {
    boolean existsByEmailUsuario(String emailUsuario);
    boolean existsByPhoneUsuario(String phoneUsuario);
    
    //método para buscar usuario por email
    Optional<AprendizModel> findByEmailUsuario(String emailUsuario);
}