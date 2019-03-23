package com.slash.inventario.data.spec;

import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.slash.inventario.domain.Proveedor;

public class ProveedorEspecificacion {

	public static Specification<Proveedor> of(Proveedor proveedor) {
		
		return (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			
			if(Optional.ofNullable(proveedor.getId()).isPresent()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), proveedor.getId()));
			}
			
			if(Optional.ofNullable(proveedor.getNombre()).isPresent() && !proveedor.getNombre().isEmpty()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nombre"), "%".concat(proveedor.getNombre()).concat("%")));
			}
			
			if(Optional.ofNullable(proveedor.getDireccion()).isPresent() && !proveedor.getDireccion().isEmpty()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("direccion"), "%".concat(proveedor.getDireccion()).concat("%")));
			}
			
			if(Optional.ofNullable(proveedor.getHabilitado()).isPresent()) {
				if(proveedor.getHabilitado()) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(root.get("habilitado")));
				} else {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.isFalse(root.get("habilitado")));
				}
			}
			
			return predicate;
		};
	}
}
