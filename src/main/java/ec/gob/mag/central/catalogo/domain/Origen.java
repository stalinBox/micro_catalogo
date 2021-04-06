package ec.gob.mag.central.catalogo.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = "oriId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Entity
@Table(name = "tbl_origen", schema = "sc_catalogos")
public class Origen {

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Origen")
	@Column(name = "ori_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("oriId")
	private Long oriId;

	@ApiModelProperty(value = "Nombre del Origen")
	@Column(name = "ori_nombre")
	@JsonProperty("oriNombre")
	private String oriNombre;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "ori_estado", columnDefinition = "Integer default 11")
	@JsonProperty("oriEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer oriEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ori_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("oripRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date oriRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "ori_reg_usu", nullable = false)
	@JsonProperty("oriRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer oriRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ori_act_fecha")
	@JsonProperty("oriActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date oriActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "ori_act_usu")
	@JsonProperty("oriActUsu")
	private Integer oriActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "ori_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("oriEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean oriEliminado;

	@PrePersist
	void prePersist() {
		this.oriEstado = 11;
		this.oriEliminado = false;
		this.oriRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.oriActFecha = new Date();
	}
}
