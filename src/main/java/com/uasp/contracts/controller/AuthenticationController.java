/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.uasp.contracts.controller;

import com.google.gson.Gson;
import com.uasp.contracts.MessageResponse;
import com.uasp.contracts.model.UserAuth;
import com.uasp.contracts.model.Users;
import com.uasp.contracts.security.userDetails.MyUserDetails;
import com.uasp.contracts.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;

/**
 *
 * @author Daniel
 */
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    AuthenticationManager auth;

    @Autowired
    UserService userService;

    @Autowired
    Gson g;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuth input) {
        Authentication authObj;

        try {
            authObj = auth.authenticate(
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObj);
            return ResponseEntity.status(HttpStatus.OK).body(g.toJson(new MessageResponse(RequestContextHolder.currentRequestAttributes().getSessionId())));
        } catch (BadCredentialsException ex) {
            MessageResponse msg = new MessageResponse("Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(g.toJson(msg));
        }
    }

    @GetMapping("/userAuth")
    public ResponseEntity<?> getAuthenticated() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users u;

        if (principal != "anonymousUser") {
            String uN = ((MyUserDetails) principal).getUser().getUsername();
            u = userService.findByUsername(uN).orElse(null);
        } else {
            u = null;
        }

        return ResponseEntity.of(Optional.ofNullable(u));
    }
}
