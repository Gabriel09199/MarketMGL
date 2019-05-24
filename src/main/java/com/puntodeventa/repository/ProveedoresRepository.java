package com.puntodeventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puntodeventa.estructure.Proveedor;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedor, Integer> {

}
