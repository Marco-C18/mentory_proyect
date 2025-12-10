package com.mentory.mentory_proyect.repository;

import com.mentory.mentory_proyect.model.SolicitudModel;
import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<SolicitudModel, Long> {
    List<SolicitudModel> findByMentor(MentorModel mentor);
    List<SolicitudModel> findByAprendiz(AprendizModel aprendiz);

    // Métodos para contar por estado
    long countByEstado(String estado);
}
