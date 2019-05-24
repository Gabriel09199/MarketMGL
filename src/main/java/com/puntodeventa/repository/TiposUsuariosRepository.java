package com.puntodeventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puntodeventa.estructure.TipoUsuario;
@Repository
public interface TiposUsuariosRepository extends JpaRepository<TipoUsuario, Short>{

}
