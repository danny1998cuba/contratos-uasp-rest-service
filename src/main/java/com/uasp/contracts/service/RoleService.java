/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.uasp.contracts.service;

import com.uasp.contracts.model.Roles;
import com.uasp.contracts.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
public class RoleService implements Services<Roles, Integer>{

    @Autowired
    RoleRepository repository;
    
    @Override
    public Integer save(Roles object) {
        if(object.getId() != null) {
            object.setId(null);
        }
        Roles p = repository.save(object);
        return p.getId();
    }

    @Override
    public Integer update(Roles object, Integer id) {
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
        return true;
    }

    @Override
    public List<Roles> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Roles> findById(Integer id) {
        return repository.findById(id);
    }
    
}
