/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.uasp.contracts.service;

import com.uasp.contracts.model.Contrato;
import com.uasp.contracts.repository.ContratoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
public class ContratoService implements Services<Contrato, Integer> {

    @Autowired
    private ContratoRepository repository;

    @Override
    public Integer save(Contrato object) {
        if (object.getId() != null) {
            object.setId(null);
        }
        Contrato p = repository.save(object);
        return p.getId();
    }

    @Override
    public Integer update(Contrato object, Integer id) {
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
    public List<Contrato> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Contrato> findById(Integer id) {
        return repository.findById(id);
    }

    public List<Contrato> findByIdProveedor(int idProv) {
        return repository.findByIdProveedor(idProv);
    }

    public List<Contrato> findByYear(int year) {
        return repository.findByYear(year);
    }

    public List<Contrato> findIfIsDict(boolean isDict) {
        return isDict ? repository.findByIdDictamenIsNotNull()
                : repository.findByIdDictamenIsNull();
    }

    public List<Contrato> findIfIsAprob(boolean isAprob, boolean withDict) {
        if (isAprob) {
            return repository.findByAprobados();
        } else {
            if (withDict) {
                return repository.findByNoAprobadosWithDict();
            } else {
                return repository.findByNoAprobados();
            }
        }
    }

    public List<Contrato> findXVenc() {
        return repository.findXVenc();
    }

    public List<Contrato> findXVigencia(boolean isVig) {
        return isVig ? repository.findVigentes()
                : repository.findVencidos();
    }

}
