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
@Table(name = "pc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pc.findAll", query = "SELECT p FROM Pc p"),
    @NamedQuery(name = "Pc.findByNoInventario", query = "SELECT p FROM Pc p WHERE p.noInventario = :noInventario"),
    @NamedQuery(name = "Pc.findByResponsable", query = "SELECT p FROM Pc p WHERE p.responsable = :responsable"),
    @NamedQuery(name = "Pc.findBySnPc", query = "SELECT p FROM Pc p WHERE p.snPc = :snPc")})
public class Pc implements Serializable {

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
    @Size(max = 255)
    @Column(name = "sn_pc")
    private String snPc;
    @JoinColumn(name = "componentesn_componente", referencedColumnName = "sn_componente")
    @ManyToOne
    private Componente componentesnComponente;
    @JoinColumn(name = "accesoriosn_accesorio", referencedColumnName = "sn_accesorio")
    @ManyToOne
    private Accesorio accesoriosnAccesorio;
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
    @JoinColumn(name = "modificaciont_modificacionid_tmodificacion", referencedColumnName = "t_modificacionid_tmodificacion")
    @ManyToOne
    private Modificacion modificaciontModificacionidTmodificacion;
    @JoinColumn(name = "programasid_programas", referencedColumnName = "id_programas")
    @ManyToOne
    private Programas programasidProgramas;
    @JoinColumn(name = "s_oid_so", referencedColumnName = "id_so")
    @ManyToOne(optional = false)
    private SO sOidSo;
    @JoinColumn(name = "t_equipoid_equipo", referencedColumnName = "id_equipo")
    @ManyToOne(optional = false)
    private TEquipo tEquipoidEquipo;

    public Pc() {
    }

    public Pc(String noInventario) {
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

    public String getSnPc() {
        return snPc;
    }

    public void setSnPc(String snPc) {
        this.snPc = snPc;
    }

    public Componente getComponentesnComponente() {
        return componentesnComponente;
    }

    public void setComponentesnComponente(Componente componentesnComponente) {
        this.componentesnComponente = componentesnComponente;
    }

    public Accesorio getAccesoriosnAccesorio() {
        return accesoriosnAccesorio;
    }

    public void setAccesoriosnAccesorio(Accesorio accesoriosnAccesorio) {
        this.accesoriosnAccesorio = accesoriosnAccesorio;
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

    public Modificacion getModificaciontModificacionidTmodificacion() {
        return modificaciontModificacionidTmodificacion;
    }

    public void setModificaciontModificacionidTmodificacion(Modificacion modificaciontModificacionidTmodificacion) {
        this.modificaciontModificacionidTmodificacion = modificaciontModificacionidTmodificacion;
    }

    public Programas getProgramasidProgramas() {
        return programasidProgramas;
    }

    public void setProgramasidProgramas(Programas programasidProgramas) {
        this.programasidProgramas = programasidProgramas;
    }

    public SO getSOidSo() {
        return sOidSo;
    }

    public void setSOidSo(SO sOidSo) {
        this.sOidSo = sOidSo;
    }

    public TEquipo getTEquipoidEquipo() {
        return tEquipoidEquipo;
    }

    public void setTEquipoidEquipo(TEquipo tEquipoidEquipo) {
        this.tEquipoidEquipo = tEquipoidEquipo;
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
        if (!(object instanceof Pc)) {
            return false;
        }
        Pc other = (Pc) object;
        if ((this.noInventario == null && other.noInventario != null) || (this.noInventario != null && !this.noInventario.equals(other.noInventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pc[ noInventario=" + noInventario + " ]";
    }
    
}
