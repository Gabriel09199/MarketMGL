package com.puntodeventa.estructure;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GabrielPC
 */
@Entity
@Table(name = "ventaactual")
public class VentaActual implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_venta_actual")
    private Integer idVentaActual;
    
    @Basic(optional = false)
    @Column(name = "precio_venta")
    private int precioVenta;
    
    @Basic(optional = false)
    @Column(name = "cantidad")
    private short cantidad;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_producto", referencedColumnName = "id_producto")
    private Producto fKProducto;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_venta", referencedColumnName = "id_venta")
    private Venta fKVenta;

    public VentaActual() {
    }

    public VentaActual(Integer idVentaActual) {
        this.idVentaActual = idVentaActual;
    }

    public VentaActual(Integer idVentaActual, int precioVenta, short cantidad) {
        this.idVentaActual = idVentaActual;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
    }

    public Integer getIdVentaActual() {
        return idVentaActual;
    }

    public void setIdVentaActual(Integer idVentaActual) {
        this.idVentaActual = idVentaActual;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public short getCantidad() {
        return cantidad;
    }

    public void setCantidad(short cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getFKProducto() {
        return fKProducto;
    }

    public void setFKProducto(Producto fKProducto) {
        this.fKProducto = fKProducto;
    }

    public Venta getFKVenta() {
        return fKVenta;
    }

    public void setFKVenta(Venta fKVenta) {
        this.fKVenta = fKVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVentaActual != null ? idVentaActual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaActual)) {
            return false;
        }
        VentaActual other = (VentaActual) object;
        if ((this.idVentaActual == null && other.idVentaActual != null) || (this.idVentaActual != null && !this.idVentaActual.equals(other.idVentaActual))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "VentaActual [idVentaActual=" + idVentaActual + ", precioVenta=" + precioVenta + ", cantidad=" + cantidad
				+ "]";
	}

	
}
