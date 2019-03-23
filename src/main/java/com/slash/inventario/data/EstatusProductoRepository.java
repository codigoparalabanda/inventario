package com.slash.inventario.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slash.inventario.domain.EstatusProducto;

public interface EstatusProductoRepository extends JpaRepository<EstatusProducto, Short> {

}
