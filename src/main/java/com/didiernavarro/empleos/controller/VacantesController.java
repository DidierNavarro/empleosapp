package com.didiernavarro.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.didiernavarro.empleos.model.Vacante;
import com.didiernavarro.empleos.service.CategoriaService;
import com.didiernavarro.empleos.service.VacanteService;
import com.didiernavarro.empleos.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController
{
	@Autowired
	private VacanteService vacanteService;
	
	@Autowired
	@Qualifier("categoriaServiceJpa")
	private CategoriaService categoriaService;
	
	@Value("${empleos.ruta.imagenes}")
	private String rutaImagenes;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@ModelAttribute
	public void setGenericos(Model model)
	{
		model.addAttribute("categorias", categoriaService.buscarTodas());
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Model model)
	{
		List<Vacante> vacantes = vacanteService.buscarTodas();
		model.addAttribute("vacantes", vacantes);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page)
	{
		Page<Vacante> vacantes = vacanteService.buscarTodas(page);
		model.addAttribute("vacantes", vacantes);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model)
	{
		
		model.addAttribute("idVacante", idVacante);
		Vacante vacante = vacanteService.buscarPorID(idVacante);
		System.out.println("Vacante: " + vacante);
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model)
	{
//		model.addAttribute("categorias", categoriaService.buscarTodas());
		return "vacantes/formVacante";
	}

	/*@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("categoria") String categoria,
			@RequestParam("estatus") String estatus,
			@RequestParam("fecha") String fecha,
			@RequestParam("destacado") int destacado,
			@RequestParam("salario") double salario,
			@RequestParam("detalles") String detalles)
	{
		System.out.println(nombre);
		System.out.println(descripcion);
		System.out.println(categoria);
		System.out.println(estatus);
		System.out.println(fecha);
		System.out.println(destacado);
		System.out.println(salario);
		System.out.println(detalles);
		return "vacantes/listVacantes";
	}*/
	
	@PostMapping("/save")
	public String guardar( Vacante vacante, BindingResult result, /*Model model,*/ RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multiPartFile )
	{
		if( result.hasErrors() )
		{
			for (ObjectError error : result.getAllErrors())
			{
				System.out.println(error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}
		
		if (!multiPartFile.isEmpty())
		{
//			String ruta = "/Users/didiernavarro/Documents/workspace-spring-tool-suite-4-4.9.0.RELEASE/imagenes-empleos/"; // Linux/MAC
//			String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPartFile, rutaImagenes);
			if (nombreImagen != null)
			{ // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}
		
		System.out.println(vacante);
		vacanteService.guardar(vacante);
//		model.addAttribute("msg", "Registro Guardado");
		attributes.addFlashAttribute("msg", "Registro Guardado");
//		return "vacantes/listVacantes";
		return "redirect:/vacantes/indexPaginate";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model)
	{
		Vacante vacante = vacanteService.buscarPorID(idVacante);
		model.addAttribute("vacante", vacante);
		return "vacantes/formVacante";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes)
	{
		System.out.println("Borrando Vacante con ID: " + idVacante);
		vacanteService.eliminar(idVacante);
		attributes.addFlashAttribute("msg", "La vacante fue eliminada");
		return "redirect:/vacantes/index";
	}
}
