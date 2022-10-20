/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "accesorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accesorio.findAll", query = "SELECT a FROM Accesorio a"),
    @NamedQuery(name = "Accesorio.findBySnAccesorio", query = "SELECT a FROM Accesorio a WHERE a.snAccesorio = :snAccesorio"),
    @NamedQuery(name = "Accesorio.findByEstado", query = "SELECT a FROM Accesorio a WHERE a.estado = :estado")})
public class Accesorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "sn_accesorio")
    private String snAccesorio;
    @Size(max = 255)
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "accesoriosnAccesorio")
    private List<Pc> pcList;
    @JoinColumn(name = "marcaid_marca", referencedColumnName = "id_marca")
    @ManyToOne(optional = false)
    private Marca marcaidMarca;
    @JoinColumn(name = "modeloid_modelo", referencedColumnName = "id_modelo")
    @ManyToOne(optional = false)
    private Modelo modeloidModelo;
    @JoinColumn(name = "t_accesorioid_accesorio", referencedColumnName = "id_accesorio")
    @ManyToOne(optional = false)
    private TAccesorio tAccesorioidAccesorio;
    @JoinColumn(name = "t_conexionid_conexion", referencedColumnName = "id_conexion")
    @ManyToOne(optional = false)
    private TConexion tConexionidConexion;
    @OneToMany(mappedBy = "accesoriosnAccesorio")
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Pc> getPcList() {
        return pcList;
    }

    public void setPcList(List<Pc> pcList) {
        this.pcList = pcList;
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
