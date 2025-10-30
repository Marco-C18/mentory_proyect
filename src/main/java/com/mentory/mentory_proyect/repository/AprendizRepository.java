package com.mentory.mentory_proyect.repository;

import com.mentory.mentory_proyect.model.AprendizModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AprendizRepository extends JpaRepository<AprendizModel, Long> {
    Optional<AprendizModel> findByEmailUsuario(String emailUsuario);
    boolean existsByEmailUsuario(String emailUsuario);
    boolean existsByPhoneUsuario(String phoneUsuario); 
}
