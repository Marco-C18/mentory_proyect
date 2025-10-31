package com.mentory.mentory_proyect.repository;

import com.mentory.mentory_proyect.model.UsuarioBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioBaseModel, Long> {
    
    Optional<UsuarioBaseModel> findByEmailUsuario(String email);
    
    boolean existsByEmailUsuario(String email);
    
}