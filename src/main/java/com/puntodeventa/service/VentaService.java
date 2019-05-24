package com.puntodeventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puntodeventa.estructure.Venta;
import com.puntodeventa.exception.ResourceNotFoundException;
import com.puntodeventa.repository.VentaRepository;

@Service
public class VentaService
{
	@Autowired
	private VentaRepository ventasRepository;
	
	public List<Venta> listarVentas()
	{
		return ventasRepository.findAll();
	}
	
	public Venta darVentaPorId(Integer id_venta)
	{
		return ventasRepository.findById(id_venta)
	            .orElseThrow(() -> new ResourceNotFoundException("Ventas", "id_venta", id_venta));
	}
	
	public Venta crearVenta(Venta venta)
	{
		return ventasRepository.save(venta);	
	}
	
	public Venta modificarVenta(Integer id_venta, Venta ventaDetalles)
	{
		Venta venta = ventasRepository.findById(id_venta)
	            .orElseThrow(() -> new ResourceNotFoundException("Ventas", "id_venta", id_venta));
		
		venta.setEstado(ventaDetalles.getEstado());
		
		Venta actualizado = ventasRepository.save(venta);
		
		return actualizado;
	}
}
