/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.uasp.contracts.service;

import com.uasp.contracts.model.Dictamen;
import com.uasp.contracts.repository.DictamenRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
public class DictamenService implements Services<Dictamen, Integer> {
    
    @Autowired
    private DictamenRepository repository;
    
    @Override
    public Integer save(Dictamen object) {
        if(object.getId() != null) {
            object.setId(null);
        }
        Dictamen p = repository.save(object);
        return p.getId();
    }
    
    @Override
    public Integer update(Dictamen object, Integer id) {
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
    public List<Dictamen> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Optional<Dictamen> findById(Integer id) {
        return repository.findById(id);
    }
    
}
