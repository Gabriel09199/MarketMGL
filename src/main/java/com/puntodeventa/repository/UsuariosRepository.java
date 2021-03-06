package com.puntodeventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puntodeventa.estructure.Usuario;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Short> {

}
