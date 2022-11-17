/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "s_o")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SO.findAll", query = "SELECT s FROM SO s")
    , @NamedQuery(name = "SO.findByIdSo", query = "SELECT s FROM SO s WHERE s.idSo = :idSo")
    , @NamedQuery(name = "SO.findByNombreSo", query = "SELECT s FROM SO s WHERE s.nombreSo = :nombreSo")})
public class SO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_so")
    private Integer idSo;
    @Column(name = "nombre_so")
    private String nombreSo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sOidSo", fetch = FetchType.LAZY)
    private List<Pc> pcList;

    public SO() {
    }

    public SO(Integer idSo) {
        this.idSo = idSo;
    }

    public Integer getIdSo() {
        return idSo;
    }

    public void setIdSo(Integer idSo) {
        this.idSo = idSo;
    }

    public String getNombreSo() {
        return nombreSo;
    }

    public void setNombreSo(String nombreSo) {
        this.nombreSo = nombreSo;
    }

    @XmlTransient
    public List<Pc> getPcList() {
        return pcList;
    }

    public void setPcList(List<Pc> pcList) {
        this.pcList = pcList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSo != null ? idSo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SO)) {
            return false;
        }
        SO other = (SO) object;
        if ((this.idSo == null && other.idSo != null) || (this.idSo != null && !this.idSo.equals(other.idSo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SO[ idSo=" + idSo + " ]";
    }
    
}
