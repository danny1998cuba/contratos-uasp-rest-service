/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uasp.contracts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "dictamen")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dictamen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "numero")
    private String numero;

    @Basic(optional = false)
    @Column(name = "aprobado")
    private boolean aprobado;

    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "idDictamen")
    @JsonIgnore
    private List<Contrato> contratoList;
}
