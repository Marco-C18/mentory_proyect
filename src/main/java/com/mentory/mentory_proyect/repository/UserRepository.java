package com.mentory.mentory_proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mentory.mentory_proyect.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailUsuario(String emailUsuario);
    boolean existsByPhoneUsuario(String phoneUsuario);
    
    //método para buscar usuario por email
    Optional<User> findByEmailUsuario(String emailUsuario);
}