/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.uasp.contracts.service;

import com.uasp.contracts.model.Proveedor;
import com.uasp.contracts.repository.ProveedorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
public class ProveedorService implements Services<Proveedor, Integer> {

    @Autowired
    private ProveedorRepository repository;

    @Override
    public Integer save(Proveedor object) {
        if (object.getId() != null) {
            object.setId(null);
        }
        Proveedor p = repository.save(object);
        return p.getId();
    }

    @Override
    public Integer update(Proveedor object, Integer id) {
        if (repository.findById(id).isPresent()) {
            object.setId(id);
            repository.save(object);
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        if (repository.findById(id).isPresent()) {
            Proveedor p = repository.findById(id).get();
            p.setActivo(false);
            repository.save(p);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Proveedor> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Proveedor> findById(Integer id) {
        return repository.findById(id);
    }

    public List<Proveedor> findByActivo(boolean activo) {
        return repository.findByActivo(activo);
    }

    public boolean isInactivo(String nombre) {
        return repository.findByNombreAndActivo(nombre, false) != null;
    }

    public Proveedor findByName(String nombre) {
        return repository.findByNombre(nombre);
    }
}
