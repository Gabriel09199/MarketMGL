package com.puntodeventa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puntodeventa.estructure.Cliente;
import com.puntodeventa.exception.ResourceNotFoundException;
import com.puntodeventa.repository.ClientesRepository;

@Service
public class ClienteService
{
	@Autowired
	private ClientesRepository clientesRepository;
	
	public List<Cliente> listarClientes()
	{
		return clientesRepository.findAll();
	}
	
	public Cliente crearClientes(Cliente cliente) throws Exception
	{
		if(existeClientePorCedula(cliente.getCedula()) != null)
		{
			throw new Exception("La cédula de este cliente ya existe");
		}
		else
		{
			return clientesRepository.save(cliente);
		}
	}
	
	public Cliente existeClientePorCedula(int cedula)
	{
		List<Cliente> clientes = listarClientes();
		for (Cliente clienteActual : clientes)
		{
			if(cedula == clienteActual.getCedula())
			{
				return clienteActual;
			}
		}
		
		return null;
	}
	
	public Cliente darClientePorId(Integer id_cliente)
	{
		return clientesRepository.findById(id_cliente)
	            .orElseThrow(() -> new ResourceNotFoundException("Clientes", "id_cliente", id_cliente));
	}
	
	public Cliente modificarCliente(Integer id_cliente, Cliente clienteDetalles)
	{
		Cliente cliente = clientesRepository.findById(id_cliente)
	            .orElseThrow(() -> new ResourceNotFoundException("Clientes", "id_cliente", id_cliente));
		
		cliente.setPrimerNombre(clienteDetalles.getPrimerNombre());
		cliente.setSegundoNombre(clienteDetalles.getSegundoNombre());
		cliente.setPrimerApellido(clienteDetalles.getPrimerApellido());
		cliente.setSegundoApellido(clienteDetalles.getSegundoApellido());
		cliente.setTelefono(clienteDetalles.getTelefono());
		cliente.setGenero(clienteDetalles.getGenero());

		Cliente actualizado = clientesRepository.save(cliente);
		
		return actualizado;
	}
}
