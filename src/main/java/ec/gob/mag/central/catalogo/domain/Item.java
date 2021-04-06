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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = "iteId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "tbl_item", schema = "sc_catalogos")
public class Item {

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Item")
	@Column(name = "ite_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("iteId")
	private Long iteId;

	@ApiModelProperty(value = "Nombre del Item")
	@Column(name = "ite_nombre", nullable = false)
	@JsonProperty("itemNombre")
	private String iteNombre;

	@ApiModelProperty(value = "Valor del Item")
	@Column(name = "ite_valor", nullable = false)
	@JsonProperty("iteValor")
	private Float iteValor;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cat_id")
	@ApiModelProperty(value = " Clave foránea de la tabla Catalogo", notes = "***")
	@JsonProperty("catalogoTR")
	@JsonManagedReference
	private Catalogo catalogo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipcol_id")
	@ApiModelProperty(value = " Clave foránea de la tabla Tipo Columna", notes = "***")
	@JsonProperty("tipoColumna")
	@JsonBackReference(value = "item-tipoColumna")
	private TipoColumna tipoColumna;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "ite_estado", columnDefinition = "Integer default 11")
	@JsonProperty("iteEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer iteEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ite_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("iteRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date iteRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "ite_reg_usu", nullable = false)
	@JsonProperty("iteRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer iteRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ite_act_fecha")
	@JsonProperty("iteActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date iteActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "ite_act_usu")
	@JsonProperty("iteActUsu")
	private Integer iteActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "ite_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("iteEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean iteEliminado;

	@PrePersist
	void prePersist() {
		this.iteEstado = 11;
		this.iteEliminado = false;
		this.iteRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.iteActFecha = new Date();
	}
}
