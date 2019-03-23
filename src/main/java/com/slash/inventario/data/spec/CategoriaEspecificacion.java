package com.slash.inventario.data.spec;

import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.slash.inventario.domain.Categoria;

public class CategoriaEspecificacion {

	public static Specification<Categoria> of(Categoria categoria) {
		
		return (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			
			if(Optional.ofNullable(categoria.getId()).isPresent()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), categoria.getId()));
			}
			
			if(Optional.ofNullable(categoria.getNombre()).isPresent() && !categoria.getNombre().isEmpty()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nombre"), "%".concat(categoria.getNombre()).concat("%")));
			}
			
			return predicate;
		};
	}
}
