/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uasp.contracts.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "contrato")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "numero")
    private String numero;
    
    @Column(name = "duracion")
    private Integer duracion;
    
    @Column(name = "fecha_firma")
    @Temporal(TemporalType.DATE)
    private Date fechaFirma;
    
    @Column(name = "fecha_venc")
    @Temporal(TemporalType.DATE)
    private Date fechaVenc;
    
    @Column(name = "observaciones")
    private String observaciones;
    
    @Column(name = "id_proveedor")
    private int idProveedor;
    
    @Column(name = "id_dictamen")
    private Integer idDictamen;
}
