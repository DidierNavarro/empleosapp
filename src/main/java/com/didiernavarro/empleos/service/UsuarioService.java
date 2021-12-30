package com.didiernavarro.empleos.service;

import java.util.List;

import com.didiernavarro.empleos.model.Usuario;

public interface UsuarioService
{
	void guardar(Usuario usuario);
	
	void eliminar(Integer idUsuario);
	
	List<Usuario> buscarTodos();
}
