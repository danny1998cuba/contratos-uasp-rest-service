/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.uasp.contracts.controller;

import com.uasp.contracts.model.Contrato;
import com.uasp.contracts.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Daniel
 */
@RestController
@RequestMapping("/contrato")
public class ContratoController {
    
    @Autowired
    ContratoService service;
    
    @GetMapping("")
    public ResponseEntity<?> list(
            @RequestParam(name = "prov", required = false) Integer idProv,
            @RequestParam(name = "dict", required = false) Boolean isDict,
            @RequestParam(name = "aprob", required = false) Boolean isAprob) {
        ResponseEntity<?> response;
        
        switch (processList(idProv, isDict, isAprob)) {
            case FIND_ALL:
                response = ResponseEntity.ok(service.findAll());
                break;
            case FIND_BY_PROV:
                response = ResponseEntity.ok(service.findByIdProveedor(idProv));
                break;
            case FIND_BY_DICT:
                response = ResponseEntity.ok(service.findIfIsDict(isDict));
                break;
            case FIND_BY_APROBADO:
                response = ResponseEntity.ok(service.findIfIsAprob(isAprob, false));
                break;
            case FIND_BY_APROBADO_AND_DICT:
                response = ResponseEntity.ok(service.findIfIsAprob(isAprob, true));
                break;
            case FIND_BY_PROV_AND_DICT:                
                response = ResponseEntity.ok(service.findIfIsDictAndProv(isDict, idProv));
                break;
            default:
                response = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
        
        return response;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        return ResponseEntity.of(service.findById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody Contrato input) {
        try {
            int idRes = service.update(input, id);
            
            if (idRes != -1) {
                return ResponseEntity.ok("Elemento con id " + idRes + " modificado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el elemento con id " + id);
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }
    
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody Contrato input) {
        try {
            int idRes = service.save(input);
            return ResponseEntity.status(HttpStatus.CREATED).body("Elemento creado con id " + idRes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("No implementado");
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
    private enum QUERY_TYPE {
        FIND_ALL,
        FIND_BY_PROV,
        FIND_BY_DICT,
        FIND_BY_PROV_AND_DICT,
        FIND_BY_APROBADO,
        FIND_BY_APROBADO_AND_DICT
    }
    
    private QUERY_TYPE processList(Object idProv, Object idDict, Object isAprob) {
        if (isAprob != null) {
            if (idDict != null && (boolean) idDict == true) {
                return QUERY_TYPE.FIND_BY_APROBADO_AND_DICT;
            } else {
                return QUERY_TYPE.FIND_BY_APROBADO;
            }
        } else {
            if (idProv != null && idDict == null) {
                return QUERY_TYPE.FIND_BY_PROV;
            }
            if (idProv == null && idDict != null) {
                return QUERY_TYPE.FIND_BY_DICT;
            }
            if (idProv != null && idDict != null) {
                return QUERY_TYPE.FIND_BY_PROV_AND_DICT;
            }
        }
        return QUERY_TYPE.FIND_ALL;
    }
    
}
