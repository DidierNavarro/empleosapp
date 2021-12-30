package com.didiernavarro.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.didiernavarro.empleos.model.Categoria;

//public interface CategoriasRepository extends CrudRepository<Categoria, Integer>
public interface CategoriasRepository extends JpaRepository<Categoria, Integer>
{
	
}
