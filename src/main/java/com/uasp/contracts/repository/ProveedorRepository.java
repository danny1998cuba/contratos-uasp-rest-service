/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.uasp.contracts.repository;

import com.uasp.contracts.model.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel
 */
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    public List<Proveedor> findByActivo(boolean activo);

    public Proveedor findByNombre(String nombre);
    
    public Proveedor findByNombreAndActivo(String nombre, boolean activo);

}
