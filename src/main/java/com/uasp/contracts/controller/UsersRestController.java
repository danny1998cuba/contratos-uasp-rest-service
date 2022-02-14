/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.uasp.contracts.controller;

import com.uasp.contracts.model.Roles;
import com.uasp.contracts.model.Users;
import com.uasp.contracts.service.RoleService;
import com.uasp.contracts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    @Autowired
    UserService service;

    @Autowired
    RoleService roleService;

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
                            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Rol no existente " + rol.getName());
                        }
                    }
                    int idRes = service.update(input, id);

                    if (idRes != -1) {
                        return ResponseEntity.ok("Elemento con id " + idRes + " modificado correctamente");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el elemento con id " + id);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("El usuario debe tener al menos un rol");
                }
            } else {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("El nombre de usuario ya existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody Users input) {
        try {
            if (!service.existentUsername(input.getUsername())) {
                if (!input.getRolesList().isEmpty()) {
                    for (Roles rol : input.getRolesList()) {
                        if (!roleService.findById(rol.getId()).isPresent()) {
                            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Rol no existente " + rol.getName());
                        }
                    }
                    int idRes = service.save(input);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Elemento creado con id " + idRes);
                } else {
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("El usuario debe tener al menos un rol");
                }
            } else {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("El nombre de usuario ya existe");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            boolean deleted = service.deleteById(id);

            if (deleted) {
                return ResponseEntity.ok("Elemento con id " + id + " eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el elemento con id " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
}
