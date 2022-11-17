/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "reg_datos_toner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegDatosToner.findAll", query = "SELECT r FROM RegDatosToner r")
    , @NamedQuery(name = "RegDatosToner.findByFechaEntrega", query = "SELECT r FROM RegDatosToner r WHERE r.fechaEntrega = :fechaEntrega")
    , @NamedQuery(name = "RegDatosToner.findByCantidadEntregada", query = "SELECT r FROM RegDatosToner r WHERE r.cantidadEntregada = :cantidadEntregada")
    , @NamedQuery(name = "RegDatosToner.findByNombre", query = "SELECT r FROM RegDatosToner r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "RegDatosToner.findByIdRegToner", query = "SELECT r FROM RegDatosToner r WHERE r.idRegToner = :idRegToner")})
public class RegDatosToner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Column(name = "cantidad_entregada")
    private Integer cantidadEntregada;
    @Column(name = "nombre")
    private String nombre;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reg_toner")
    private Integer idRegToner;
    @JoinColumn(name = "impresorano_inventario", referencedColumnName = "no_inventario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Impresora impresoranoInventario;
    @JoinColumn(name = "t_tonnersn_tonner", referencedColumnName = "sn_tonner")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TTonner tTonnersnTonner;

    public RegDatosToner() {
    }

    public RegDatosToner(Integer idRegToner) {
        this.idRegToner = idRegToner;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdRegToner() {
        return idRegToner;
    }

    public void setIdRegToner(Integer idRegToner) {
        this.idRegToner = idRegToner;
    }

    public Impresora getImpresoranoInventario() {
        return impresoranoInventario;
    }

    public void setImpresoranoInventario(Impresora impresoranoInventario) {
        this.impresoranoInventario = impresoranoInventario;
    }

    public TTonner getTTonnersnTonner() {
        return tTonnersnTonner;
    }

    public void setTTonnersnTonner(TTonner tTonnersnTonner) {
        this.tTonnersnTonner = tTonnersnTonner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegToner != null ? idRegToner.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegDatosToner)) {
            return false;
        }
        RegDatosToner other = (RegDatosToner) object;
        if ((this.idRegToner == null && other.idRegToner != null) || (this.idRegToner != null && !this.idRegToner.equals(other.idRegToner))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegDatosToner[ idRegToner=" + idRegToner + " ]";
    }
    
}
