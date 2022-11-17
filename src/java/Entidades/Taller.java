/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "taller")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taller.findAll", query = "SELECT t FROM Taller t")
    , @NamedQuery(name = "Taller.findByNroContrato", query = "SELECT t FROM Taller t WHERE t.nroContrato = :nroContrato")
    , @NamedQuery(name = "Taller.findByFIniContrato", query = "SELECT t FROM Taller t WHERE t.fIniContrato = :fIniContrato")
    , @NamedQuery(name = "Taller.findByFFinContrato", query = "SELECT t FROM Taller t WHERE t.fFinContrato = :fFinContrato")
    , @NamedQuery(name = "Taller.findByNroContacto", query = "SELECT t FROM Taller t WHERE t.nroContacto = :nroContacto")
    , @NamedQuery(name = "Taller.findByDireccionTaller", query = "SELECT t FROM Taller t WHERE t.direccionTaller = :direccionTaller")
    , @NamedQuery(name = "Taller.findByDescripcion", query = "SELECT t FROM Taller t WHERE t.descripcion = :descripcion")})
public class Taller implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nro_contrato")
    private String nroContrato;
    @Column(name = "f_ini_contrato")
    @Temporal(TemporalType.DATE)
    private Date fIniContrato;
    @Column(name = "f_fin_contrato")
    @Temporal(TemporalType.DATE)
    private Date fFinContrato;
    @Column(name = "nro_contacto")
    private String nroContacto;
    @Column(name = "direccion_taller")
    private String direccionTaller;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tallernroContrato", fetch = FetchType.LAZY)
    private List<Reparacion> reparacionList;

    public Taller() {
    }

    public Taller(String nroContrato) {
        this.nroContrato = nroContrato;
    }

    public String getNroContrato() {
        return nroContrato;
    }

    public void setNroContrato(String nroContrato) {
        this.nroContrato = nroContrato;
    }

    public Date getFIniContrato() {
        return fIniContrato;
    }

    public void setFIniContrato(Date fIniContrato) {
        this.fIniContrato = fIniContrato;
    }

    public Date getFFinContrato() {
        return fFinContrato;
    }

    public void setFFinContrato(Date fFinContrato) {
        this.fFinContrato = fFinContrato;
    }

    public String getNroContacto() {
        return nroContacto;
    }

    public void setNroContacto(String nroContacto) {
        this.nroContacto = nroContacto;
    }

    public String getDireccionTaller() {
        return direccionTaller;
    }

    public void setDireccionTaller(String direccionTaller) {
        this.direccionTaller = direccionTaller;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Reparacion> getReparacionList() {
        return reparacionList;
    }

    public void setReparacionList(List<Reparacion> reparacionList) {
        this.reparacionList = reparacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nroContrato != null ? nroContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taller)) {
            return false;
        }
        Taller other = (Taller) object;
        if ((this.nroContrato == null && other.nroContrato != null) || (this.nroContrato != null && !this.nroContrato.equals(other.nroContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Taller[ nroContrato=" + nroContrato + " ]";
    }
    
}
