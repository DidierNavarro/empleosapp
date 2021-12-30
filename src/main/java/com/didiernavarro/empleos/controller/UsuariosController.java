package com.didiernavarro.empleos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.didiernavarro.empleos.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController
{
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model)
	{
		model.addAttribute("usuarios", usuarioService.buscarTodos());
		return "usuarios/listUsuarios";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Integer idUsuario, RedirectAttributes attributes)
	{
		usuarioService.eliminar(idUsuario);
		return "redirect:/usuarios/index";
	}
}
