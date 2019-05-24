package com.puntodeventa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puntodeventa.estructure.TipoUsuario;
import com.puntodeventa.estructure.Usuario;
import com.puntodeventa.exception.ResourceNotFoundException;
import com.puntodeventa.repository.TiposUsuariosRepository;

@Service
public class TipoUsuarioService 
{
	@Autowired
	private TiposUsuariosRepository tiposRepository;

	public List<TipoUsuario> listarUsuarios()
	{
		return tiposRepository.findAll();
	}
	
	public List<TipoUsuario> listarUsuariosAdministrador()
	{
		List<TipoUsuario> tiposUsuario = tiposRepository.findAll();
		List<TipoUsuario> tiposUsuarioAdmin = new ArrayList<TipoUsuario>();
		
		for(TipoUsuario tipoUsuarioActual: tiposUsuario)
		{
			if(!tipoUsuarioActual.getNombreEstado().equals(TipoUsuario.SUPER_ADMINISTRADOR))
			{
				tiposUsuarioAdmin.add(tipoUsuarioActual);
			}
		}
		
		return tiposUsuarioAdmin;
	}

	public TipoUsuario buscarTipoUsuarioPorId(Short id_tipo_usuario) {
		return tiposRepository.findById(id_tipo_usuario)
	            .orElseThrow(() -> new ResourceNotFoundException("TiposUsuario", "id_tipo_usuario", id_tipo_usuario));
	}

}
