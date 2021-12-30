package com.didiernavarro.empleos.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.didiernavarro.empleos.model.Categoria;

@Service
public class CategoriaServiceImpl implements CategoriaService
{
	private LinkedList<Categoria> categorias = null;
	
	public CategoriaServiceImpl()
	{
//		super();
		this.categorias = new LinkedList<>();
		
		Categoria cat1 = new Categoria();
		cat1.setId(1);
		cat1.setNombre("Contabilidad");
		
		Categoria cat2 = new Categoria();
		cat2.setId(2);
		cat2.setNombre("Ventas");
		
		Categoria cat3 = new Categoria();
		cat3.setId(3);
		cat3.setNombre("Comunicaciones");
		
		Categoria cat4 = new Categoria();
		cat4.setId(4);
		cat4.setNombre("Arquitectura");
		
		Categoria cat5 = new Categoria();
		cat5.setId(5);
		cat5.setNombre("Educación");
		
		this.categorias.add(cat1);
		this.categorias.add(cat2);
		this.categorias.add(cat3);
		this.categorias.add(cat4);
		this.categorias.add(cat5);
	}

	@Override
	public void guardar(Categoria categoria)
	{
		 this.categorias.add(categoria);
	}

	@Override
	public List<Categoria> buscarTodas()
	{
		
		return categorias;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria)
	{
		for (Categoria categoria : categorias)
		{
			if(categoria.getId() == idCategoria)
				return categoria;
			
		}
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria)
	{
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable pageable)
	{
		return null;
	}

}
