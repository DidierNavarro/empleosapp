package com.didiernavarro.empleos.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.didiernavarro.empleos.model.Perfil;
import com.didiernavarro.empleos.model.Usuario;
import com.didiernavarro.empleos.model.Vacante;
import com.didiernavarro.empleos.service.CategoriaService;
import com.didiernavarro.empleos.service.UsuarioService;
import com.didiernavarro.empleos.service.VacanteService;

@Controller
public class HomeController
{
	@Autowired
	private VacanteService vacanteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	@Qualifier("categoriaServiceJpa")
	private CategoriaService categoriaService;
	
	/**
	 * InitBinder para Strings que si los detecta vacíos en el Data Binding, los setea a NULL
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@GetMapping("/")
	public String mostrarHome(Model model)
	{
		/*model.addAttribute("mensaje", "Bienvenidos a Empleos App");
		model.addAttribute("fecha", new Date());*/
		/*String nombre = "Auxiliar de Contabilidad";
		Date fechaPub = new Date();
		Double salario = 9000.0;
		Boolean vigente = true;
		
		model.addAttribute("nombre", nombre);
		model.addAttribute("fecha", fechaPub);
		model.addAttribute("salario", salario);
		model.addAttribute("vigente", vigente);*/
//		List<Vacante> lista = vacanteService.buscarTodas();
//		model.addAttribute("vacantes", lista);
		
		return "home";
	}
	
	// @ModelAttribute Sirve para agregar al modelo atributos disponibles para todos lo métodos en todo el controlador
	@ModelAttribute
	public void setGenericos(Model model)
	{
		model.addAttribute("vacantes", vacanteService.buscarDetacadas());
		model.addAttribute("categorias", categoriaService.buscarTodas());
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.reset();
		model.addAttribute("search", vacanteSearch);
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model)
	{
		List<String> lista = new LinkedList<>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		model.addAttribute("empleos", lista);
		
		return "listado";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario)
	{
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes)
	{
		usuario.setFechaRegistro(new Date());
		usuario.setEstatus(1);
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregarPerfil(perfil);
		
		usuarioService.guardar(usuario);
		return "redirect:/usuarios/index";
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model)
	{
		System.out.println("Buscando por: " + vacante);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> vacantes = vacanteService.buscarByExample(example);
		model.addAttribute("vacantes", vacantes);
		return "home";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model)
	{
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero de Comunicaciones");
		vacante.setDescripcion("Se solicita Ingeniero para dar soporte a Intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model)
	{
		List<Vacante> lista = vacanteService.buscarTodas();
		model.addAttribute("vacantes", lista);
		return "tabla";
	}
	
	/**
	 * Método que regresa una lista de objetos de tipo Vacante
	 * @return
	 */
//	private List<Vacante> getVacantes()
//	{
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//		List<Vacante> lista = new LinkedList<Vacante>();
//		try
//		{
//			// Creamos la oferta de Trabajo 1.
//			Vacante vacante1 = new Vacante();
//			vacante1.setId(1);
//			vacante1.setNombre("Ingeniero Civil"); // Titulo de la vacante
//			vacante1.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal.");
//			vacante1.setFecha(sdf.parse("08-02-2019"));
//			vacante1.setSalario(8500.0);
//			vacante1.setDestacado(1);
//			vacante1.setImagen("empresa1.png");
//
//			// Creamos la oferta de Trabajo 2.
//			Vacante vacante2 = new Vacante();
//			vacante2.setId(2);
//			vacante2.setNombre("Contador Publico");
//			vacante2.setDescripcion("Empresa importante solicita contador con 5 años de experiencia titulado.");
//			vacante2.setFecha(sdf.parse("09-02-2019"));
//			vacante2.setSalario(12000.0);
//			vacante2.setDestacado(0);
//			vacante2.setImagen("empresa2.png");
//
//			// Creamos la oferta de Trabajo 3.
//			Vacante vacante3 = new Vacante();
//			vacante3.setId(3);
//			vacante3.setNombre("Ingeniero Eléctrico");
//			vacante3.setDescripcion(
//					"Empresa internacional solicita Ingeniero mecánico para mantenimiento de la instalación eléctrica.");
//			vacante3.setFecha(sdf.parse("10-02-2019"));
//			vacante3.setSalario(10500.0);
//			vacante3.setDestacado(0);
//
//			// Creamos la oferta de Trabajo 4.
//			Vacante vacante4 = new Vacante();
//			vacante4.setId(4);
//			vacante4.setNombre("Diseñador Gráfico");
//			vacante4.setDescripcion("Solicitamos Diseñador Gráfico titulado para diseñar publicidad de la empresa.");
//			vacante4.setFecha(sdf.parse("11-02-2019"));
//			vacante4.setSalario(7500.0);
//			vacante4.setDestacado(1);
//			vacante4.setImagen("empresa3.png");
//
//			/**
//			 * Agregamos los 4 objetos de tipo Vacante a la lista ...
//			 */
//			lista.add(vacante1);
//			lista.add(vacante2);
//			lista.add(vacante3);
//			lista.add(vacante4);
//
//		}
//		catch (ParseException e)
//		{
//			System.out.println("Error: " + e.getMessage());
//		}
//		return lista;
//	}

}
