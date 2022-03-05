/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.uasp.contracts.controller;

import com.google.gson.Gson;
import com.uasp.contracts.MessageResponse;
import com.uasp.contracts.model.Contrato;
import com.uasp.contracts.service.ContratoService;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Daniel
 */
@CrossOrigin(origins = {"*"})
@RestController
@PreAuthorize("hasAnyAuthority('CONT')")
@RequestMapping("/api/contrato")
public class ContratoController {

    @Autowired
    ContratoService service;

    @Autowired
    Gson g;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('CONT', 'ADMIN', 'USER')")
    public ResponseEntity<?> list(
            @RequestParam(name = "prov", required = false) Integer idProv,
            @RequestParam(name = "dict", required = false) Boolean isDict,
            @RequestParam(name = "aprob", required = false) Boolean isAprob,
            @RequestParam(name = "vig", required = false) Boolean vig,
            @RequestParam(name = "xVenc", required = false) Boolean xVenc,
            @RequestParam(name = "year", required = false) Integer year) {
        ResponseEntity<?> response;

        List<List<Contrato>> listas = new ArrayList<>();
        List<Contrato> byProv = idProv != null ? service.findByIdProveedor(idProv) : null;
        List<Contrato> byDict = isDict != null ? service.findIfIsDict(isDict) : null;
        List<Contrato> byAprob = isAprob != null ? service.findIfIsAprob(isAprob, isDict != null ? isDict : false) : null;
        List<Contrato> byVig = vig != null ? service.findXVigencia(vig) : null;
        List<Contrato> byXVenc = (xVenc != null && xVenc) ? service.findXVenc() : null;
        List<Contrato> byYear = year != null ? service.findByYear(year) : null;

        listas.add(byProv);
        listas.add(byDict);
        listas.add(byAprob);
        listas.add(byVig);
        listas.add(byXVenc);
        listas.add(byYear);

        List<Contrato> result = new ArrayList<>();
        boolean yaAdded = false;
        for (List<Contrato> l : listas) {
            if (l != null) {
                if (!yaAdded) {
                    result = l;
                    yaAdded = true;
                } else {
                    result.retainAll(l);
                }
            }
        }

        if (result.isEmpty() && !yaAdded) {
            result = service.findAll();
        }

        response = ResponseEntity.ok(result);
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
    public ResponseEntity<?> post(@RequestBody Contrato input) {
        try {
            int idRes = service.save(input);
            MessageResponse m = new MessageResponse("Elemento creado con id " + idRes);
            return ResponseEntity.status(HttpStatus.CREATED).body(g.toJson(m));
        } catch (Exception e) {
            MessageResponse m = new MessageResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(g.toJson(m));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        MessageResponse m = new MessageResponse("No implementado");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(g.toJson(m));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

}
