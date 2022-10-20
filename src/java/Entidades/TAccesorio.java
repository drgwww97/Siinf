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
@Table(name = "t_accesorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TAccesorio.findAll", query = "SELECT t FROM TAccesorio t"),
    @NamedQuery(name = "TAccesorio.findByIdAccesorio", query = "SELECT t FROM TAccesorio t WHERE t.idAccesorio = :idAccesorio"),
    @NamedQuery(name = "TAccesorio.findByNombreAccesorio", query = "SELECT t FROM TAccesorio t WHERE t.nombreAccesorio = :nombreAccesorio")})
public class TAccesorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_accesorio")
    private Integer idAccesorio;
    @Size(max = 255)
    @Column(name = "nombre_accesorio")
    private String nombreAccesorio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tAccesorioidAccesorio")
    private List<Accesorio> accesorioList;

    public TAccesorio() {
    }

    public TAccesorio(Integer idAccesorio) {
        this.idAccesorio = idAccesorio;
    }

    public Integer getIdAccesorio() {
        return idAccesorio;
    }

    public void setIdAccesorio(Integer idAccesorio) {
        this.idAccesorio = idAccesorio;
    }

    public String getNombreAccesorio() {
        return nombreAccesorio;
    }

    public void setNombreAccesorio(String nombreAccesorio) {
        this.nombreAccesorio = nombreAccesorio;
    }

    @XmlTransient
    public List<Accesorio> getAccesorioList() {
        return accesorioList;
    }

    public void setAccesorioList(List<Accesorio> accesorioList) {
        this.accesorioList = accesorioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccesorio != null ? idAccesorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TAccesorio)) {
            return false;
        }
        TAccesorio other = (TAccesorio) object;
        if ((this.idAccesorio == null && other.idAccesorio != null) || (this.idAccesorio != null && !this.idAccesorio.equals(other.idAccesorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TAccesorio[ idAccesorio=" + idAccesorio + " ]";
    }
    
}
