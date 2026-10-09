package com.reto.empresa.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.reto.empresa.models.Registro;
import com.reto.empresa.repository.RegistroRepository;

@Service
public class RegistroServicioImpl {

    private final RegistroRepository registroRepository;

    public RegistroServicioImpl(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    private void validarRegistro(Registro registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        if (registro.getId() == null) {
            throw new IllegalArgumentException("El ID del registro no puede estar vacío");
        }
    }

    private void validarRegistroNuevo(Registro registro) {
        validarRegistro(registro);
        if (registroRepository.existsById(registro.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El ID del registro ya existe");
        }
    }

    public Registro guardarRegistro(Registro registro) {
        validarRegistroNuevo(registro);
        return registroRepository.save(registro);
    }

    public List<Registro> obtenerRegistros() {
        return registroRepository.findAll();
    }

    public Registro modificarRegistro(Registro registro) {
        validarRegistro(registro);
        Optional<Registro> registroExistente = registroRepository.findById(registro.getId());
        if (registroExistente.isPresent()) {
            return registroRepository.save(registro);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El registro no existe");
        }
    }

    public boolean eliminar(UUID id) {
        if (registroRepository.existsById(id)) {
            registroRepository.deleteById(id);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el registro");
        }
    }
}
