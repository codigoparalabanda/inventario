package com.slash.inventario.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.slash.inventario.data.ProveedorRepository;
import com.slash.inventario.domain.Proveedor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProveedorTest {

	@Autowired
	private ProveedorRepository proveedorRepository;
	
	@Test
	public void testFindAll() {
		List<Proveedor> proveedores = proveedorRepository.findAll();
		
		assertNotNull("El objeto proveedores es nulo", proveedores);
		assertEquals(4, proveedores.size());
		
		proveedores.forEach(proveedor -> log.info("{}", proveedor));
	}
}
