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
@Table(name = "t_componente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TComponente.findAll", query = "SELECT t FROM TComponente t"),
    @NamedQuery(name = "TComponente.findByIdComponente", query = "SELECT t FROM TComponente t WHERE t.idComponente = :idComponente"),
    @NamedQuery(name = "TComponente.findByNombreComponente", query = "SELECT t FROM TComponente t WHERE t.nombreComponente = :nombreComponente")})
public class TComponente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_componente")
    private Integer idComponente;
    @Size(max = 255)
    @Column(name = "nombre_componente")
    private String nombreComponente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tComponenteidComponente")
    private List<Componente> componenteList;

    public TComponente() {
    }

    public TComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getNombreComponente() {
        return nombreComponente;
    }

    public void setNombreComponente(String nombreComponente) {
        this.nombreComponente = nombreComponente;
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
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TComponente)) {
            return false;
        }
        TComponente other = (TComponente) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TComponente[ idComponente=" + idComponente + " ]";
    }
    
}
