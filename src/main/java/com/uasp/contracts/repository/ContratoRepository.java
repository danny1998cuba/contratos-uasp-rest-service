/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.uasp.contracts.repository;

import com.uasp.contracts.model.Contrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Daniel
 */
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {

    @Query("Select c from Contrato c where c.idProveedor.id = :idProveedor")
    public List<Contrato> findByIdProveedor(@Param(value = "idProveedor") int idProveedor);

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

    @Query(value = "SELECT c FROM Contrato c WHERE c.fechaFirma is not null and YEAR(c.fechaFirma) = :year")
    public List<Contrato> findByYear(@Param(value = "year") int year);
    
    @Query(value = "SELECT c from Contrato c WHERE c.fechaVenc is not null and TIMESTAMPDIFF(day,now(), c.fechaVenc) >= 0 and TIMESTAMPDIFF(day,now(), c.fechaVenc) < 60")
    public List<Contrato> findXVenc();

    @Query(value = "SELECT c from Contrato c WHERE c.fechaVenc is null or TIMESTAMPDIFF(day,now(), c.fechaVenc) >= 0")
    public List<Contrato> findVigentes();
    
    @Query(value = "SELECT c from Contrato c WHERE c.fechaVenc is not null and TIMESTAMPDIFF(day,now(), c.fechaVenc) < 0")
    public List<Contrato> findVencidos();
}
