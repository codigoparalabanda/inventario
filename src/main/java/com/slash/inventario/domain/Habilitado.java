package com.slash.inventario.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Habilitado {

	private final Boolean valor;
	private final String leyenda;
}
