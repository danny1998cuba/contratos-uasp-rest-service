/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.uasp.contracts.repository;

import com.uasp.contracts.model.Contrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Daniel
 */
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {

    public List<Contrato> findByIdProveedor(int idProveedor);

    public List<Contrato> findByIdDictamenIsNull();

    public List<Contrato> findByIdDictamenIsNotNull();
    
    public List<Contrato> findByIdDictamenIsNullAndIdProveedor(int idProveedor);

    public List<Contrato> findByIdDictamenIsNotNullAndIdProveedor(int idProveedor);

    @Query(nativeQuery = true,
            value = "SELECT c.* FROM `contrato` as c left join `dictamen` as d "
            + "on d.id = c.id_dictamen WHERE c.id_dictamen is null "
            + "or d.aprobado = false")
    public List<Contrato> findByNoAprobados();
    
    @Query(nativeQuery = true,
            value = "SELECT c.* FROM `contrato` as c inner join `dictamen` as d "
            + "on d.id = c.id_dictamen WHERE d.aprobado = false")
    public List<Contrato> findByNoAprobadosWithDict();

    @Query(nativeQuery = true,
            value = "SELECT c.* FROM `contrato` as c INNER join `dictamen` as d "
            + "on d.id = c.id_dictamen WHERE d.aprobado = true")
    public List<Contrato> findByAprobados();

}
