package com.slash.inventario.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slash.inventario.domain.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Short> {

}
