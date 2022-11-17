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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "almacen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almacen.findAll", query = "SELECT a FROM Almacen a")
    , @NamedQuery(name = "Almacen.findByFEntrada", query = "SELECT a FROM Almacen a WHERE a.fEntrada = :fEntrada")
    , @NamedQuery(name = "Almacen.findByFSalida", query = "SELECT a FROM Almacen a WHERE a.fSalida = :fSalida")
    , @NamedQuery(name = "Almacen.findByIdAlmacen", query = "SELECT a FROM Almacen a WHERE a.idAlmacen = :idAlmacen")})
public class Almacen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "f_entrada")
    @Temporal(TemporalType.DATE)
    private Date fEntrada;
    @Column(name = "f_salida")
    @Temporal(TemporalType.DATE)
    private Date fSalida;
    @Id
    @Basic(optional = false)
    @Column(name = "id_almacen")
    private Integer idAlmacen;
    @JoinColumn(name = "accesoriosn_accesorio", referencedColumnName = "sn_accesorio")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accesorio accesoriosnAccesorio;
    @JoinColumn(name = "componentesn_componente", referencedColumnName = "sn_componente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Componente componentesnComponente;

    public Almacen() {
    }

    public Almacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public Date getFEntrada() {
        return fEntrada;
    }

    public void setFEntrada(Date fEntrada) {
        this.fEntrada = fEntrada;
    }

    public Date getFSalida() {
        return fSalida;
    }

    public void setFSalida(Date fSalida) {
        this.fSalida = fSalida;
    }

    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public Accesorio getAccesoriosnAccesorio() {
        return accesoriosnAccesorio;
    }

    public void setAccesoriosnAccesorio(Accesorio accesoriosnAccesorio) {
        this.accesoriosnAccesorio = accesoriosnAccesorio;
    }

    public Componente getComponentesnComponente() {
        return componentesnComponente;
    }

    public void setComponentesnComponente(Componente componentesnComponente) {
        this.componentesnComponente = componentesnComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlmacen != null ? idAlmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Almacen)) {
            return false;
        }
        Almacen other = (Almacen) object;
        if ((this.idAlmacen == null && other.idAlmacen != null) || (this.idAlmacen != null && !this.idAlmacen.equals(other.idAlmacen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Almacen[ idAlmacen=" + idAlmacen + " ]";
    }
    
}
