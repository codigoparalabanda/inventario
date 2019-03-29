package com.slash.inventario.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Proveedor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Short id;
	
	@Size(min=4, max=20, message="El nombre debe tener entre 4 y 30 caracteres")
	private String nombre;
	
	@Size(min=5, max=150, message="La dirección debe tener entre 5 y 150 caracteres")
	private String direccion;
	
	private Boolean habilitado;
}
