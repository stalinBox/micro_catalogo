package ec.gob.mag.central.catalogo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = "agrId")
@EqualsAndHashCode(of = "agrId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_agrupacion", schema = "sc_catalogos")
public class Agrupacion {

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla agrupacion")
	@Column(name = "agr_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("agrId")
	private Long agrId;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Catalogo")
	@Column(name = "cat_id_padre", nullable = false)
	@JsonProperty("catIdPadre")
	private Long catIdPadre;

	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla Catalogo")
	@Column(name = "cat_id_hijo", nullable = false)
	@JsonProperty("catIdHijo")
	private Long catIdHijo;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ori_id")
	@ApiModelProperty(value = " Clave foránea de la tabla Origen", notes = "***")
	@JsonProperty("origen")
	private Origen origen;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipcat_id", insertable = false, updatable = false)
	@ApiModelProperty(value = " Clave foránea de la tabla Catalogo", notes = "***")
	@JsonProperty("tipoCatalogo")
	@JsonManagedReference
	private TipoCatalogo tipoCatalogo;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "agr_estado", columnDefinition = "Integer default 11")
	@JsonProperty("agrEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer agrEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "agr_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("agrRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date agrRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "agr_reg_usu", nullable = false)
	@JsonProperty("agrRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer agrRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "agr_act_fecha")
	@JsonProperty("agrActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date agrActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "agr_act_usu")
	@JsonProperty("agrActUsu")
	private Integer agrActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "agr_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("agrEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean agrEliminado;

	@PrePersist
	void prePersist() {
		this.agrEstado = 11;
		this.agrEliminado = false;
		this.agrRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.agrActFecha = new Date();
	}
}
