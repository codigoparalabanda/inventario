package com.slash.inventario.web;

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

import com.slash.inventario.data.ProveedorRepository;
import com.slash.inventario.data.spec.ProveedorEspecificacion;
import com.slash.inventario.domain.Habilitado;
import com.slash.inventario.domain.Proveedor;

@SessionAttributes({"elementoBusqueda", "numeroPagina"})
@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

	@Autowired
	private ProveedorRepository proveedorRepository;
	
	@ModelAttribute(name="entidad")
	public String entidad() {
		return "Proveedor";
	}
	
	@ModelAttribute(name="elementoBusqueda")
	public Proveedor proveedor() {
		return new Proveedor();
	}
	
	@ModelAttribute(name="urlPaginacion")
	public String urlPaginacion() {
		return "/proveedor/paginar";
	}
	
	@ModelAttribute(name="opcionesHabilitado")
	public List<Habilitado> opcionesHabilitado() {
		return Arrays.asList(
				new Habilitado(null, "-- Seleccione --"),
				new Habilitado(false, "No"),
				new Habilitado(true, "Si")
		);
	}
	
	@GetMapping
	public String home(Model modelo) {
		//Sobreescribe el tipo de dato del objeto elementoBusqueda
		modelo.addAttribute("elementoBusqueda", new Proveedor());
		
		return "redirect:/proveedor/buscar";
	}
	
	@GetMapping("/buscar")
	public String home(Model modelo
			, @RequestParam(name="numeroPagina", required=false, defaultValue="0") Integer numeroPagina
			, @ModelAttribute("elementoBusqueda") Proveedor proveedor) {
		
		modelo.addAttribute("numeroPagina", numeroPagina);
		
		return consultar(modelo, numeroPagina, proveedor);
	}
	
	@GetMapping("/paginar")
	public String paginar(Model modelo
			, @RequestParam(name="numeroPagina", required=false, defaultValue="0") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Proveedor proveedor) {
		
		modelo.addAttribute("numeroPagina", numeroPagina);
		
		return consultar(modelo, numeroPagina, proveedor);
	}
	
	private String consultar(Model modelo, Integer numeroPagina, Proveedor proveedor) {
		PageRequest pageRequest = PageRequest.of(numeroPagina, 5, Sort.by("id").descending());
		Page<Proveedor> pagina = proveedorRepository.findAll(ProveedorEspecificacion.of(proveedor), pageRequest);
		
		modelo.addAttribute("pagina", pagina);
		
		return "proveedor/home";
	}
	
	@GetMapping("/registrar")
	public String registrar(Model modelo) {
		Proveedor proveedor = new Proveedor();
		proveedor.setHabilitado(true);
		
		modelo.addAttribute("proveedor", proveedor);
		
		return "proveedor/registrar";
	}
	
	@PostMapping("/registrar")
	public String registrar(@Valid Proveedor proveedor, Errors errores) {
		
		if(errores.hasErrors()) {
			return "proveedor/registrar";
		}
		
		proveedorRepository.save(proveedor);
		
		return "redirect:/proveedor";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(Model modelo, @PathVariable("id") Short id) {
		Proveedor proveedor = proveedorRepository.findById(id).orElse(null);
		
		modelo.addAttribute("proveedor", proveedor);
		
		return "proveedor/editar";
	}
	
	@PostMapping("/editar")
	public String editar(Model modelo
			, @Valid Proveedor proveedor
			, Errors errores
			, @SessionAttribute("numeroPagina") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Proveedor proveedorSesion) {
		
		if(errores.hasErrors()) {
			return "proveedor/editar";
		}
		
		proveedorRepository.saveAndFlush(proveedor);
		
		return consultar(modelo, numeroPagina, proveedorSesion);
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model modelo, @PathVariable("id") Short id) {
		Proveedor proveedor = proveedorRepository.findById(id).orElse(null);
		
		modelo.addAttribute("proveedor", proveedor);
		
		return "proveedor/eliminar";
	}
	
	@PostMapping("/eliminar")
	public String eliminar(Model modelo
			, Proveedor proveedor
			, @SessionAttribute("numeroPagina") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Proveedor proveedorSesion) {
		
		proveedorRepository.deleteById(proveedor.getId());
		
		return consultar(modelo, numeroPagina, proveedorSesion);
	}
}
