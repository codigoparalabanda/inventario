package com.slash.inventario.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	private String apellidos;
	private String contrasennia;
	private Boolean habilitado;
	
	@ManyToOne(targetEntity=Perfil.class)
	@JoinColumn(name="perfil")
	private Perfil perfil;
}
