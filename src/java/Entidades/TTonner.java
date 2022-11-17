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
@Table(name = "t_tonner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TTonner.findAll", query = "SELECT t FROM TTonner t")
    , @NamedQuery(name = "TTonner.findBySnTonner", query = "SELECT t FROM TTonner t WHERE t.snTonner = :snTonner")
    , @NamedQuery(name = "TTonner.findByTipoToner", query = "SELECT t FROM TTonner t WHERE t.tipoToner = :tipoToner")
    , @NamedQuery(name = "TTonner.findByCantidadToner", query = "SELECT t FROM TTonner t WHERE t.cantidadToner = :cantidadToner")})
public class TTonner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sn_tonner")
    private String snTonner;
    @Column(name = "tipo_toner")
    private String tipoToner;
    @Column(name = "cantidad_toner")
    private Integer cantidadToner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tTonnersnTonner", fetch = FetchType.LAZY)
    private List<RegDatosToner> regDatosTonerList;
    @OneToMany(mappedBy = "tTonnersnTonner", fetch = FetchType.LAZY)
    private List<Modelo> modeloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tTonnersnTonner", fetch = FetchType.LAZY)
    private List<Impresora> impresoraList;

    public TTonner() {
    }

    public TTonner(String snTonner) {
        this.snTonner = snTonner;
    }

    public String getSnTonner() {
        return snTonner;
    }

    public void setSnTonner(String snTonner) {
        this.snTonner = snTonner;
    }

    public String getTipoToner() {
        return tipoToner;
    }

    public void setTipoToner(String tipoToner) {
        this.tipoToner = tipoToner;
    }

    public Integer getCantidadToner() {
        return cantidadToner;
    }

    public void setCantidadToner(Integer cantidadToner) {
        this.cantidadToner = cantidadToner;
    }

    @XmlTransient
    public List<RegDatosToner> getRegDatosTonerList() {
        return regDatosTonerList;
    }

    public void setRegDatosTonerList(List<RegDatosToner> regDatosTonerList) {
        this.regDatosTonerList = regDatosTonerList;
    }

    @XmlTransient
    public List<Modelo> getModeloList() {
        return modeloList;
    }

    public void setModeloList(List<Modelo> modeloList) {
        this.modeloList = modeloList;
    }

    @XmlTransient
    public List<Impresora> getImpresoraList() {
        return impresoraList;
    }

    public void setImpresoraList(List<Impresora> impresoraList) {
        this.impresoraList = impresoraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (snTonner != null ? snTonner.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TTonner)) {
            return false;
        }
        TTonner other = (TTonner) object;
        if ((this.snTonner == null && other.snTonner != null) || (this.snTonner != null && !this.snTonner.equals(other.snTonner))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TTonner[ snTonner=" + snTonner + " ]";
    }
    
}
