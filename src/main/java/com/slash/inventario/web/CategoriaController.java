package com.slash.inventario.web;

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

import com.slash.inventario.data.CategoriaRepository;
import com.slash.inventario.data.spec.CategoriaEspecificacion;
import com.slash.inventario.domain.Categoria;

@SessionAttributes({"elementoBusqueda", "numeroPagina"})
@Controller
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@ModelAttribute(name="entidad")
	public String entidad() {
		return "Categoría";
	}
	
	@ModelAttribute(name="elementoBusqueda")
	public Categoria categoria() {
		return new Categoria();
	}
	
	@ModelAttribute(name="urlPaginacion")
	public String urlPaginacion() {
		return "/categoria/paginar";
	}
	
	@GetMapping
	public String home(Model modelo) {
		modelo.addAttribute("elementoBusqueda", new Categoria());
		
		return "redirect:/categoria/buscar";
	}
	
	@GetMapping("/buscar")
	public String home(Model modelo
			, @RequestParam(name="numeroPagina", required=false, defaultValue="0") Integer numeroPagina
			, @ModelAttribute("elementoBusqueda") Categoria categoria) {
		
		//Sobreescribe el tipo de dato del objeto elementoBusqueda
		modelo.addAttribute("numeroPagina", numeroPagina);
		
		return consultar(modelo, numeroPagina, categoria);
	}
	
	@GetMapping("/paginar")
	public String paginar(Model modelo
			, @RequestParam(name="numeroPagina", required=false, defaultValue="0") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Categoria categoria) {
		
		modelo.addAttribute("numeroPagina", numeroPagina);
		
		return consultar(modelo, numeroPagina, categoria);
	}
	
	private String consultar(Model modelo, Integer numeroPagina, Categoria categoria) {
		PageRequest pageRequest = PageRequest.of(numeroPagina, 5, Sort.by("id").descending());
		Page<Categoria> pagina = categoriaRepository.findAll(CategoriaEspecificacion.of(categoria), pageRequest);
		
		modelo.addAttribute("pagina", pagina);
		
		return "categoria/home";
	}
	
	@GetMapping("/registrar")
	public String registrar(Model modelo) {
		modelo.addAttribute("categoria", new Categoria());
		
		return "categoria/registrar";
	}
	
	@PostMapping("/registrar")
	public String registrar(@Valid Categoria categoria, Errors errores) {
		
		if(errores.hasErrors()) {
			return "categoria/registrar";
		}
		
		categoriaRepository.save(categoria);
		
		return "redirect:/categoria";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(Model modelo, @PathVariable("id") Short id) {
		Categoria categoria = categoriaRepository.findById(id).orElse(null);
		
		modelo.addAttribute("categoria", categoria);
		
		return "categoria/editar";
	}
	
	@PostMapping("/editar")
	public String editar(Model modelo
			, @Valid Categoria categoria
			, Errors errores
			, @SessionAttribute("numeroPagina") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Categoria categoriaSesion) {
		
		if(errores.hasErrors()) {
			return "categoria/editar";
		}
		
		categoriaRepository.saveAndFlush(categoria);
		
		return consultar(modelo, numeroPagina, categoriaSesion);
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model modelo, @PathVariable("id") Short id) {
		Categoria categoria = categoriaRepository.findById(id).orElse(null);
		
		modelo.addAttribute("categoria", categoria);
		
		return "categoria/eliminar";
	}
	
	@PostMapping("/eliminar")
	public String eliminar(Model modelo
			, Categoria categoria
			, @SessionAttribute("numeroPagina") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Categoria categoriaSesion) {
		
		categoriaRepository.deleteById(categoria.getId());
		
		return consultar(modelo, numeroPagina, categoriaSesion);
	}
}
