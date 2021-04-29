package ec.gob.mag.central.catalogo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = "catId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Entity
@Table(name = "tbl_homologacion", schema = "sc_catalogos")
public class Homologacion {

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Homologacion")
	@Column(name = "hom_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("homId")
	private Long homId;
	
	@ApiModelProperty(value = "Id Catalogo")
	@Column(name = "cat_id", nullable = false)
	@JsonProperty("catId")
	private Long catId;

	@ApiModelProperty(value = "Nombre del Catalogo")
	@Column(name = "cat_nombre")
	@JsonProperty("catNombre")
	private String catNombre;
	
	
	@ApiModelProperty(value = "Id Homologado")
	@Column(name = "hom_id_homologado", nullable = false)
	@JsonProperty("homIdHomologado")
	private String homIdHomologado;
	
	
	@ApiModelProperty(value = "Id Origen")
	@Column(name = "ori_id", nullable = false)
	@JsonProperty("oriId")
	private Long oriId;
	
	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "hom_estado", columnDefinition = "Integer default 11")
	@JsonProperty("homEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer homEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hom_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("homRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date homRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "hom_reg_usu", nullable = false)
	@JsonProperty("homRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer homRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hom_act_fecha")
	@JsonProperty("homActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date homActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "hom_act_usu")
	@JsonProperty("homActUsu")
	private Integer homActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "hom_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("homEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean homEliminado;

	@PrePersist
	void prePersist() {
		this.homEstado = 11;
		this.homEliminado = false;
		this.homRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.homActFecha = new Date();
	}

}
