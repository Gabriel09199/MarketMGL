/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puntodeventa.estructure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "productos")
public class Producto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_producto")
    private Integer idProducto;
    
    @Basic(optional = false)
    @Column(name = "nombre_producto")
    private String nombreProducto;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "costo")
    private int costo;
    
    @Basic(optional = false)
    @Column(name = "valor_unitario")
    private int valorUnitario;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fKProducto")
    private List<VentaActual> ventaactualList;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_categoria", referencedColumnName = "id_categoria")
    private Categoria fKCategoria;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_proveedor", referencedColumnName = "id_proveedor")
    private Proveedor fKProveedor;

    public Producto()
    {
    	
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Integer idProducto, String nombreProducto, int costo, int valorUnitario) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.costo = costo;
        this.valorUnitario = valorUnitario;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(int valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @XmlTransient
    public List<VentaActual> getVentaactualList() {
        return ventaactualList;
    }

    public void setVentaactualList(List<VentaActual> ventaactualList) {
        this.ventaactualList = ventaactualList;
    }

    public Categoria getFKCategoria() {
        return fKCategoria;
    }

    public void setFKCategoria(Categoria fKCategoria) {
        this.fKCategoria = fKCategoria;
    }

    public Proveedor getFKProveedor() {
        return fKProveedor;
    }

    public void setFKProveedor(Proveedor fKProveedor) {
        this.fKProveedor = fKProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", descripcion="
				+ descripcion + ", costo=" + costo + ", valorUnitario=" + valorUnitario + ", fKCategoria=" + fKCategoria
				+ ", fKProveedor=" + fKProveedor + "]";
	}
}
