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
@Table(name = "entidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entidad.findAll", query = "SELECT e FROM Entidad e")
    , @NamedQuery(name = "Entidad.findByIdEntidad", query = "SELECT e FROM Entidad e WHERE e.idEntidad = :idEntidad")
    , @NamedQuery(name = "Entidad.findByNombreEntidad", query = "SELECT e FROM Entidad e WHERE e.nombreEntidad = :nombreEntidad")
    , @NamedQuery(name = "Entidad.findByDireccionEntidad", query = "SELECT e FROM Entidad e WHERE e.direccionEntidad = :direccionEntidad")
    , @NamedQuery(name = "Entidad.findByNotelefono", query = "SELECT e FROM Entidad e WHERE e.notelefono = :notelefono")})
public class Entidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_entidad")
    private Integer idEntidad;
    @Column(name = "nombre_entidad")
    private String nombreEntidad;
    @Column(name = "direccion_entidad")
    private String direccionEntidad;
    @Column(name = "notelefono")
    private Integer notelefono;
    @OneToMany(mappedBy = "entidadidEntidad", fetch = FetchType.LAZY)
    private List<Accesorio> accesorioList;
    @OneToMany(mappedBy = "entidadidEntidad", fetch = FetchType.LAZY)
    private List<Componente> componenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadidEntidad", fetch = FetchType.LAZY)
    private List<Impresora> impresoraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadidEntidad", fetch = FetchType.LAZY)
    private List<Pc> pcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadidEntidad", fetch = FetchType.LAZY)
    private List<Departamento> departamentoList;

    public Entidad() {
    }

    public Entidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public Integer getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getDireccionEntidad() {
        return direccionEntidad;
    }

    public void setDireccionEntidad(String direccionEntidad) {
        this.direccionEntidad = direccionEntidad;
    }

    public Integer getNotelefono() {
        return notelefono;
    }

    public void setNotelefono(Integer notelefono) {
        this.notelefono = notelefono;
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

    @XmlTransient
    public List<Departamento> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<Departamento> departamentoList) {
        this.departamentoList = departamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntidad != null ? idEntidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidad)) {
            return false;
        }
        Entidad other = (Entidad) object;
        if ((this.idEntidad == null && other.idEntidad != null) || (this.idEntidad != null && !this.idEntidad.equals(other.idEntidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Entidad[ idEntidad=" + idEntidad + " ]";
    }
    
}
