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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "modelo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modelo.findAll", query = "SELECT m FROM Modelo m")
    , @NamedQuery(name = "Modelo.findByIdModelo", query = "SELECT m FROM Modelo m WHERE m.idModelo = :idModelo")
    , @NamedQuery(name = "Modelo.findByNombreModelo", query = "SELECT m FROM Modelo m WHERE m.nombreModelo = :nombreModelo")})
public class Modelo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_modelo")
    private Integer idModelo;
    @Column(name = "nombre_modelo")
    private String nombreModelo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modeloidModelo", fetch = FetchType.LAZY)
    private List<Accesorio> accesorioList;
    @OneToMany(mappedBy = "modeloidModelo", fetch = FetchType.LAZY)
    private List<Componente> componenteList;
    @JoinColumn(name = "t_tonnersn_tonner", referencedColumnName = "sn_tonner")
    @ManyToOne(fetch = FetchType.LAZY)
    private TTonner tTonnersnTonner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modeloidModelo", fetch = FetchType.LAZY)
    private List<Impresora> impresoraList;
    @OneToMany(mappedBy = "modeloidModelo", fetch = FetchType.LAZY)
    private List<Pc> pcList;

    public Modelo() {
    }

    public Modelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
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

    public TTonner getTTonnersnTonner() {
        return tTonnersnTonner;
    }

    public void setTTonnersnTonner(TTonner tTonnersnTonner) {
        this.tTonnersnTonner = tTonnersnTonner;
    }

    @XmlTransient
    public List<Impresora> getImpresoraList() {
        return impresoraList;
    }

    public void setImpresoraList(List<Impresora> impresoraList) {
        this.impresoraList = impresoraList;
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
        hash += (idModelo != null ? idModelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modelo)) {
            return false;
        }
        Modelo other = (Modelo) object;
        if ((this.idModelo == null && other.idModelo != null) || (this.idModelo != null && !this.idModelo.equals(other.idModelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Modelo[ idModelo=" + idModelo + " ]";
    }
    
}
