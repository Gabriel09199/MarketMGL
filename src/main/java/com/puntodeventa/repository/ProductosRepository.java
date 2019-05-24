package com.puntodeventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puntodeventa.estructure.Producto;

@Repository
public interface ProductosRepository extends JpaRepository<Producto, Integer>   {

}
