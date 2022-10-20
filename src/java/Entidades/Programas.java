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
@Table(name = "programas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programas.findAll", query = "SELECT p FROM Programas p"),
    @NamedQuery(name = "Programas.findByIdProgramas", query = "SELECT p FROM Programas p WHERE p.idProgramas = :idProgramas"),
    @NamedQuery(name = "Programas.findByNombreProg", query = "SELECT p FROM Programas p WHERE p.nombreProg = :nombreProg"),
    @NamedQuery(name = "Programas.findByVersion", query = "SELECT p FROM Programas p WHERE p.version = :version")})
public class Programas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_programas")
    private Integer idProgramas;
    @Size(max = 255)
    @Column(name = "nombre_prog")
    private String nombreProg;
    @Size(max = 255)
    @Column(name = "version")
    private String version;
    @OneToMany(mappedBy = "programasidProgramas")
    private List<Pc> pcList;

    public Programas() {
    }

    public Programas(Integer idProgramas) {
        this.idProgramas = idProgramas;
    }

    public Integer getIdProgramas() {
        return idProgramas;
    }

    public void setIdProgramas(Integer idProgramas) {
        this.idProgramas = idProgramas;
    }

    public String getNombreProg() {
        return nombreProg;
    }

    public void setNombreProg(String nombreProg) {
        this.nombreProg = nombreProg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
        hash += (idProgramas != null ? idProgramas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programas)) {
            return false;
        }
        Programas other = (Programas) object;
        if ((this.idProgramas == null && other.idProgramas != null) || (this.idProgramas != null && !this.idProgramas.equals(other.idProgramas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Programas[ idProgramas=" + idProgramas + " ]";
    }
    
}
