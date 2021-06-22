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
import javax.persistence.Transient;
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
@Table(name = "tbl_catalogo", schema = "sc_catalogos")
public class Catalogo {

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Catalogo")
	@Column(name = "cat_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("catId")
	private Long catId;

	@ApiModelProperty(value = "Nombre del Catalogo")
	@Column(name = "cat_nombre", nullable = false)
	@JsonProperty("catNombre")
	private String catNombre;

	@ApiModelProperty(value = "Descripcion del Catalogo")
	@Column(name = "cat_descripcion", nullable = false)
	@JsonProperty("catDescripcion")
	private String catDescripcion;

	@ApiModelProperty(value = "Abreviatura del Catalogo")
	@Column(name = "cat_abreviatura", nullable = false)
	@JsonProperty("catAbreviatura")
	private String catAbreviatura;

	@ApiModelProperty(value = "Identificativo del Catalogo")
	@Column(name = "cat_identificativo", nullable = false)
	@JsonProperty("catIdentificativo")
	private String catIdentificativo;

	@Transient
	private Integer tipcatIdHijo;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "agr_id")
	@JsonProperty("agrupacion")
	private List<Agrupacion> agrupacion;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "cat_estado", columnDefinition = "Integer default 11")
	@JsonProperty("catEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer catEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cat_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("catRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date catRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "cat_reg_usu", nullable = false)
	@JsonProperty("catRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer catRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cat_act_fecha")
	@JsonProperty("catActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date catActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "cat_act_usu")
	@JsonProperty("catActUsu")
	private Integer catActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "cat_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("catEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean catEliminado;

	@PrePersist
	void prePersist() {
		this.catEstado = 11;
		this.catEliminado = false;
		this.catRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.catActFecha = new Date();
	}

}
