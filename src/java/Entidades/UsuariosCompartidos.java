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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "usuarios_compartidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariosCompartidos.findAll", query = "SELECT u FROM UsuariosCompartidos u")
    , @NamedQuery(name = "UsuariosCompartidos.findByNombreUsuariocomp", query = "SELECT u FROM UsuariosCompartidos u WHERE u.nombreUsuariocomp = :nombreUsuariocomp")})
public class UsuariosCompartidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_usuariocomp")
    private String nombreUsuariocomp;
    @JoinTable(name = "Usuarios Compartidos_Impresora", joinColumns = {
        @JoinColumn(name = "usuarios_compartidosnombre_usuariocomp", referencedColumnName = "nombre_usuariocomp")}, inverseJoinColumns = {
        @JoinColumn(name = "impresorano_inventario", referencedColumnName = "no_inventario")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Impresora> impresoraList;

    public UsuariosCompartidos() {
    }

    public UsuariosCompartidos(String nombreUsuariocomp) {
        this.nombreUsuariocomp = nombreUsuariocomp;
    }

    public String getNombreUsuariocomp() {
        return nombreUsuariocomp;
    }

    public void setNombreUsuariocomp(String nombreUsuariocomp) {
        this.nombreUsuariocomp = nombreUsuariocomp;
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
        hash += (nombreUsuariocomp != null ? nombreUsuariocomp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariosCompartidos)) {
            return false;
        }
        UsuariosCompartidos other = (UsuariosCompartidos) object;
        if ((this.nombreUsuariocomp == null && other.nombreUsuariocomp != null) || (this.nombreUsuariocomp != null && !this.nombreUsuariocomp.equals(other.nombreUsuariocomp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.UsuariosCompartidos[ nombreUsuariocomp=" + nombreUsuariocomp + " ]";
    }
    
}
