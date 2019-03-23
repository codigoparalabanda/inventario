package com.slash.inventario.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Proveedor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Short id;
	
	private String nombre;
	private String direccion;
	private Boolean habilitado;
}
