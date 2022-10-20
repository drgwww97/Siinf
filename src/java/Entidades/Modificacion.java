/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David Ruiz
 */
@Entity
@Table(name = "modificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modificacion.findAll", query = "SELECT m FROM Modificacion m"),
    @NamedQuery(name = "Modificacion.findByFecha", query = "SELECT m FROM Modificacion m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "Modificacion.findByTecnico", query = "SELECT m FROM Modificacion m WHERE m.tecnico = :tecnico"),
    @NamedQuery(name = "Modificacion.findByMotivo", query = "SELECT m FROM Modificacion m WHERE m.motivo = :motivo"),
    @NamedQuery(name = "Modificacion.findByTModificacionidTmodificacion", query = "SELECT m FROM Modificacion m WHERE m.tModificacionidTmodificacion = :tModificacionidTmodificacion")})
public class Modificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 255)
    @Column(name = "tecnico")
    private String tecnico;
    @Size(max = 255)
    @Column(name = "motivo")
    private String motivo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_modificacionid_tmodificacion")
    private Integer tModificacionidTmodificacion;
    @JoinColumn(name = "t_modificacionid_tmodificacion", referencedColumnName = "id_tmodificacion", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TModificacion tModificacion;
    @OneToMany(mappedBy = "modificaciontModificacionidTmodificacion")
    private List<Pc> pcList;

    public Modificacion() {
    }

    public Modificacion(Integer tModificacionidTmodificacion) {
        this.tModificacionidTmodificacion = tModificacionidTmodificacion;
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

    public TModificacion getTModificacion() {
        return tModificacion;
    }

    public void setTModificacion(TModificacion tModificacion) {
        this.tModificacion = tModificacion;
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
