/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "modificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modificacion.findAll", query = "SELECT m FROM Modificacion m")
    , @NamedQuery(name = "Modificacion.findByFecha", query = "SELECT m FROM Modificacion m WHERE m.fecha = :fecha")
    , @NamedQuery(name = "Modificacion.findByTecnico", query = "SELECT m FROM Modificacion m WHERE m.tecnico = :tecnico")
    , @NamedQuery(name = "Modificacion.findByMotivo", query = "SELECT m FROM Modificacion m WHERE m.motivo = :motivo")
    , @NamedQuery(name = "Modificacion.findByTModificacionidTmodificacion", query = "SELECT m FROM Modificacion m WHERE m.tModificacionidTmodificacion = :tModificacionidTmodificacion")
    , @NamedQuery(name = "Modificacion.findByComponenteNuevo", query = "SELECT m FROM Modificacion m WHERE m.componenteNuevo = :componenteNuevo")})
public class Modificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "tecnico")
    private String tecnico;
    @Column(name = "motivo")
    private String motivo;
    @Id
    @Basic(optional = false)
    @Column(name = "t_modificacionid_tmodificacion")
    private Integer tModificacionidTmodificacion;
    @Basic(optional = false)
    @Column(name = "componente_nuevo")
    private String componenteNuevo;
    @JoinColumn(name = "componentesn_componente", referencedColumnName = "sn_componente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Componente componentesnComponente;
    @JoinColumn(name = "pcno_inventario", referencedColumnName = "no_inventario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pc pcnoInventario;
    @JoinColumn(name = "t_modificacionid_tmodificacion", referencedColumnName = "id_tmodificacion", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private TModificacion tModificacion;

    public Modificacion() {
    }

    public Modificacion(Integer tModificacionidTmodificacion) {
        this.tModificacionidTmodificacion = tModificacionidTmodificacion;
    }

    public Modificacion(Integer tModificacionidTmodificacion, String componenteNuevo) {
        this.tModificacionidTmodificacion = tModificacionidTmodificacion;
        this.componenteNuevo = componenteNuevo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getTModificacionidTmodificacion() {
        return tModificacionidTmodificacion;
    }

    public void setTModificacionidTmodificacion(Integer tModificacionidTmodificacion) {
        this.tModificacionidTmodificacion = tModificacionidTmodificacion;
    }

    public String getComponenteNuevo() {
        return componenteNuevo;
    }

    public void setComponenteNuevo(String componenteNuevo) {
        this.componenteNuevo = componenteNuevo;
    }

    public Componente getComponentesnComponente() {
        return componentesnComponente;
    }

    public void setComponentesnComponente(Componente componentesnComponente) {
        this.componentesnComponente = componentesnComponente;
    }

    public Pc getPcnoInventario() {
        return pcnoInventario;
    }

    public void setPcnoInventario(Pc pcnoInventario) {
        this.pcnoInventario = pcnoInventario;
    }

    public TModificacion getTModificacion() {
        return tModificacion;
    }

    public void setTModificacion(TModificacion tModificacion) {
        this.tModificacion = tModificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tModificacionidTmodificacion != null ? tModificacionidTmodificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modificacion)) {
            return false;
        }
        Modificacion other = (Modificacion) object;
        if ((this.tModificacionidTmodificacion == null && other.tModificacionidTmodificacion != null) || (this.tModificacionidTmodificacion != null && !this.tModificacionidTmodificacion.equals(other.tModificacionidTmodificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Modificacion[ tModificacionidTmodificacion=" + tModificacionidTmodificacion + " ]";
    }
    
}
