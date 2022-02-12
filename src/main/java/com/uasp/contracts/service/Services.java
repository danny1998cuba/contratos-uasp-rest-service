/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uasp.contracts.service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Daniel
 * @param <T>
 * @param <E>
 */
public interface Services<T, E> {

    public E save(T object);

    public E update(T object, E id);

    public boolean deleteById(E id);

    public List<T> findAll();
    
    public Optional<T> findById(E id);
}
