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
@Table(name = "componente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Componente.findAll", query = "SELECT c FROM Componente c"),
    @NamedQuery(name = "Componente.findBySnComponente", query = "SELECT c FROM Componente c WHERE c.snComponente = :snComponente"),
    @NamedQuery(name = "Componente.findByCapacidad", query = "SELECT c FROM Componente c WHERE c.capacidad = :capacidad"),
    @NamedQuery(name = "Componente.findByEstado", query = "SELECT c FROM Componente c WHERE c.estado = :estado")})
public class Componente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "sn_componente")
    private String snComponente;
    @Size(max = 255)
    @Column(name = "capacidad")
    private String capacidad;
    @Size(max = 255)
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "componentesnComponente")
    private List<Pc> pcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componentesnComponente")
    private List<Almacen> almacenList;
    @JoinColumn(name = "marcaid_marca", referencedColumnName = "id_marca")
    @ManyToOne(optional = false)
    private Marca marcaidMarca;
    @JoinColumn(name = "modeloid_modelo", referencedColumnName = "id_modelo")
    @ManyToOne(optional = false)
    private Modelo modeloidModelo;
    @JoinColumn(name = "t_componenteid_componente", referencedColumnName = "id_componente")
    @ManyToOne(optional = false)
    private TComponente tComponenteidComponente;
    @JoinColumn(name = "t_conexionid_conexion", referencedColumnName = "id_conexion")
    @ManyToOne(optional = false)
    private TConexion tConexionidConexion;

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

    @XmlTransient
    public List<Almacen> getAlmacenList() {
        return almacenList;
    }

    public void setAlmacenList(List<Almacen> almacenList) {
        this.almacenList = almacenList;
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
