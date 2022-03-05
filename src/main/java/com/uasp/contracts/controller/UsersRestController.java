/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.uasp.contracts.controller;

import com.google.gson.Gson;
import com.uasp.contracts.MessageResponse;
import com.uasp.contracts.model.Roles;
import com.uasp.contracts.model.Users;
import com.uasp.contracts.service.RoleService;
import com.uasp.contracts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Daniel
 */
@CrossOrigin(origins = {"*"})
@RestController
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/api/users")
public class UsersRestController {

    @Autowired
    UserService service;

    @Autowired
    RoleService roleService;
    
    @Autowired
    Gson g;

    @GetMapping("")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/roles")
    public ResponseEntity<?> listRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        return ResponseEntity.of(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody Users input) {
        try {
            if (!service.existentUsername(input.getUsername(), id)) {
                if (!input.getRolesList().isEmpty()) {
                    for (Roles rol : input.getRolesList()) {
                        if (!roleService.findById(rol.getId()).isPresent()) {
                            MessageResponse m = new MessageResponse("Rol no existente " + rol.getName());
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(g.toJson(m));
                        }
                    }
                    int idRes = service.update(input, id);

                    if (idRes != -1) {
                        MessageResponse m = new MessageResponse("Elemento con id " + idRes + " modificado correctamente");
                        return ResponseEntity.ok(g.toJson(m));
                    } else {
                        MessageResponse m = new MessageResponse("No se encuentra el elemento con id " + id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(g.toJson(m));
                    }
                } else {
                    MessageResponse m = new MessageResponse("El usuario debe tener al menos un rol");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(g.toJson(m));
                }
            } else {
                MessageResponse m = new MessageResponse("El nombre de usuario ya existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(g.toJson(m));
            }
        } catch (Exception e) {
            MessageResponse m = new MessageResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(g.toJson(m));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody Users input) {
        try {
            if (!service.existentUsername(input.getUsername())) {
                if (!input.getRolesList().isEmpty()) {
                    for (Roles rol : input.getRolesList()) {
                        if (!roleService.findById(rol.getId()).isPresent()) {
                            MessageResponse m = new MessageResponse("Rol no existente " + rol.getName());
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(g.toJson(m));
                        }
                    }
                    int idRes = service.save(input);
                    MessageResponse m = new MessageResponse("Elemento creado con id " + idRes);
                    return ResponseEntity.status(HttpStatus.CREATED).body(g.toJson(m));
                } else {
                    MessageResponse m = new MessageResponse("El usuario debe tener al menos un rol");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(g.toJson(m));
                }
            } else {
                MessageResponse m = new MessageResponse("El nombre de usuario ya existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(g.toJson(m));
            }

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
