/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puntodeventa.estructure;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author GabrielPC
 */

@Entity
@Table(name = "tiposusuario")
public class TipoUsuario implements Serializable {
	
	public final static String SUPER_ADMINISTRADOR = "Super Admin";
	public final static String ADMINISTRADOR = "Administrador";
	public final static String EMPLEADO = "Empleado";
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_usuario")
    private Short idTipoUsuario;
    
    @Basic(optional = false)
    @Column(name = "nombre_estado")
    private String nombreEstado;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fKTipoUsuario")
    @JsonIgnore
    private List<Usuario> usuariosList;

    public TipoUsuario() {
    }

    public TipoUsuario(Short idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public TipoUsuario(Short idTipoUsuario, String nombreEstado) {
        this.idTipoUsuario = idTipoUsuario;
        this.nombreEstado = nombreEstado;
    }

    public Short getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Short idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    @XmlTransient
    public List<Usuario> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuario> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoUsuario != null ? idTipoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.idTipoUsuario == null && other.idTipoUsuario != null) || (this.idTipoUsuario != null && !this.idTipoUsuario.equals(other.idTipoUsuario))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return  nombreEstado;
	}
}
