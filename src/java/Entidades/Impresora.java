/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David Ruiz
 */
@Entity
@Table(name = "impresora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Impresora.findAll", query = "SELECT i FROM Impresora i"),
    @NamedQuery(name = "Impresora.findByNoInventario", query = "SELECT i FROM Impresora i WHERE i.noInventario = :noInventario"),
    @NamedQuery(name = "Impresora.findByResponsable", query = "SELECT i FROM Impresora i WHERE i.responsable = :responsable")})
public class Impresora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "no_inventario")
    private String noInventario;
    @Size(max = 255)
    @Column(name = "responsable")
    private String responsable;
    @JoinColumn(name = "areaid_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area areaidArea;
    @JoinColumn(name = "departamentoid_departamento", referencedColumnName = "id_departamento")
    @ManyToOne(optional = false)
    private Departamento departamentoidDepartamento;
    @JoinColumn(name = "entidadid_entidad", referencedColumnName = "id_entidad")
    @ManyToOne(optional = false)
    private Entidad entidadidEntidad;
    @JoinColumn(name = "marcaid_marca", referencedColumnName = "id_marca")
    @ManyToOne(optional = false)
    private Marca marcaidMarca;
    @JoinColumn(name = "modeloid_modelo", referencedColumnName = "id_modelo")
    @ManyToOne(optional = false)
    private Modelo modeloidModelo;
    @JoinColumn(name = "t_tonnersn_tonner", referencedColumnName = "sn_tonner")
    @ManyToOne
    private TTonner tTonnersnTonner;

    public Impresora() {
    }

    public Impresora(String noInventario) {
        this.noInventario = noInventario;
    }

    public String getNoInventario() {
        return noInventario;
    }

    public void setNoInventario(String noInventario) {
        this.noInventario = noInventario;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
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

    public TTonner getTTonnersnTonner() {
        return tTonnersnTonner;
    }

    public void setTTonnersnTonner(TTonner tTonnersnTonner) {
        this.tTonnersnTonner = tTonnersnTonner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noInventario != null ? noInventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Impresora)) {
            return false;
        }
        Impresora other = (Impresora) object;
        if ((this.noInventario == null && other.noInventario != null) || (this.noInventario != null && !this.noInventario.equals(other.noInventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Impresora[ noInventario=" + noInventario + " ]";
    }
    
}
