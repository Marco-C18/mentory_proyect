package com.mentory.mentory_proyect.repository;

import com.mentory.mentory_proyect.model.SolicitudModel;
import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<SolicitudModel, Long> {
    List<SolicitudModel> findByMentor(MentorModel mentor);
    List<SolicitudModel> findByAprendiz(AprendizModel aprendiz);
    List<SolicitudModel> findByMentorAndEstado(MentorModel mentor, String estado);
    List<SolicitudModel> findByAprendizAndEstado(AprendizModel aprendiz, String estado);

    // Métodos para contar por estado
    long countByEstado(String estado);

    // Contar todas las solicitudes
    int countByMentorId(Long mentorId);

    // Contar solo las aceptadas
    int countByMentorIdAndEstadoIgnoreCase(Long mentorId, String estado);

    // Promedio de calificación
    @Query("""
    SELECT AVG(s.calificacion)
    FROM SolicitudModel s
    WHERE s.mentor.id = :mentorId
    AND s.calificacion IS NOT NULL
    """)
    Double promedioCalificacionPorMentor(@Param("mentorId") Long mentorId);

}
