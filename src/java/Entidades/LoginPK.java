/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author David
 */
@Embeddable
public class LoginPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "usuariousuario")
    private String usuariousuario;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    public LoginPK() {
    }

    public LoginPK(String usuariousuario, String password) {
        this.usuariousuario = usuariousuario;
        this.password = password;
    }

    public String getUsuariousuario() {
        return usuariousuario;
    }

    public void setUsuariousuario(String usuariousuario) {
        this.usuariousuario = usuariousuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuariousuario != null ? usuariousuario.hashCode() : 0);
        hash += (password != null ? password.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginPK)) {
            return false;
        }
        LoginPK other = (LoginPK) object;
        if ((this.usuariousuario == null && other.usuariousuario != null) || (this.usuariousuario != null && !this.usuariousuario.equals(other.usuariousuario))) {
            return false;
        }
        if ((this.password == null && other.password != null) || (this.password != null && !this.password.equals(other.password))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.LoginPK[ usuariousuario=" + usuariousuario + ", password=" + password + " ]";
    }
    
}
