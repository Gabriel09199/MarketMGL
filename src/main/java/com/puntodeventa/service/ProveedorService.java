package com.puntodeventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puntodeventa.estructure.Proveedor;
import com.puntodeventa.exception.ResourceNotFoundException;
import com.puntodeventa.repository.ProveedoresRepository;

@Service
public class ProveedorService
{
	@Autowired
	private ProveedoresRepository proveedoresRepository;
	
	public List<Proveedor> listarProveedores()
	{
		return proveedoresRepository.findAll();
	}
	
	public Proveedor darProveedorPorId(Integer id_proveedor)
	{
		return proveedoresRepository.findById(id_proveedor)
	            .orElseThrow(() -> new ResourceNotFoundException("Proveedores", "id_proveedor", id_proveedor));
	}
}
