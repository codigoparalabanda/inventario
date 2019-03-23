package com.slash.inventario.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.slash.inventario.data.CategoriaRepository;
import com.slash.inventario.data.spec.CategoriaEspecificacion;
import com.slash.inventario.domain.Categoria;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaTests {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Test
	public void testFindAll() {
		log.info("***** testFindAll *****");
		
		List<Categoria> categorias = categoriaRepository.findAll();
		
		assertNotNull("El objeto categorias es nullo", categorias);
		assertEquals(3, categorias.size());
		
		categorias.forEach(categoria -> log.info("{}", categoria));
	}
	
	@Test
	public void testFindAllSpec() {
		log.info("***** testFindAllSpec *****");
		
		Categoria categoria = new Categoria();
		categoria.setNombre("categoria 2");
		
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());
		Page<Categoria> pagina = categoriaRepository.findAll(CategoriaEspecificacion.of(categoria), pageRequest);
		
		assertEquals(1, pagina.getTotalElements());
		
		pagina.getContent().forEach(item -> log.info("{}", item));
	}
}

