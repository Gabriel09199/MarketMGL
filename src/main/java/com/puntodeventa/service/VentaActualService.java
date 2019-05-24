package com.puntodeventa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puntodeventa.estructure.VentaActual;
import com.puntodeventa.estructure.Venta;
import com.puntodeventa.exception.ResourceNotFoundException;
import com.puntodeventa.repository.VentaActualRepository;

@Service
public class VentaActualService
{
	@Autowired
	private VentaActualRepository ventaActualRepository;
	
	public List<VentaActual> listarVentasActualesIndividuales()
	{
		return ventaActualRepository.findAll();
	}
	
	public List<VentaActual> listarVentasActualesPorVenta(Integer id_venta)
	{
		List<VentaActual> ventasIndividuales = listarVentasActualesIndividuales();
		List<VentaActual> ventas = new ArrayList<>();
		
		for (VentaActual ventaActual : ventasIndividuales) {
			if(ventaActual.getFKVenta().getIdVenta() == id_venta)
			{
				ventas.add(ventaActual);
			}
		}
		
		return ventas;
	}
	
	public VentaActual crearVenta(VentaActual ventaActual)
	{
		return ventaActualRepository.save(ventaActual);	
	}
	
	public VentaActual darVentaActualPorId(Integer id_venta_actual)
	{
		return ventaActualRepository.findById(id_venta_actual)
	            .orElseThrow(() -> new ResourceNotFoundException("VentaActual", "id_venta_actual", id_venta_actual));
	}
}
