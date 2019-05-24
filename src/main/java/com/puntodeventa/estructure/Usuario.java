package com.puntodeventa.estructure;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author GabrielPC
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Short idUsuario;
    
    @Basic(optional = false)
    @Column(name = "cedula")
    private int cedula;
    
    @Basic(optional = false)
    @Column(name = "contrasenia")
    private String contrasenia;
    
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
    
    @Column(name = "telefono")
    private int telefono;
    
    @Basic(optional = false)
    @Column(name = "genero")
    private short genero;
    
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "correo_electronico")
    private String correoElectronico;
    
    @Basic(optional = false)
    @Column(name = "estado")
    private char estado;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fKUsuario")
    @JsonIgnore
    private List<Venta> ventasList;
  
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fktipo_usuario", referencedColumnName = "id_tipo_usuario")
    private TipoUsuario fKTipoUsuario;

    public Usuario()
    {
    	
    }
    
    public Usuario(int cedula, String contrasenia, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, int telefono, short genero, LocalDate fechaNacimiento, String correoElectronico,
			char estado, TipoUsuario fKTipoUsuario) {
		super();
		this.cedula = cedula;
		this.contrasenia = contrasenia;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.telefono = telefono;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.correoElectronico = correoElectronico;
		this.estado = estado;
		this.fKTipoUsuario = fKTipoUsuario;
	}

	public Short getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Short idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public short getGenero() {
        return genero;
    }

    public void setGenero(short genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Venta> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<Venta> ventasList) {
        this.ventasList = ventasList;
    }

    public TipoUsuario getFKTipoUsuario() {
        return fKTipoUsuario;
    }

    public void setFKTipoUsuario(TipoUsuario fKTipoUsuario) {
        this.fKTipoUsuario = fKTipoUsuario;
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
    
    public String getNameEstado() {
		if(estado == '1')
		{
			return "Activo";
		}
		else
		{
			return "Inactivo";
		}
	}
	
	public String getNombresApellidos()
	{
		if(segundoNombre == null || segundoNombre.equals(""))
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
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Usuarios [idUsuario=" + idUsuario + ", cedula=" + cedula + ", contrasenia=" + contrasenia
				+ ", primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre + ", primerApellido="
				+ primerApellido + ", segundoApellido=" + segundoApellido + ", telefono=" + telefono + ", genero="
				+ genero + ", fechaNacimiento=" + fechaNacimiento + ", correoElectronico=" + correoElectronico
				+ ", estado=" + estado + ", fKTipoUsuario=" + fKTipoUsuario + "]";
	}

	
}
