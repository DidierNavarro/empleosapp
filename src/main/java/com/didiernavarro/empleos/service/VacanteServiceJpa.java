package com.didiernavarro.empleos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.didiernavarro.empleos.model.Vacante;
import com.didiernavarro.empleos.repository.VacantesRepository;

@Service
@Primary
public class VacanteServiceJpa implements VacanteService
{
	@Autowired
	private VacantesRepository vacantesRepository;

	@Override
	public List<Vacante> buscarTodas()
	{
		return vacantesRepository.findAll();
	}

	@Override
	public Vacante buscarPorID(Integer idVacante)
	{
		Optional<Vacante> optional = vacantesRepository.findById(idVacante);
		if (optional.isPresent())
		{
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante)
	{
		vacantesRepository.save(vacante);
	}

	@Override
	public List<Vacante> buscarDetacadas()
	{
		return vacantesRepository.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void eliminar(Integer idVacante)
	{
		vacantesRepository.deleteById(idVacante);
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example)
	{
		return vacantesRepository.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page)
	{
		return vacantesRepository.findAll(page);
	}

}
