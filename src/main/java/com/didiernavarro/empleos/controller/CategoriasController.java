package com.didiernavarro.empleos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.didiernavarro.empleos.model.Categoria;
import com.didiernavarro.empleos.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController
{
	@Autowired
	@Qualifier("categoriaServiceJpa")
	CategoriaService categoriaService;
	
    // @GetMapping("/index")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model)
	{
		model.addAttribute("categorias", categoriaService.buscarTodas());
		return "categorias/listCategorias";
	}
	
	@RequestMapping(value="/indexPaginate", method=RequestMethod.GET)
	public String mostrarIndexPaginado(Model model, Pageable pageable)
	{
		Page<Categoria> categorias = categoriaService.buscarTodas(pageable);
		model.addAttribute("categorias", categorias);
		return "categorias/listCategorias";
	}
	
	// @GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria)
	{
		return "categorias/formCategoria";
	}
	
	// @PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
//	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion)
	public String guardar(RedirectAttributes redirectAttributes, Categoria categoria, BindingResult result)
	{
		if( result.hasErrors() )
		{
			for (ObjectError error : result.getAllErrors())
			{
				System.out.println(error.getDefaultMessage());
			}
			return "categorias/formCategoria";
		}
		System.out.println("Categoria: " + categoria);
		categoriaService.guardar(categoria);
		redirectAttributes.addFlashAttribute("msg", "Registro Guardado");
		return "redirect:/categorias/indexPaginate";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model)
	{
		Categoria categoria = categoriaService.buscarPorId(idCategoria);
		model.addAttribute("categoria", categoria);
		return "categorias/formCategoria";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes)
	{
		System.out.println("Borrando Categoria con ID: " + idCategoria);
		categoriaService.eliminar(idCategoria);
		attributes.addFlashAttribute("msg", "La categoría fue eliminada");
		return "redirect:/categorias/index";
	}

}
