/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puntodeventa.estructure;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "clientes")

public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    @Basic(optional = false)
    @Column(name = "cedula")
    private int cedula;
    
    @Basic(optional = false)
    @Column(name = "primer_nombre")
    private String primerNombre;
    
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    
    @Basic(optional = false)
    @Column(name = "primer_apellido")
    private String primerApellido;
    
    @Basic(optional = false)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    
    @Basic(optional = false)
    @Column(name = "telefono")
    private int telefono;
    
    @Basic(optional = false)
    @Column(name = "genero")
    private short genero;
    
    @OneToMany(mappedBy = "fKCliente")
    @JsonIgnore
    private List<Venta> ventasList;

    public Cliente() {
    }
    
    public Cliente(Integer idCliente, int cedula, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, int telefono, short genero) {
		super();
		this.idCliente = idCliente;
		this.cedula = cedula;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.telefono = telefono;
		this.genero = genero;
	}



	public Cliente(int cedula, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			int telefono, short genero) {
		super();
		this.cedula = cedula;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.telefono = telefono;
		this.genero = genero;
	}

	public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public short getGenero() {
		return genero;
	}

	public void setGenero(short genero) {
		this.genero = genero;
	}
	
	public String getNameGenero() {
		if(genero == 1)
		{
			return "Hombre";
		}
		else
		{
			return "Mujer";
		}
	}
	
	public String getNombresApellidos()
	{
		if(segundoNombre == null)
		{
			return primerNombre + " " + primerApellido + " " + segundoApellido;
		}
		else
		{
			return primerNombre + " " + segundoNombre + " " + primerApellido + " " + segundoApellido;
		}
	}

	public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public List<Venta> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<Venta> ventasList) {
        this.ventasList = ventasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Clientes [idCliente=" + idCliente + ", cedula=" + cedula + ", primerNombre=" + primerNombre
				+ ", segundoNombre=" + segundoNombre + ", primerApellido=" + primerApellido + ", segundoApellido="
				+ segundoApellido + ", telefono=" + telefono + ",]";
	}
}
