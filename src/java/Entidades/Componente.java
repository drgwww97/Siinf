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
@Table(name = "componente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Componente.findAll", query = "SELECT c FROM Componente c")
    , @NamedQuery(name = "Componente.findBySnComponente", query = "SELECT c FROM Componente c WHERE c.snComponente = :snComponente")
    , @NamedQuery(name = "Componente.findByCapacidad", query = "SELECT c FROM Componente c WHERE c.capacidad = :capacidad")
    , @NamedQuery(name = "Componente.findByCantidad", query = "SELECT c FROM Componente c WHERE c.cantidad = :cantidad")})
public class Componente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sn_componente")
    private String snComponente;
    @Column(name = "capacidad")
    private String capacidad;
    @Column(name = "cantidad")
    private Integer cantidad;
    @OneToMany(mappedBy = "componentesnComponente", fetch = FetchType.LAZY)
    private List<Almacen> almacenList;
    @JoinColumn(name = "areaid_area", referencedColumnName = "id_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private Area areaidArea;
    @JoinColumn(name = "departamentoid_departamento", referencedColumnName = "id_departamento")
    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamentoidDepartamento;
    @JoinColumn(name = "entidadid_entidad", referencedColumnName = "id_entidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Entidad entidadidEntidad;
    @JoinColumn(name = "estadoid_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estado estadoidEstado;
    @JoinColumn(name = "marcaid_marca", referencedColumnName = "id_marca")
    @ManyToOne(fetch = FetchType.LAZY)
    private Marca marcaidMarca;
    @JoinColumn(name = "modeloid_modelo", referencedColumnName = "id_modelo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Modelo modeloidModelo;
    @JoinColumn(name = "pcno_inventario", referencedColumnName = "no_inventario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pc pcnoInventario;
    @JoinColumn(name = "t_componenteid_componente", referencedColumnName = "id_componente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TComponente tComponenteidComponente;
    @JoinColumn(name = "t_conexionid_conexion", referencedColumnName = "id_conexion")
    @ManyToOne(fetch = FetchType.LAZY)
    private TConexion tConexionidConexion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componentesnComponente", fetch = FetchType.LAZY)
    private List<Modificacion> modificacionList;

    public Componente() {
    }

    public Componente(String snComponente) {
        this.snComponente = snComponente;
    }

    public String getSnComponente() {
        return snComponente;
    }

    public void setSnComponente(String snComponente) {
        this.snComponente = snComponente;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @XmlTransient
    public List<Almacen> getAlmacenList() {
        return almacenList;
    }

    public void setAlmacenList(List<Almacen> almacenList) {
        this.almacenList = almacenList;
    }

    public Area getAreaidArea() {
        return areaidArea;
    }

    public void setAreaidArea(Area areaidArea) {
        this.areaidArea = areaidArea;
    }

    public Departamento getDepartamentoidDepartamento() {
        return departamentoidDepartamento;
    }

    public void setDepartamentoidDepartamento(Departamento departamentoidDepartamento) {
        this.departamentoidDepartamento = departamentoidDepartamento;
    }

    public Entidad getEntidadidEntidad() {
        return entidadidEntidad;
    }

    public void setEntidadidEntidad(Entidad entidadidEntidad) {
        this.entidadidEntidad = entidadidEntidad;
    }

    public Estado getEstadoidEstado() {
        return estadoidEstado;
    }

    public void setEstadoidEstado(Estado estadoidEstado) {
        this.estadoidEstado = estadoidEstado;
    }

    public Marca getMarcaidMarca() {
        return marcaidMarca;
    }

    public void setMarcaidMarca(Marca marcaidMarca) {
        this.marcaidMarca = marcaidMarca;
    }

    public Modelo getModeloidModelo() {
        return modeloidModelo;
    }

    public void setModeloidModelo(Modelo modeloidModelo) {
        this.modeloidModelo = modeloidModelo;
    }

    public Pc getPcnoInventario() {
        return pcnoInventario;
    }

    public void setPcnoInventario(Pc pcnoInventario) {
        this.pcnoInventario = pcnoInventario;
    }

    public TComponente getTComponenteidComponente() {
        return tComponenteidComponente;
    }

    public void setTComponenteidComponente(TComponente tComponenteidComponente) {
        this.tComponenteidComponente = tComponenteidComponente;
    }

    public TConexion getTConexionidConexion() {
        return tConexionidConexion;
    }

    public void setTConexionidConexion(TConexion tConexionidConexion) {
        this.tConexionidConexion = tConexionidConexion;
    }

    @XmlTransient
    public List<Modificacion> getModificacionList() {
        return modificacionList;
    }

    public void setModificacionList(List<Modificacion> modificacionList) {
        this.modificacionList = modificacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (snComponente != null ? snComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Componente)) {
            return false;
        }
        Componente other = (Componente) object;
        if ((this.snComponente == null && other.snComponente != null) || (this.snComponente != null && !this.snComponente.equals(other.snComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Componente[ snComponente=" + snComponente + " ]";
    }
    
}
