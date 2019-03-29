package com.slash.inventario.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.slash.inventario.data.PerfilRepository;
import com.slash.inventario.data.UsuarioRepository;
import com.slash.inventario.data.spec.UsuarioEspecificacion;
import com.slash.inventario.domain.Habilitado;
import com.slash.inventario.domain.Perfil;
import com.slash.inventario.domain.Usuario;

@SessionAttributes({"elementoBusqueda", "numeroPagina"})
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@ModelAttribute(name="entidad")
	public String entidad() {
		return "Usuario";
	}
	
	@ModelAttribute(name="elementoBusqueda")
	public Usuario usuarioBusqueda() {
		return new Usuario();
	}
	
	@ModelAttribute(name="urlPaginacion")
	public String urlPaginacion() {
		return "/usuario/paginar";
	}
	
	@ModelAttribute(name="opcionesHabilitado")
	public List<Habilitado> opcionesHabilitado() {
		return Arrays.asList(
				new Habilitado(null, "-- Seleccione --"),
				new Habilitado(false, "No"),
				new Habilitado(true, "Si")
		);
	}
	
	@ModelAttribute("perfiles")
	public List<Perfil> perfiles() {
		Perfil perfil = new Perfil();
		perfil.setNombre("-- Seleccione --");
		
		List<Perfil> perfiles = new ArrayList<>();
		perfiles.add(perfil);
		perfiles.addAll(perfilRepository.findAll());
		
		return perfiles;
	}
	
	@GetMapping
	public String home(Model modelo) {
		//Sobreescribe el tipo de dato del objeto elementoBusqueda
		modelo.addAttribute("elementoBusqueda", new Usuario());
		
		return "redirect:/usuario/buscar";
	}
	
	@GetMapping("/buscar")
	public String home(Model modelo
			, @RequestParam(name="numeroPagina", required=false, defaultValue="0") Integer numeroPagina
			, @ModelAttribute("elementoBusqueda") Usuario usuario) {
		
		modelo.addAttribute("numeroPagina", numeroPagina);
		
		return consultar(modelo, numeroPagina, usuario);
	}
	
	@GetMapping("/paginar")
	public String paginar(Model modelo
			, @RequestParam(name="numeroPagina", required=false, defaultValue="0") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Usuario usuario) {
		
		modelo.addAttribute("numeroPagina", numeroPagina);
		
		return consultar(modelo, numeroPagina, usuario);
	}
	
	private String consultar(Model modelo, Integer numeroPagina, Usuario usuario) {
		PageRequest pageRequest = PageRequest.of(numeroPagina, 5, Sort.by("id").descending());
		Page<Usuario> pagina = usuarioRepository.findAll(UsuarioEspecificacion.of(usuario), pageRequest);
		
		modelo.addAttribute("pagina", pagina);
		
		return "usuario/home";
	}
	
	@GetMapping("/registrar")
	public String registrar(Model modelo) {
		Usuario usuario = new Usuario();
		usuario.setHabilitado(true);
		
		modelo.addAttribute("usuario", usuario);
		
		return "usuario/registrar";
	}
	
	@PostMapping("/registrar")
	public String registrar(@Valid Usuario usuario, Errors errores) {
		if(errores.hasErrors()) {
			return "usuario/registrar";
		}
		
		usuarioRepository.save(usuario);
		
		return "redirect:/usuario";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(Model modelo, @PathVariable("id") Integer id) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		
		modelo.addAttribute("usuario", usuario);
		
		return "usuario/editar";
	}
	
	@PostMapping("/editar")
	public String editar(Model modelo
			, @Valid Usuario usuario
			, Errors errores
			, @SessionAttribute("numeroPagina") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Usuario usuarioSesion) {
		
		if(errores.hasErrors()) {
			return "usuario/editar";
		}
		
		usuarioRepository.saveAndFlush(usuario);
		
		return consultar(modelo, numeroPagina, usuarioSesion);
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model modelo, @PathVariable("id") Integer id) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		
		modelo.addAttribute("usuario", usuario);
		
		return "usuario/eliminar";
	}
	
	@PostMapping("/eliminar")
	public String eliminar(Model modelo
			, Usuario usuario
			, @SessionAttribute("numeroPagina") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Usuario usuarioSesion) {
		
		usuarioRepository.deleteById(usuario.getId());
		
		return consultar(modelo, numeroPagina, usuarioSesion);
	}
}
