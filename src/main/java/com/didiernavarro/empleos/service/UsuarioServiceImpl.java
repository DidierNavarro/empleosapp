package com.didiernavarro.empleos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.didiernavarro.empleos.model.Usuario;
import com.didiernavarro.empleos.repository.UsuariosRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService
{
	@Autowired
	private UsuariosRepository usuariosRepository;

	@Override
	public void guardar(Usuario usuario)
	{
		usuariosRepository.save(usuario);
	}

	@Override
	public void eliminar(Integer idUsuario)
	{
		usuariosRepository.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos()
	{
		return usuariosRepository.findAll();
	}

}
