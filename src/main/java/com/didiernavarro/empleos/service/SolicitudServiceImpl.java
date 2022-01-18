package com.didiernavarro.empleos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.didiernavarro.empleos.model.Solicitud;
import com.didiernavarro.empleos.repository.SolcitudesRepository;

@Service
public class SolicitudServiceImpl implements SolicitudService
{
	
	@Autowired
	private SolcitudesRepository solicitudesRepository;

	@Override
	public void guardar(Solicitud solicitud)
	{
		solicitudesRepository.save(solicitud);
	}

	@Override
	public void eliminar(Integer idSolicitud)
	{
		solicitudesRepository.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas()
	{
		return solicitudesRepository.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud)
	{
		Optional<Solicitud> optional = solicitudesRepository.findById(idSolicitud);
		if (optional.isPresent())
		{
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page)
	{
		return solicitudesRepository.findAll(page);
	}

}
