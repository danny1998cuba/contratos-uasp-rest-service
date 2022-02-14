/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uasp.contracts.security.userDetails;

import com.uasp.contracts.model.Users;
import com.uasp.contracts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        Users user = repository.findByUsername(string);

        if (user == null) {
            throw new UsernameNotFoundException("No se encuentra el usuario");
        }

        return new MyUserDetails(user);
    }

}
