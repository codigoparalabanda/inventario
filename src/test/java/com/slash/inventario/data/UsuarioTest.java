package com.slash.inventario.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.slash.inventario.data.UsuarioRepository;
import com.slash.inventario.domain.Usuario;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void testFindAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		assertNotNull("El objeto usuarios es nulo", usuarios);
		assertEquals(3, usuarios.size());
		
		usuarios.forEach(usuario -> log.info("{}", usuario));
	}
}
