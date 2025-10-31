package com.mentory.mentory_proyect.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentory.mentory_proyect.model.RecordatorioModel;
import com.mentory.mentory_proyect.repository.RecordatorioRepository;

@Service
public class RecordatorioService {

    @Autowired
    private RecordatorioRepository recordatorioRepository;

    public void crearRecordatorio(Long usuarioId, String mensaje, LocalDateTime fecha) {
        RecordatorioModel r = new RecordatorioModel();
        r.setUsuarioId(usuarioId);
        r.setMensaje(mensaje);
        r.setFecha(fecha);
        r.setEnviado(false);
        recordatorioRepository.save(r);
    }

    public List<RecordatorioModel> listarPorUsuario(Long id) {
        return recordatorioRepository.findByUsuarioId(id);
    }
}

