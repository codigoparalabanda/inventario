package com.slash.inventario.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min=3, max=20, message="El nombre debe tener entre 3 y 20 caracteres")
	private String nombre;
	
	@Size(min=3, max=60, message="Los apellidos deben tener entre 3 y 60 caracteres")
	private String apellidos;
	
	@Size(min=8, max=10, message="La contraseña debe tener entre 8 y 10 caracteres")
	private String contrasennia;
	
	@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
			, message = "La cuenta de correcto electrónico debe estar bien formada")
	private String email;
	
	private Boolean habilitado;
	
	@ManyToOne(targetEntity=Perfil.class)
	@JoinColumn(name="perfil")
	private Perfil perfil;
}
