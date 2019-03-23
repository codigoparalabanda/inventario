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

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	private String descripcion;
	
	@Column(name="precio_unitario")
	private Double precioUnitario;
	
	@Column(name="fecha_registro")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date fechaRegistro;
	
	private Short stock;
	
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
