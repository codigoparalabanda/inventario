package com.slash.inventario.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.slash.inventario.data.ProductoRepository;
import com.slash.inventario.domain.Categoria;
import com.slash.inventario.domain.EstatusProducto;
import com.slash.inventario.domain.Producto;
import com.slash.inventario.domain.Proveedor;
import com.slash.inventario.domain.Usuario;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductoTest {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Test
	public void testASave() {
		List<Producto> productos = new ArrayList<>();
		productos.add(getProducto1());
		productos.add(getProducto2());
		
		productoRepository.saveAll(productos);
		
		productos.forEach(producto -> {
			assertNotNull("La llave primaria del producto [{}] es nula", producto.getId());
		});
	}
	
	@Test
	public void testBFindAll() {
		List<Producto> productos = productoRepository.findAll();
		
		assertNotNull("El objeto productos es nulo", productos);
		assertEquals(2, productos.size());
		
		productos.forEach(producto -> log.info("{}", producto));
	}
	
	private Producto getProducto1() {
		Producto producto = new Producto();
		
		Categoria categoria = new Categoria();
		categoria.setId((short) 1);
		
		Proveedor proveedor = new Proveedor();
		proveedor.setId((short) 3);
		
		Usuario usuario = new Usuario();
		usuario.setId(2);
		
		EstatusProducto estatusProducto = new EstatusProducto();
		estatusProducto.setId((short) 3);
		
		producto.setNombre("Producto 1");
		producto.setDescripcion("Producto correspondiente a la categoría 1");
		producto.setPrecioUnitario(Double.valueOf("13.99"));
		producto.setStock((short) 13);
		producto.setCategoria(categoria);
		producto.setProveedor(proveedor);
		producto.setUsuario(usuario);
		producto.setEstatus(estatusProducto);
		
		return producto;
	}
	
	private Producto getProducto2() {
		Producto producto = new Producto();
		
		Categoria categoria = new Categoria();
		categoria.setId((short) 2);
		
		Proveedor proveedor = new Proveedor();
		proveedor.setId((short) 1);
		
		Usuario usuario = new Usuario();
		usuario.setId(1);
		
		EstatusProducto estatusProducto = new EstatusProducto();
		estatusProducto.setId((short) 1);
		
		producto.setNombre("Producto 2");
		producto.setDescripcion("Producto correspondiente a la categoría 2");
		producto.setPrecioUnitario(Double.valueOf("15.50"));
		producto.setStock((short) 21);
		producto.setCategoria(categoria);
		producto.setProveedor(proveedor);
		producto.setUsuario(usuario);
		producto.setEstatus(estatusProducto);
		
		return producto;
	}
}
