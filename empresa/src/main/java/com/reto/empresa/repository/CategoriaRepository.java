package com.reto.empresa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reto.empresa.models.Categoria;

@Repository 
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    // Puedes agregar métodos de consulta personalizados aquí si es necesario

}
