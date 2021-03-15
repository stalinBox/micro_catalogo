package ec.gob.mag.central.catalogo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constante {

	ESTADO_ACTIVO(11, "Activo"), ESTADO_INACTIVO(12, "Inactivo");

	private Integer codigo;
	private String desc;
}
