package com.puntodeventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puntodeventa.estructure.Producto;
import com.puntodeventa.exception.ResourceNotFoundException;
import com.puntodeventa.repository.ProductosRepository;

@Service
public class ProductoService
{
	@Autowired
	private ProductosRepository productosRepository;
	
	public List<Producto> listarProductos()
	{
		return productosRepository.findAll();
	}
	
	public Producto darProductoPorId(Integer id_producto)
	{
		return productosRepository.findById(id_producto)
	            .orElseThrow(() -> new ResourceNotFoundException("Productos", "id_producto", id_producto));
	}
}
