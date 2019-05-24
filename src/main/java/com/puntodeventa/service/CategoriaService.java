package com.puntodeventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puntodeventa.estructure.Categoria;
import com.puntodeventa.exception.ResourceNotFoundException;
import com.puntodeventa.repository.CategoriasRepository;

@Service
public class CategoriaService
{
	@Autowired
	private CategoriasRepository categoriasRepository;
	
	public List<Categoria> listarCategorias()
	{
		return categoriasRepository.findAll();
	}
	
	public Categoria darCategoriaPorId(Short id_categoria)
	{
		return categoriasRepository.findById(id_categoria)
	            .orElseThrow(() -> new ResourceNotFoundException("Categorias", "id_categoria", id_categoria));
	}
}
