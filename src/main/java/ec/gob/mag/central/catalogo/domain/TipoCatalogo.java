package ec.gob.mag.central.catalogo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = "tipocatId")
@EqualsAndHashCode(of = "tipocatId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Entity
@Table(name = "tbl_tipo_catalogo", schema = "sc_catalogos")
public class TipoCatalogo {

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Tipo Catalogo")
	@Column(name = "tipcat_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("tipocatId")
	@JsonInclude(Include.NON_NULL)
	private Long tipocatId;

	@ApiModelProperty(value = "Nombre de Tipo Catalogo")
	@Column(name = "tipcat_nombre")
	@JsonProperty("tipocatNombre")
	@JsonInclude(Include.NON_NULL)
	private String tipocatNombre;

	@ApiModelProperty(value = "Descripcion de Tipo Catalogo")
	@Column(name = "tipcat_descripcion")
	@JsonProperty("tipocatDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String tipocatDescripcion;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tipoCatalogo", cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JsonProperty("agrupacion")
	@JsonInclude(Include.NON_NULL)
	@JsonManagedReference
	private List<Agrupacion> agrupacion;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "tipcat_estado", columnDefinition = "Integer default 11")
	@JsonProperty("tipcatEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer tipcatEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tipcat_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("tipcatRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date tipcatRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "tipcat_reg_usu", nullable = false)
	@JsonProperty("tipcatRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer tipcatRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tipcat_act_fecha")
	@JsonProperty("tipcatActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date tipcatActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "tipcat_act_usu")
	@JsonProperty("tipcatActUsu")
	private Integer tipcatActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "tipcat_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("tipcatEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean tipcatEliminado;

	@PrePersist
	void prePersist() {
		this.tipcatEstado = 11;
		this.tipcatEliminado = false;
		this.tipcatRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.tipcatActFecha = new Date();
	}
}
