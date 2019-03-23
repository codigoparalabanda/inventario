package com.slash.inventario.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.slash.inventario.data.CategoriaRepository;
import com.slash.inventario.data.EstatusProductoRepository;
import com.slash.inventario.data.ProductoRepository;
import com.slash.inventario.data.ProveedorRepository;
import com.slash.inventario.data.UsuarioRepository;
import com.slash.inventario.data.spec.ProductoEspecificacion;
import com.slash.inventario.domain.Categoria;
import com.slash.inventario.domain.EstatusProducto;
import com.slash.inventario.domain.Producto;
import com.slash.inventario.domain.Proveedor;
import com.slash.inventario.domain.Usuario;

@SessionAttributes({"elementoBusqueda", "numeroPagina"})
@Controller
@RequestMapping("/producto")
public class ProductoController {

	private ProductoRepository productoRepository;
	private CategoriaRepository categoriaRepository;
	private ProveedorRepository proveedorRepository;
	private EstatusProductoRepository estatusProductoRepository;
	private UsuarioRepository usuarioRepository;
	
	public ProductoController(ProductoRepository productoRepository
			, CategoriaRepository categoriaRepository
			, ProveedorRepository proveedorRepository
			, EstatusProductoRepository estatusProductoRepository
			, UsuarioRepository usuarioRepository) {
		
		this.productoRepository = productoRepository;
		this.categoriaRepository = categoriaRepository;
		this.proveedorRepository = proveedorRepository;
		this.estatusProductoRepository = estatusProductoRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	@ModelAttribute(name="entidad")
	public String entidad() {
		return "Producto";
	}
	
	@ModelAttribute(name="elementoBusqueda")
	public Producto producto() {
		return new Producto();
	}
	
	@ModelAttribute(name="urlPaginacion")
	public String urlPaginacion() {
		return "/producto/paginar";
	}
	
	@ModelAttribute(name="categorias")
	public List<Categoria> categorias() {
		Categoria categoria = new Categoria();
		categoria.setNombre("-- Seleccione --");
		
		List<Categoria> categorias = new ArrayList<>();
		categorias.add(categoria);
		categorias.addAll(categoriaRepository.findAll());
		
		return categorias;
	}
	
	@ModelAttribute(name="proveedores")
	public List<Proveedor> proveedores() {
		Proveedor proveedor = new Proveedor();
		proveedor.setNombre("-- Seleccione --");
		
		List<Proveedor> proveedores = new ArrayList<>();
		proveedores.add(proveedor);
		proveedores.addAll(proveedorRepository.findAll());
		
		return proveedores;
	}
	
	@ModelAttribute(name="estatusProducto")
	public List<EstatusProducto> estatusProducto() {
		EstatusProducto estatus = new EstatusProducto();
		estatus.setNombre("-- Seleccione --");
		
		List<EstatusProducto> listaEstatus = new ArrayList<>();
		listaEstatus.add(estatus);
		listaEstatus.addAll(estatusProductoRepository.findAll());
		
		return listaEstatus;
	}
	
	@ModelAttribute(name="usuarios")
	public List<Usuario> usuarios() {
		Usuario usuario = new Usuario();
		usuario.setNombre("-- Seleccione --");
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		usuarios.addAll(usuarioRepository.findAll());
		
		return usuarios;
	}
	
	@GetMapping
	public String home(Model modelo) {
		//Sobreescribe el tipo de dato del objeto elementoBusqueda
		modelo.addAttribute("elementoBusqueda", new Producto());
		
		return "redirect:/producto/buscar";
	}
	
	@GetMapping("/buscar")
	public String home(Model modelo
			, @RequestParam(name="numeroPagina", required=false, defaultValue="0") Integer numeroPagina
			, @ModelAttribute("elementoBusqueda") Producto producto) {
		
		modelo.addAttribute("numeroPagina", numeroPagina);
		
		return consultar(modelo, numeroPagina, producto);
	}
	
	@GetMapping("/paginar")
	public String paginar(Model modelo
			, @RequestParam(name="numeroPagina", required=false, defaultValue="0") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Producto producto) {
		
		modelo.addAttribute("numeroPagina", numeroPagina);
		
		return consultar(modelo, numeroPagina, producto);
	}
	
	private String consultar(Model modelo, Integer numeroPagina, Producto producto) {
		PageRequest pageRequest = PageRequest.of(numeroPagina, 5, Sort.by("id").descending());
		Page<Producto> pagina = productoRepository.findAll(ProductoEspecificacion.of(producto), pageRequest);
		
		modelo.addAttribute("pagina", pagina);
		
		return "producto/home";
	}
	
	@GetMapping("/registrar")
	public String registrar() {
		return "producto/registrar";
	}
	
	@PostMapping("/registrar")
	public String registrar(Producto producto) {
		productoRepository.save(producto);
		
		return "redirect:/producto";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(Model modelo, @PathVariable("id") Integer id) {
		Producto producto = productoRepository.findById(id).orElse(null);
		
		modelo.addAttribute("producto", producto);
		
		return "producto/editar";
	}
	
	@PostMapping("/editar")
	public String editar(Model modelo
			, Producto producto
			, @SessionAttribute("numeroPagina") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Producto productoSesion) {
		
		productoRepository.saveAndFlush(producto);
		
		return consultar(modelo, numeroPagina, productoSesion);
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model modelo, @PathVariable("id") Integer id) {
		Producto producto = productoRepository.findById(id).orElse(null);
		
		modelo.addAttribute("producto", producto);
		
		return "producto/eliminar";
	}
	
	@PostMapping("/eliminar")
	public String eliminar(Model modelo
			, Producto producto
			, @SessionAttribute("numeroPagina") Integer numeroPagina
			, @SessionAttribute("elementoBusqueda") Producto productoSesion) {
		
		productoRepository.deleteById(producto.getId());
		
		return consultar(modelo, numeroPagina, productoSesion);
	}
}
