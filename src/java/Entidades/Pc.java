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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "pc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pc.findAll", query = "SELECT p FROM Pc p")
    , @NamedQuery(name = "Pc.findByNoInventario", query = "SELECT p FROM Pc p WHERE p.noInventario = :noInventario")
    , @NamedQuery(name = "Pc.findByResponsable", query = "SELECT p FROM Pc p WHERE p.responsable = :responsable")
    , @NamedQuery(name = "Pc.findBySnPc", query = "SELECT p FROM Pc p WHERE p.snPc = :snPc")
    , @NamedQuery(name = "Pc.findByComponentesnComponente", query = "SELECT p FROM Pc p WHERE p.componentesnComponente = :componentesnComponente")
    , @NamedQuery(name = "Pc.findByAccesoriosnAccesorio", query = "SELECT p FROM Pc p WHERE p.accesoriosnAccesorio = :accesoriosnAccesorio")
    , @NamedQuery(name = "Pc.findByModificaciontModificacionidTmodificacion", query = "SELECT p FROM Pc p WHERE p.modificaciontModificacionidTmodificacion = :modificaciontModificacionidTmodificacion")
    , @NamedQuery(name = "Pc.findByProgramasidProgramas", query = "SELECT p FROM Pc p WHERE p.programasidProgramas = :programasidProgramas")
    , @NamedQuery(name = "Pc.findBySelloPc", query = "SELECT p FROM Pc p WHERE p.selloPc = :selloPc")})
public class Pc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_inventario")
    private String noInventario;
    @Column(name = "responsable")
    private String responsable;
    @Column(name = "sn_pc")
    private String snPc;
    @Column(name = "componentesn_componente")
    private String componentesnComponente;
    @Column(name = "accesoriosn_accesorio")
    private String accesoriosnAccesorio;
    @Column(name = "modificaciont_modificacionid_tmodificacion")
    private Integer modificaciontModificacionidTmodificacion;
    @Column(name = "programasid_programas")
    private Integer programasidProgramas;
    @Column(name = "sello_pc")
    private String selloPc;
    @JoinTable(name = "pc_programas", joinColumns = {
        @JoinColumn(name = "pcno_inventario", referencedColumnName = "no_inventario")}, inverseJoinColumns = {
        @JoinColumn(name = "programasid_programas", referencedColumnName = "id_programas")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Programas> programasList;
    @OneToMany(mappedBy = "pcnoInventario", fetch = FetchType.LAZY)
    private List<Accesorio> accesorioList;
    @OneToMany(mappedBy = "pcnoInventario", fetch = FetchType.LAZY)
    private List<Componente> componenteList;
    @OneToMany(mappedBy = "pcnoInventario", fetch = FetchType.LAZY)
    private List<Modificacion> modificacionList;
    @JoinColumn(name = "areaid_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Area areaidArea;
    @JoinColumn(name = "departamentoid_departamento", referencedColumnName = "id_departamento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Departamento departamentoidDepartamento;
    @JoinColumn(name = "entidadid_entidad", referencedColumnName = "id_entidad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Entidad entidadidEntidad;
    @JoinColumn(name = "estadoid_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estado estadoidEstado;
    @JoinColumn(name = "marcaid_marca", referencedColumnName = "id_marca")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Marca marcaidMarca;
    @JoinColumn(name = "modeloid_modelo", referencedColumnName = "id_modelo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Modelo modeloidModelo;
    @JoinColumn(name = "s_oid_so", referencedColumnName = "id_so")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SO sOidSo;
    @JoinColumn(name = "t_equipoid_equipo", referencedColumnName = "id_equipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
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

    public String getComponentesnComponente() {
        return componentesnComponente;
    }

    public void setComponentesnComponente(String componentesnComponente) {
        this.componentesnComponente = componentesnComponente;
    }

    public String getAccesoriosnAccesorio() {
        return accesoriosnAccesorio;
    }

    public void setAccesoriosnAccesorio(String accesoriosnAccesorio) {
        this.accesoriosnAccesorio = accesoriosnAccesorio;
    }

    public Integer getModificaciontModificacionidTmodificacion() {
        return modificaciontModificacionidTmodificacion;
    }

    public void setModificaciontModificacionidTmodificacion(Integer modificaciontModificacionidTmodificacion) {
        this.modificaciontModificacionidTmodificacion = modificaciontModificacionidTmodificacion;
    }

    public Integer getProgramasidProgramas() {
        return programasidProgramas;
    }

    public void setProgramasidProgramas(Integer programasidProgramas) {
        this.programasidProgramas = programasidProgramas;
    }

    public String getSelloPc() {
        return selloPc;
    }

    public void setSelloPc(String selloPc) {
        this.selloPc = selloPc;
    }

    @XmlTransient
    public List<Programas> getProgramasList() {
        return programasList;
    }

    public void setProgramasList(List<Programas> programasList) {
        this.programasList = programasList;
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
    public List<Modificacion> getModificacionList() {
        return modificacionList;
    }

    public void setModificacionList(List<Modificacion> modificacionList) {
        this.modificacionList = modificacionList;
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
