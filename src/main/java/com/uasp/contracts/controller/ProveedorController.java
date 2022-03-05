/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.uasp.contracts.controller;

import com.google.gson.Gson;
import com.uasp.contracts.MessageResponse;
import com.uasp.contracts.model.Proveedor;
import com.uasp.contracts.service.ProveedorService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Daniel
 */
@CrossOrigin(origins = {"*"})
@RestController
@PreAuthorize("hasAnyAuthority('CONT')")
@RequestMapping("/api/proveedor")
public class ProveedorController {

    @Autowired
    ProveedorService service;

    @Autowired
    Gson g;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('CONT', 'ADMIN', 'USER')")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.findByActivo(true));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('CONT', 'ADMIN', 'USER')")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        return ResponseEntity.of(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody Proveedor input) {
        try {
            int idRes = service.update(input, id);

            if (idRes != -1) {
                MessageResponse m = new MessageResponse("Elemento con id " + idRes + " modificado correctamente");
                return ResponseEntity.ok(g.toJson(m));
            } else {
                MessageResponse m = new MessageResponse("No se encuentra el elemento con id " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(g.toJson(m));
            }

        } catch (Exception e) {
            MessageResponse m = new MessageResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(g.toJson(m));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody Proveedor input) {
        try {
            int idRes;
            if (service.findByName(input.getNombre()) != null) {
                if (service.isInactivo(input.getNombre())) {
                    Proveedor prov = service.findByName(input.getNombre());
                    prov.setActivo(true);
                    idRes = service.update(prov, prov.getId());
                    if (idRes == -1) {
                        MessageResponse m = new MessageResponse("No se encuentra el elemento con id " + prov.getId());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(g.toJson(m));
                    }
                } else {
                    MessageResponse m = new MessageResponse("Ya existe un elemento con el nombre " + input.getNombre());
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(g.toJson(m));
                }
            } else {
                idRes = service.save(input);
            }
            MessageResponse m = new MessageResponse("Elemento creado con id " + idRes);
            return ResponseEntity.status(HttpStatus.CREATED).body(g.toJson(m));
        } catch (Exception e) {
            MessageResponse m = new MessageResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(g.toJson(m));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            boolean deleted = service.deleteById(id);

            if (deleted) {
                MessageResponse m = new MessageResponse("Elemento con id " + id + " eliminado correctamente");
                return ResponseEntity.ok(g.toJson(m));
            } else {
                MessageResponse m = new MessageResponse("No se encuentra el elemento con id " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(g.toJson(m));
            }

        } catch (Exception e) {
            MessageResponse m = new MessageResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(g.toJson(m));
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

}
