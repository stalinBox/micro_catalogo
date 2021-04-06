package ec.gob.mag.central.catalogo.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = "tipoColumnaId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "tbl_tipo_columna", schema = "sc_catalogos")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ord", scope = TipoColumna.class)
public class TipoColumna {

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Tipo Columna")
	@Column(name = "tipcol_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("tipoColumnaId")
	private Long tipoColumnaId;

	@ApiModelProperty(value = "Nombre de Tipo Columna")
	@JoinColumn(name = "tipcol_nombre")
	@JsonProperty("tipoColumnaNombre")
	private String tipoColumnaNombre;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "tipcol_estado", columnDefinition = "Integer default 11")
	@JsonProperty("tipcolEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer tipcolEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tipcol_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("tipcolRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date tipcolRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "tipcol_reg_usu", nullable = false)
	@JsonProperty("tipcolRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer tipcolRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tipcol_act_fecha")
	@JsonProperty("tipcolActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date tipcolActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "tipcol_act_usu")
	@JsonProperty("tipcolActUsu")
	private Integer tipcolActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "tipcol_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("tipcolEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean tipcolEliminado;

	@PrePersist
	void prePersist() {
		this.tipcolEstado = 11;
		this.tipcolEliminado = false;
		this.tipcolRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.tipcolActFecha = new Date();
	}

}
