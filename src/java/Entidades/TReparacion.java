/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "t_reparacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TReparacion.findAll", query = "SELECT t FROM TReparacion t")
    , @NamedQuery(name = "TReparacion.findByIdReparacio", query = "SELECT t FROM TReparacion t WHERE t.idReparacio = :idReparacio")
    , @NamedQuery(name = "TReparacion.findByTReparacion", query = "SELECT t FROM TReparacion t WHERE t.tReparacion = :tReparacion")})
public class TReparacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reparacio")
    private Integer idReparacio;
    @Column(name = "t_reparacion")
    private String tReparacion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tReparacion", fetch = FetchType.LAZY)
    private Reparacion reparacion;

    public TReparacion() {
    }

    public TReparacion(Integer idReparacio) {
        this.idReparacio = idReparacio;
    }

    public Integer getIdReparacio() {
        return idReparacio;
    }

    public void setIdReparacio(Integer idReparacio) {
        this.idReparacio = idReparacio;
    }

    public String getTReparacion() {
        return tReparacion;
    }

    public void setTReparacion(String tReparacion) {
        this.tReparacion = tReparacion;
    }

    public Reparacion getReparacion() {
        return reparacion;
    }

    public void setReparacion(Reparacion reparacion) {
        this.reparacion = reparacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReparacio != null ? idReparacio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TReparacion)) {
            return false;
        }
        TReparacion other = (TReparacion) object;
        if ((this.idReparacio == null && other.idReparacio != null) || (this.idReparacio != null && !this.idReparacio.equals(other.idReparacio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TReparacion[ idReparacio=" + idReparacio + " ]";
    }
    
}
