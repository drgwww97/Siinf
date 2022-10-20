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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David Ruiz
 */
@Entity
@Table(name = "t_conexion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TConexion.findAll", query = "SELECT t FROM TConexion t"),
    @NamedQuery(name = "TConexion.findByIdConexion", query = "SELECT t FROM TConexion t WHERE t.idConexion = :idConexion"),
    @NamedQuery(name = "TConexion.findByNombreConexion", query = "SELECT t FROM TConexion t WHERE t.nombreConexion = :nombreConexion")})
public class TConexion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_conexion")
    private Integer idConexion;
    @Size(max = 255)
    @Column(name = "nombre_conexion")
    private String nombreConexion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tConexionidConexion")
    private List<Accesorio> accesorioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tConexionidConexion")
    private List<Componente> componenteList;

    public TConexion() {
    }

    public TConexion(Integer idConexion) {
        this.idConexion = idConexion;
    }

    public Integer getIdConexion() {
        return idConexion;
    }

    public void setIdConexion(Integer idConexion) {
        this.idConexion = idConexion;
    }

    public String getNombreConexion() {
        return nombreConexion;
    }

    public void setNombreConexion(String nombreConexion) {
        this.nombreConexion = nombreConexion;
    }

    @XmlTransient
    public List<Accesorio> getAccesorioList() {
        return accesorioList;
    }

    public void setAccesorioList(List<Accesorio> accesorioList) {
        this.accesorioList = accesorioList;
    }

    @XmlTransient
    public List<Componente> getComponenteList() {
        return componenteList;
    }

    public void setComponenteList(List<Componente> componenteList) {
        this.componenteList = componenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConexion != null ? idConexion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TConexion)) {
            return false;
        }
        TConexion other = (TConexion) object;
        if ((this.idConexion == null && other.idConexion != null) || (this.idConexion != null && !this.idConexion.equals(other.idConexion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TConexion[ idConexion=" + idConexion + " ]";
    }
    
}
