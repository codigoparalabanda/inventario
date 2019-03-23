package com.slash.inventario.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.slash.inventario.data.PerfilRepository;
import com.slash.inventario.domain.Perfil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PerfilTest {

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Test
	public void testFindAll() {
		List<Perfil> perfiles = perfilRepository.findAll();
		
		assertNotNull("El objeto perfiles es nulo", perfiles);
		assertEquals(5, perfiles.size());
		
		perfiles.forEach(perfil -> log.info("{}", perfil));
	}
}
