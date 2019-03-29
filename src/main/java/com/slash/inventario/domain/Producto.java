package com.slash.inventario.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min=4, max=20, message="El nombre debe tener entre 4 y 30 caracteres")
	private String nombre;
	
	@Size(min=4, max=250, message="La descripción debe tener entre 4 y 250 caracteres")
	private String descripcion;
	
	@Size(min=1, max=7, message="")
	@Digits(fraction=2, integer=4, message="El precio unitario sólo acepta valores numéricos de hasta 4 digitos enteros y 2 decimales")
	@Column(name="precio_unitario")
	private String precioUnitario;
	
	@Column(name="fecha_registro")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date fechaRegistro;
	
	@Size(min=1, max=3, message="")
	@Digits(fraction=0, integer=3, message="El stock sólo acepta valores numéricos de hasta 3 enteros")
	private String stock;
	
	@ManyToOne(targetEntity=Categoria.class)
	@JoinColumn(name="categoria")
	private Categoria categoria;
	
	@ManyToOne(targetEntity=Proveedor.class)
	@JoinColumn(name="proveedor")
	private Proveedor proveedor;
	
	@ManyToOne(targetEntity=Usuario.class)
	@JoinColumn(name="usuario_captura")
	private Usuario usuario;
	
	@ManyToOne(targetEntity=EstatusProducto.class)
	@JoinColumn(name="estatus")
	private EstatusProducto estatus;
	
	@PrePersist
	public void setFechaRegistro() {
		this.fechaRegistro = new Date();
	}
}
