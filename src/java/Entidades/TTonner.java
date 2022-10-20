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
@Table(name = "t_tonner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TTonner.findAll", query = "SELECT t FROM TTonner t"),
    @NamedQuery(name = "TTonner.findBySnTonner", query = "SELECT t FROM TTonner t WHERE t.snTonner = :snTonner"),
    @NamedQuery(name = "TTonner.findByTipo", query = "SELECT t FROM TTonner t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "TTonner.findByCantidad", query = "SELECT t FROM TTonner t WHERE t.cantidad = :cantidad")})
public class TTonner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "sn_tonner")
    private String snTonner;
    @Size(max = 255)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "cantidad")
    private Integer cantidad;
    @OneToMany(mappedBy = "tTonnersnTonner")
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
