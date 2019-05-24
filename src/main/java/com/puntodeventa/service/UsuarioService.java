package com.puntodeventa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puntodeventa.estructure.TipoUsuario;
import com.puntodeventa.estructure.Usuario;
import com.puntodeventa.exception.ResourceNotFoundException;
import com.puntodeventa.repository.UsuariosRepository;

@Service
public class UsuarioService
{
	private Usuario usuario;
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	public List<Usuario> listarUsuarios()
	{
		return usuariosRepository.findAll();
	}
	
	public List<Usuario> listarUsuariosAdministrador()
	{
		List<Usuario> usuarios = usuariosRepository.findAll();
		List<Usuario> usuariosAdmin = new ArrayList<Usuario>();
		
		for(Usuario usuarioActual: usuarios)
		{
			if(!usuarioActual.getFKTipoUsuario().getNombreEstado().equals(TipoUsuario.SUPER_ADMINISTRADOR))
			{
				usuariosAdmin.add(usuarioActual);
			}
		}
		
		return usuariosAdmin;
	}
	
	public Usuario crearUsuarios(Usuario usuario) throws Exception
	{
		if(existeUsuarioPorCedula(usuario.getCedula()) != null)
		{
			throw new Exception("La cédula de este usuario ya existe");
		}
		else if(existeUsuarioPorCorreo(usuario.getCorreoElectronico()) != null)
		{
			throw new Exception("El correo electrónico de este usuario ya existe");
		}
		else
		{
			return usuariosRepository.save(usuario);
		}
	}
	
	private Usuario existeUsuarioPorCorreo(String correoElectronico)
	{
		List<Usuario> usuarios = listarUsuarios();
		for (Usuario usuarioActual : usuarios)
		{
			if(correoElectronico.equals(usuarioActual.getCorreoElectronico()))
			{
				return usuarioActual;
			}
		}
		
		return null;
	}
	
	public Usuario existeUsuarioPorCedula(int cedula)
	{
		List<Usuario> usuarios = listarUsuarios();
		for (Usuario usuarioActual : usuarios)
		{
			if(cedula == usuarioActual.getCedula())
			{
				return usuarioActual;
			}
		}
		
		return null;
	}
	
	public Usuario darUsuarioPorId(Short id_usuario)
	{
		return usuariosRepository.findById(id_usuario)
	            .orElseThrow(() -> new ResourceNotFoundException("Usuarios", "id_usuarios", id_usuario));
	}
	
	public Usuario modificarUsuario(Short id_usuario, Usuario usuarioDetalles)
	{
		Usuario user = usuariosRepository.findById(id_usuario)
	            .orElseThrow(() -> new ResourceNotFoundException("Usuarios", "id_usuarios", id_usuario));
		
		user.setPrimerNombre(usuarioDetalles.getPrimerNombre());
		user.setSegundoNombre(usuarioDetalles.getSegundoNombre());
		user.setPrimerApellido(usuarioDetalles.getPrimerApellido());
		user.setSegundoApellido(usuarioDetalles.getSegundoApellido());
		user.setContrasenia(usuarioDetalles.getContrasenia());
		user.setTelefono(usuarioDetalles.getTelefono());
		user.setCorreoElectronico(usuarioDetalles.getCorreoElectronico());
		user.setEstado(usuarioDetalles.getEstado());
		user.setGenero(usuarioDetalles.getGenero());
		user.setFechaNacimiento(usuarioDetalles.getFechaNacimiento());
		user.setFKTipoUsuario(usuarioDetalles.getFKTipoUsuario());

		Usuario actualizado = usuariosRepository.save(user);
		
		return actualizado;
	}

	public Usuario autenticar(String cedula, String contrasenia, String cargo) throws Exception
	{
		// TODO Auto-generated method stub
		Usuario usuario = existeUsuarioPorCedula(Integer.parseInt(cedula));
		if(usuario == null)
		{
			throw new Exception("El usuario con esa cédula no existe");
		}
		else if(!usuario.getFKTipoUsuario().getNombreEstado().equals(cargo)) 
		{
			throw new Exception("El usuario no tiene permisos para autenticarse como " + cargo);
		}
		else if(usuario.getEstado() == '2')
		{
			throw new Exception("El usuario se encuentra inhabilitado");
		}
		else if(usuario.getContrasenia().equals(contrasenia) && usuario.getEstado() == '1')
		{
			return usuario;
		}
		
		return null;
	}
	
	public void setUsuario(Usuario currentUsuario)
	{
		usuario = currentUsuario;
	}
	
	public Usuario getUsuario()
	{
		return usuario;
	}
}
