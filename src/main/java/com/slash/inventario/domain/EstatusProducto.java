package com.slash.inventario.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="estatus_producto")
public class EstatusProducto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Short id;
	
	private String nombre;
}
