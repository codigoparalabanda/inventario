package com.slash.inventario.data.spec;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.slash.inventario.domain.Perfil;
import com.slash.inventario.domain.Usuario;

public class UsuarioEspecificacion {

	public static Specification<Usuario> of(Usuario usuario) {
		
		return (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			
			if(Optional.ofNullable(usuario.getId()).isPresent()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), usuario.getId()));
			}
			
			if(Optional.ofNullable(usuario.getNombre()).isPresent() && !usuario.getNombre().isEmpty()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nombre"), "%".concat(usuario.getNombre()).concat("%")));
			}
			
			if(Optional.ofNullable(usuario.getApellidos()).isPresent() && !usuario.getApellidos().isEmpty()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("apellidos"), "%".concat(usuario.getApellidos()).concat("%")));
			}
			
			//Filtra el perfil del usuario
			filtrarPerfil(usuario.getPerfil(), predicate, criteriaBuilder, root);
			
			if(Optional.ofNullable(usuario.getHabilitado()).isPresent()) {
				if(usuario.getHabilitado()) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(root.get("habilitado")));
				} else {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.isFalse(root.get("habilitado")));
				}
			}
			
			return predicate;
		};
	}
	
	private static void filtrarPerfil(Perfil perfil, Predicate predicate, CriteriaBuilder criteriaBuilder, Root<Usuario> root) {
		Join<Usuario, Perfil> usuarioPerfilJoin = root.join("perfil");
		
		if(Optional.ofNullable(perfil).isPresent() && Optional.ofNullable(perfil.getId()).isPresent()) {
			usuarioPerfilJoin.on(predicate, criteriaBuilder.and(predicate, criteriaBuilder.equal(usuarioPerfilJoin.get("id"), perfil.getId())));
		}
	}
}
