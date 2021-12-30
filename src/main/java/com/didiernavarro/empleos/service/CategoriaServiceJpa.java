package com.didiernavarro.empleos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.didiernavarro.empleos.model.Categoria;
import com.didiernavarro.empleos.repository.CategoriasRepository;

@Service
//@Primary
public class CategoriaServiceJpa implements CategoriaService
{
	@Autowired
	private CategoriasRepository categoriasRepository;

	@Override
	public void guardar(Categoria categoria)
	{
		categoriasRepository.save(categoria);
	}

	@Override
	public List<Categoria> buscarTodas()
	{
		return categoriasRepository.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria)
	{
		Optional<Categoria> optional = categoriasRepository.findById(idCategoria);
		if (optional.isPresent())
		{
			return optional.get();
		}
		return null; 
	}

	@Override
	public void eliminar(Integer idCategoria)
	{
		categoriasRepository.deleteById(idCategoria);
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable pageable)
	{
		return categoriasRepository.findAll(pageable);
	}

}
