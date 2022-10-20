/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David Ruiz
 */
@Entity
@Table(name = "t_equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TEquipo.findAll", query = "SELECT t FROM TEquipo t"),
    @NamedQuery(name = "TEquipo.findByIdEquipo", query = "SELECT t FROM TEquipo t WHERE t.idEquipo = :idEquipo"),
    @NamedQuery(name = "TEquipo.findByNombreEquipo", query = "SELECT t FROM TEquipo t WHERE t.nombreEquipo = :nombreEquipo")})
public class TEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "nombre_equipo")
    private Integer nombreEquipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tEquipoidEquipo")
    private List<Pc> pcList;

    public TEquipo() {
    }

    public TEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Integer getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(Integer nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
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
        hash += (idEquipo != null ? idEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TEquipo)) {
            return false;
        }
        TEquipo other = (TEquipo) object;
        if ((this.idEquipo == null && other.idEquipo != null) || (this.idEquipo != null && !this.idEquipo.equals(other.idEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TEquipo[ idEquipo=" + idEquipo + " ]";
    }
    
}
