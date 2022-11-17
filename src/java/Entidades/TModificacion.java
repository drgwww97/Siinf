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
@Table(name = "t_modificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TModificacion.findAll", query = "SELECT t FROM TModificacion t")
    , @NamedQuery(name = "TModificacion.findByIdTmodificacion", query = "SELECT t FROM TModificacion t WHERE t.idTmodificacion = :idTmodificacion")
    , @NamedQuery(name = "TModificacion.findByNModificacion", query = "SELECT t FROM TModificacion t WHERE t.nModificacion = :nModificacion")})
public class TModificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tmodificacion")
    private Integer idTmodificacion;
    @Column(name = "n_modificacion")
    private String nModificacion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tModificacion", fetch = FetchType.LAZY)
    private Modificacion modificacion;

    public TModificacion() {
    }

    public TModificacion(Integer idTmodificacion) {
        this.idTmodificacion = idTmodificacion;
    }

    public Integer getIdTmodificacion() {
        return idTmodificacion;
    }

    public void setIdTmodificacion(Integer idTmodificacion) {
        this.idTmodificacion = idTmodificacion;
    }

    public String getNModificacion() {
        return nModificacion;
    }

    public void setNModificacion(String nModificacion) {
        this.nModificacion = nModificacion;
    }

    public Modificacion getModificacion() {
        return modificacion;
    }

    public void setModificacion(Modificacion modificacion) {
        this.modificacion = modificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTmodificacion != null ? idTmodificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TModificacion)) {
            return false;
        }
        TModificacion other = (TModificacion) object;
        if ((this.idTmodificacion == null && other.idTmodificacion != null) || (this.idTmodificacion != null && !this.idTmodificacion.equals(other.idTmodificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TModificacion[ idTmodificacion=" + idTmodificacion + " ]";
    }
    
}
