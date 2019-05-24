package com.puntodeventa.estructure;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author GabrielPC
 */
@Entity
@Table(name = "ventas")
public class Venta implements Serializable {

	public final static String ACTIVO = "Activo";
	public final static String INACTIVO = "Inactivo";
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_venta")
    private Integer idVenta;
    
    @Basic(optional = false)
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta;
    
    @Basic(optional = false)
    @Column(name = "precio_total_venta")
    private int precioTotalVenta;
    
    @Basic(optional = false)
    @Column(name = "estado")
    private short estado;
    
    @ManyToOne
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id_cliente")
    private Cliente fKCliente;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    private Usuario fKUsuario;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fKVenta")
    @JsonIgnore
    private List<VentaActual> ventaactualList;

    public Venta() 
    {
    	
    }
    
    public Venta(Date fechaVenta, int precioTotalVenta, short estado, Usuario fKUsuario) {
		super();
		this.fechaVenta = fechaVenta;
		this.precioTotalVenta = precioTotalVenta;
		this.estado = estado;
		this.fKUsuario = fKUsuario;
	}

	public Venta(Date fechaVenta, int precioTotalVenta, short estado, Cliente fKCliente, Usuario fKUsuario) {
		super();
		this.fechaVenta = fechaVenta;
		this.precioTotalVenta = precioTotalVenta;
		this.estado = estado;
		this.fKCliente = fKCliente;
		this.fKUsuario = fKUsuario;
	}

	public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getPrecioTotalVenta() {
        return precioTotalVenta;
    }

    public void setPrecioTotalVenta(int precioTotalVenta) {
        this.precioTotalVenta = precioTotalVenta;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public Cliente getFKCliente() {
        return fKCliente;
    }

    public void setFKCliente(Cliente fKCliente) {
        this.fKCliente = fKCliente;
    }

    public Usuario getFKUsuario() {
        return fKUsuario;
    }

    public void setFKUsuario(Usuario fKUsuario) {
        this.fKUsuario = fKUsuario;
    }

    @XmlTransient
    public List<VentaActual> getVentaactualList() {
        return ventaactualList;
    }

    public void setVentaactualList(List<VentaActual> ventaactualList) {
        this.ventaactualList = ventaactualList;
    }
    
    public String getNameEstado() {
		if(estado == 1)
		{
			return "Activo";
		}
		else
		{
			return "Inactivo";
		}
	}
    
    public String getCedulaCliente()
    {
    	if(fKCliente != null)
    	{
    		return fKCliente.getCedula() + "";
    	}
    	else
    	{
    		return "Sin cliente";
    	}
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Ventas [idVenta=" + idVenta + ", fechaVenta=" + fechaVenta + ", precioTotalVenta=" + precioTotalVenta
				+ ", estado=" + estado + ", fKCliente=" + fKCliente + ", fKUsuario=" + fKUsuario + "]";
	}
    
    
    
}
