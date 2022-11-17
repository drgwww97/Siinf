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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "reparacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reparacion.findAll", query = "SELECT r FROM Reparacion r")
    , @NamedQuery(name = "Reparacion.findByFIda", query = "SELECT r FROM Reparacion r WHERE r.fIda = :fIda")
    , @NamedQuery(name = "Reparacion.findByFRegreso", query = "SELECT r FROM Reparacion r WHERE r.fRegreso = :fRegreso")
    , @NamedQuery(name = "Reparacion.findByDescripcion", query = "SELECT r FROM Reparacion r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "Reparacion.findByNInv", query = "SELECT r FROM Reparacion r WHERE r.nInv = :nInv")
    , @NamedQuery(name = "Reparacion.findByTipoEquipo", query = "SELECT r FROM Reparacion r WHERE r.tipoEquipo = :tipoEquipo")
    , @NamedQuery(name = "Reparacion.findByTReparacionidReparacio", query = "SELECT r FROM Reparacion r WHERE r.tReparacionidReparacio = :tReparacionidReparacio")})
public class Reparacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "f_ida")
    @Temporal(TemporalType.DATE)
    private Date fIda;
    @Column(name = "f_regreso")
    @Temporal(TemporalType.DATE)
    private Date fRegreso;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "n_inv")
    private String nInv;
    @Column(name = "tipo_equipo")
    private String tipoEquipo;
    @Id
    @Basic(optional = false)
    @Column(name = "t_reparacionid_reparacio")
    private Integer tReparacionidReparacio;
    @JoinColumn(name = "estadoid_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estado estadoidEstado;
    @JoinColumn(name = "t_reparacionid_reparacio", referencedColumnName = "id_reparacio", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private TReparacion tReparacion;
    @JoinColumn(name = "tallernro_contrato", referencedColumnName = "nro_contrato")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Taller tallernroContrato;

    public Reparacion() {
    }

    public Reparacion(Integer tReparacionidReparacio) {
        this.tReparacionidReparacio = tReparacionidReparacio;
    }

    public Reparacion(Integer tReparacionidReparacio, String nInv) {
        this.tReparacionidReparacio = tReparacionidReparacio;
        this.nInv = nInv;
    }

    public Date getFIda() {
        return fIda;
    }

    public void setFIda(Date fIda) {
        this.fIda = fIda;
    }

    public Date getFRegreso() {
        return fRegreso;
    }

    public void setFRegreso(Date fRegreso) {
        this.fRegreso = fRegreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNInv() {
        return nInv;
    }

    public void setNInv(String nInv) {
        this.nInv = nInv;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public Integer getTReparacionidReparacio() {
        return tReparacionidReparacio;
    }

    public void setTReparacionidReparacio(Integer tReparacionidReparacio) {
        this.tReparacionidReparacio = tReparacionidReparacio;
    }

    public Estado getEstadoidEstado() {
        return estadoidEstado;
    }

    public void setEstadoidEstado(Estado estadoidEstado) {
        this.estadoidEstado = estadoidEstado;
    }

    public TReparacion getTReparacion() {
        return tReparacion;
    }

    public void setTReparacion(TReparacion tReparacion) {
        this.tReparacion = tReparacion;
    }

    public Taller getTallernroContrato() {
        return tallernroContrato;
    }

    public void setTallernroContrato(Taller tallernroContrato) {
        this.tallernroContrato = tallernroContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tReparacionidReparacio != null ? tReparacionidReparacio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reparacion)) {
            return false;
        }
        Reparacion other = (Reparacion) object;
        if ((this.tReparacionidReparacio == null && other.tReparacionidReparacio != null) || (this.tReparacionidReparacio != null && !this.tReparacionidReparacio.equals(other.tReparacionidReparacio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Reparacion[ tReparacionidReparacio=" + tReparacionidReparacio + " ]";
    }
    
}
