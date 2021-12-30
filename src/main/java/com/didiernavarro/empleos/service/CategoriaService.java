package com.didiernavarro.empleos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.didiernavarro.empleos.model.Categoria;

public interface CategoriaService
{
	void guardar(Categoria categoria);
	
	List<Categoria> buscarTodas();
	
	Categoria buscarPorId(Integer idCategoria);
	
	void eliminar(Integer idCategoria);
	
	Page<Categoria> buscarTodas(Pageable pageable);
}
