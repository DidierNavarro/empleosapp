package com.didiernavarro.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.didiernavarro.empleos.model.Solicitud;
import com.didiernavarro.empleos.model.Usuario;
import com.didiernavarro.empleos.model.Vacante;
import com.didiernavarro.empleos.service.SolicitudService;
import com.didiernavarro.empleos.service.UsuarioService;
import com.didiernavarro.empleos.service.VacanteService;
import com.didiernavarro.empleos.util.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController
{
	@Autowired
	private VacanteService vacanteService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * EJERCICIO: Declarar esta propiedad en el archivo application.properties. El
	 * valor sera el directorio en donde se guardarán los archivos de los
	 * Curriculums Vitaes de los usuarios.
	 */
	@Value("${empleos.ruta.cv}")
	private String ruta;

	/**
	 * Metodo que muestra la lista de solicitudes sin paginacion Seguridad: Solo
	 * disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * 
	 * @return
	 */
	@GetMapping("/index")
	public String mostrarIndex(Model model)
	{
		List<Solicitud> solicitudes = solicitudService.buscarTodas();
    	model.addAttribute("solicitudes", solicitudes);
		return "solicitudes/listSolicitudes";

	}

	/**
	 * Metodo que muestra la lista de solicitudes con paginacion
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page)
	{
		Page<Solicitud> lista = solicitudService.buscarTodas(page);
		model.addAttribute("solicitudes", lista);
		return "solicitudes/listSolicitudes";
	}

	/**
	 * Método para renderizar el formulario para aplicar para una Vacante Seguridad:
	 * Solo disponible para un usuario con perfil USUARIO.
	 * 
	 * @return
	 */
	@GetMapping("/create/{idVacante}")
	public String crear(@PathVariable("idVacante") Integer idVacante, Model model)
	{
		Vacante vacante = vacanteService.buscarPorID(idVacante);
		model.addAttribute("vacante", vacante);
		model.addAttribute("solicitud", new Solicitud());
		return "solicitudes/formSolicitud";
	}

	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * @param solicitud
	 * @param result
	 * @param model
	 * @param session
	 * @param multiPart
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, Model model, HttpSession session,
			@RequestParam("archivoCV") MultipartFile multiPart, RedirectAttributes attributes, Authentication authentication)
	{

		// Recuperamos el username que inicio sesión
		String username = authentication.getName();

		if (result.hasErrors())
		{

			System.out.println("Existieron errores");
			return "solicitudes/formSolicitud";
		}

		if (!multiPart.isEmpty())
		{
			// String ruta = "/empleos/files-cv/"; // Linux/MAC
			// String ruta = "c:/empleos/files-cv/"; // Windows
			String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreArchivo != null)
			{ // El archivo (CV) si se subio
				solicitud.setArchivo(nombreArchivo); // Asignamos el nombre de la imagen
			}
		}

		// Buscamos el objeto Usuario en BD
		Usuario usuario = usuarioService.buscarPorUsername(username);

		solicitud.setUsuario(usuario); // Referenciamos la solicitud con el usuario
		solicitud.setFecha(new Date());
		// Guadamos el objeto solicitud en la bd
		solicitudService.guardar(solicitud);
		attributes.addFlashAttribute("msg", "Gracias por enviar tu CV!");

		// return "redirect:/solicitudes/index";
		return "redirect:/";
	}

	/**
	 * Método para eliminar una solicitud
	 * @param idSolicitud
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attributes)
	{
		// Eliminamos la solicitud.
		solicitudService.eliminar(idSolicitud);

		attributes.addFlashAttribute("msg", "La solicitud fue eliminada!.");
		return "redirect:/solicitudes/indexPaginate";
	}

	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * 
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
