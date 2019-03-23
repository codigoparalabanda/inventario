package com.slash.inventario.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.slash.inventario.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Short>, JpaSpecificationExecutor<Categoria> {

}
