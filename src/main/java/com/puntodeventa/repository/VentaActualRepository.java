package com.puntodeventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puntodeventa.estructure.VentaActual;

@Repository
public interface VentaActualRepository extends JpaRepository<VentaActual, Integer>    {

}
