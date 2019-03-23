package com.slash.inventario.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.slash.inventario.data.EstatusProductoRepository;
import com.slash.inventario.domain.EstatusProducto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EstatusProductoTest {

	@Autowired
	private EstatusProductoRepository estatusProductoRepository;
	
	@Test
	public void testFindAll() {
		List<EstatusProducto> estatusProducto = estatusProductoRepository.findAll();
		
		assertNotNull("El objeto estatusProducto es nulo", estatusProducto);
		assertEquals(5, estatusProducto.size());
		
		estatusProducto.forEach(item -> log.info("{}", item));
	}
}
