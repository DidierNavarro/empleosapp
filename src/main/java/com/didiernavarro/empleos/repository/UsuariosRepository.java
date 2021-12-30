package com.didiernavarro.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.didiernavarro.empleos.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer>
{

}
