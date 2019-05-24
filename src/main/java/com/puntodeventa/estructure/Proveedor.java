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
@Table(name = "proveedores")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    
    @Basic(optional = false)
    @Column(name = "nombre_compania")
    private String nombreCompania;
    
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
    
    @Column(name = "correo_electronico")
    private String correoElectronico;
    
    @Column(name = "direccion")
    private String direccion;
    
    @Basic(optional = false)
    @Column(name = "telefono")
    private int telefono;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fKProveedor")
    @JsonIgnore
    private List<Producto> productosList;

    public Proveedor() {
    }

    public Proveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Proveedor(Integer idProveedor, String nombreCompania, String primerNombre, String primerApellido, String segundoApellido, int telefono) {
        this.idProveedor = idProveedor;
        this.nombreCompania = nombreCompania;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreCompania() {
        return nombreCompania;
    }

    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania = nombreCompania;
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

    public String getCorreoElectronico() {
        if(correoElectronico == null)
        {
        	return "El proveedor no tiene correo";
        }
        else
        {
        	return correoElectronico;
        }
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
    	if(direccion == null)
        {
        	return "El proveedor no tiene direccion";
        }
        else
        {
        	return direccion;
        }
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public List<Producto> getProductosList() {
        return productosList;
    }

    public void setProductosList(List<Producto> productosList) {
        this.productosList = productosList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Proveedores [idProveedor=" + idProveedor + ", nombreCompania=" + nombreCompania + ", primerNombre="
				+ primerNombre + ", segundoNombre=" + segundoNombre + ", primerApellido=" + primerApellido
				+ ", segundoApellido=" + segundoApellido + ", correoElectronico=" + correoElectronico + ", direccion="
				+ direccion + ", telefono=" + telefono + ", productosList=" + productosList + "]";
	}

    
    
}
