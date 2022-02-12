/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.uasp.contracts.repository;

import com.uasp.contracts.model.Dictamen;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface DictamenRepository extends JpaRepository<Dictamen, Integer> {
    
}
