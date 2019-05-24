package com.puntodeventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puntodeventa.estructure.Cliente;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Integer>   {

}
