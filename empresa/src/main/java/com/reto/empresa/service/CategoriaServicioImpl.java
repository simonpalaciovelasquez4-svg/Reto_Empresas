package com.reto.empresa.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.reto.empresa.models.Categoria;
import com.reto.empresa.repository.CategoriaRepository;

@Service 
public class CategoriaServicioImpl  {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServicioImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;

    }
    private void validarCategoria(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
        if(categoria.getNombre()==categoria.getNombre()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de la categoría ya existe");
        }
        if(categoria.getDescripcion().length()>255){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La descripción de la categoría no puede exceder los 255 caracteres");
        }
    }
    public Categoria guardarCategoria(Categoria categoria) {
        validarCategoria(categoria);
        return categoriaRepository.save(categoria);
    }
    public List<Categoria> obtenerCategorias() {
        return categoriaRepository.findAll();
    }
    public Categoria modificarCategoria(Categoria categoria) {
        validarCategoria(categoria);
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(categoria.getId());
        if (categoriaExistente.isPresent()) {
            Categoria categoriaEncontrada = categoriaExistente.get();
            categoriaEncontrada.setNombre(categoria.getNombre());
            categoriaEncontrada.setDescripcion(categoria.getDescripcion());
            return categoriaRepository.save(categoriaEncontrada);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría no existe");
        }
    }
        public boolean eliminar(UUID id){
        Optional<Categoria> categoriaBuscada=this.categoriaRepository.findById(id);
        if(categoriaBuscada.isPresent()){

            this.categoriaRepository.deleteById(id);
            return true;

        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encontro la categoría");
        }
    }
}
