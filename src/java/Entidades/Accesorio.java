/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "accesorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accesorio.findAll", query = "SELECT a FROM Accesorio a")
    , @NamedQuery(name = "Accesorio.findBySnAccesorio", query = "SELECT a FROM Accesorio a WHERE a.snAccesorio = :snAccesorio")
    , @NamedQuery(name = "Accesorio.findByCantidad", query = "SELECT a FROM Accesorio a WHERE a.cantidad = :cantidad")})
public class Accesorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sn_accesorio")
    private String snAccesorio;
    @Column(name = "cantidad")
    private Integer cantidad;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Marca marcaidMarca;
    @JoinColumn(name = "modeloid_modelo", referencedColumnName = "id_modelo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Modelo modeloidModelo;
    @JoinColumn(name = "pcno_inventario", referencedColumnName = "no_inventario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pc pcnoInventario;
    @JoinColumn(name = "t_accesorioid_accesorio", referencedColumnName = "id_accesorio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TAccesorio tAccesorioidAccesorio;
    @JoinColumn(name = "t_conexionid_conexion", referencedColumnName = "id_conexion")
    @ManyToOne(fetch = FetchType.LAZY)
    private TConexion tConexionidConexion;
    @OneToMany(mappedBy = "accesoriosnAccesorio", fetch = FetchType.LAZY)
    private List<Almacen> almacenList;

    public Accesorio() {
    }

    public Accesorio(String snAccesorio) {
        this.snAccesorio = snAccesorio;
    }

    public String getSnAccesorio() {
        return snAccesorio;
    }

    public void setSnAccesorio(String snAccesorio) {
        this.snAccesorio = snAccesorio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public TAccesorio getTAccesorioidAccesorio() {
        return tAccesorioidAccesorio;
    }

    public void setTAccesorioidAccesorio(TAccesorio tAccesorioidAccesorio) {
        this.tAccesorioidAccesorio = tAccesorioidAccesorio;
    }

    public TConexion getTConexionidConexion() {
        return tConexionidConexion;
    }

    public void setTConexionidConexion(TConexion tConexionidConexion) {
        this.tConexionidConexion = tConexionidConexion;
    }

    @XmlTransient
    public List<Almacen> getAlmacenList() {
        return almacenList;
    }

    public void setAlmacenList(List<Almacen> almacenList) {
        this.almacenList = almacenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (snAccesorio != null ? snAccesorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accesorio)) {
            return false;
        }
        Accesorio other = (Accesorio) object;
        if ((this.snAccesorio == null && other.snAccesorio != null) || (this.snAccesorio != null && !this.snAccesorio.equals(other.snAccesorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Accesorio[ snAccesorio=" + snAccesorio + " ]";
    }
    
}
