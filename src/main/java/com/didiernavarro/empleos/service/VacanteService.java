package com.didiernavarro.empleos.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.didiernavarro.empleos.model.Vacante;

public interface VacanteService
{
	List<Vacante> buscarTodas();
	
	Vacante buscarPorID(Integer idVacante);
	
	void guardar(Vacante vacante);
	
	List<Vacante> buscarDetacadas();
	
	void eliminar(Integer idVacante);
	
	List<Vacante> buscarByExample(Example<Vacante> example);
	
	Page<Vacante> buscarTodas(Pageable page);
}
