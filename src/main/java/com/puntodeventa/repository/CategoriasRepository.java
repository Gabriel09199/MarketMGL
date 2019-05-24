package com.puntodeventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puntodeventa.estructure.Categoria;

@Repository
public interface CategoriasRepository extends JpaRepository<Categoria, Short>  {

}
