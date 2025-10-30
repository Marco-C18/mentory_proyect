package com.mentory.mentory_proyect.repository;

import com.mentory.mentory_proyect.model.RecordatorioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordatorioRepository extends JpaRepository<RecordatorioModel, Long> {
    List<RecordatorioModel> findByUsuarioId(Long usuarioId);
}
