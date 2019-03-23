package com.slash.inventario.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.slash.inventario.domain.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Short>, JpaSpecificationExecutor<Proveedor> {

}
