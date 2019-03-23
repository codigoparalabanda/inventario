package com.slash.inventario.data.spec;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.slash.inventario.domain.Categoria;
import com.slash.inventario.domain.EstatusProducto;
import com.slash.inventario.domain.Producto;
import com.slash.inventario.domain.Proveedor;
import com.slash.inventario.domain.Usuario;

public class ProductoEspecificacion {

	public static Specification<Producto> of(Producto producto) {
		
		return (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			
			if(Optional.ofNullable(producto.getId()).isPresent()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), producto.getId()));
			}
			
			if(Optional.ofNullable(producto.getNombre()).isPresent() && !producto.getNombre().isEmpty()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nombre"), "%".concat(producto.getNombre()).concat("%")));
			}
			
			if(Optional.ofNullable(producto.getDescripcion()).isPresent() && !producto.getDescripcion().isEmpty()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("descripcion"), "%".concat(producto.getDescripcion()).concat("%")));
			}
			
			if(Optional.ofNullable(producto.getPrecioUnitario()).isPresent()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("precioUnitario"), producto.getPrecioUnitario()));
			}
			
			if(Optional.ofNullable(producto.getFechaRegistro()).isPresent()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("fechaRegistro"), producto.getFechaRegistro()));
			}
			
			if(Optional.ofNullable(producto.getStock()).isPresent()) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("stock"), producto.getStock()));
			}
			
			//Filtra por categoria
			filtrarCategoria(producto.getCategoria(), predicate, criteriaBuilder, root);
			
			//Filtra por proveedor
			filtrarProveedor(producto.getProveedor(), predicate, criteriaBuilder, root);
			
			//Filtra por usuario
			filtrarUsuario(producto.getUsuario(), predicate, criteriaBuilder, root);
			
			//Filtra por estatus del producto
			filtrarEstatus(producto.getEstatus(), predicate, criteriaBuilder, root);
			
			return predicate;
		};
	}
	
	private static void filtrarCategoria(Categoria categoria, Predicate predicate, CriteriaBuilder criteriaBuilder, Root<Producto> root) {
		Join<Producto, Categoria> productoCategoriaJoin = root.join("categoria");
		
		if(Optional.ofNullable(categoria).isPresent() && Optional.ofNullable(categoria.getId()).isPresent()) {
			productoCategoriaJoin.on(predicate, criteriaBuilder.and(predicate, criteriaBuilder.equal(productoCategoriaJoin.get("id"), categoria.getId())));
		}
	}
	
	private static void filtrarProveedor(Proveedor proveedor, Predicate predicate, CriteriaBuilder criteriaBuilder, Root<Producto> root) {
		Join<Producto, Proveedor> productoProveedorJoin = root.join("proveedor");
		
		if(Optional.ofNullable(proveedor).isPresent() && Optional.ofNullable(proveedor.getId()).isPresent()) {
			productoProveedorJoin.on(predicate, criteriaBuilder.and(predicate, criteriaBuilder.equal(productoProveedorJoin.get("id"), proveedor.getId())));
		}
	}
	
	private static void filtrarUsuario(Usuario usuario, Predicate predicate, CriteriaBuilder criteriaBuilder, Root<Producto> root) {
		Join<Producto, Usuario> productoUsuarioJoin = root.join("usuario");
		
		if(Optional.ofNullable(usuario).isPresent() && Optional.ofNullable(usuario.getId()).isPresent()) {
			productoUsuarioJoin.on(predicate, criteriaBuilder.and(predicate, criteriaBuilder.equal(productoUsuarioJoin.get("id"), usuario.getId())));
		}
	}
	
	private static void filtrarEstatus(EstatusProducto estatusProducto, Predicate predicate, CriteriaBuilder criteriaBuilder, Root<Producto> root) {
		Join<Producto, EstatusProducto> productoEstatusJoin = root.join("estatus");
		
		if(Optional.ofNullable(estatusProducto).isPresent() && Optional.ofNullable(estatusProducto.getId()).isPresent()) {
			productoEstatusJoin.on(predicate, criteriaBuilder.and(predicate, criteriaBuilder.equal(productoEstatusJoin.get("id"), estatusProducto.getId())));
		}
	}
}
