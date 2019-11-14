package ec.gob.mag.central.catalogo.domain;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ec.gob.mag.central.catalogo.util.Util;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//============== LOMBOK =============

@Getter
@Setter
@ToString(of = "agrId")
@EqualsAndHashCode(of = "agrId")
@NoArgsConstructor
@AllArgsConstructor
@Builder

//========== JPA ======================

@Entity
@Table(name = "tbl_agrupacion", schema = "sc_catalogos")
public class Agrupacion implements Serializable {
	private static final long serialVersionUID = -4017650183258693515L;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla agrupacion")
	@Id
	@Column(name = "agr_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("agrId")
	private Long agrId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipcat_id")
	@ApiModelProperty(value = " Clave foránea de la tabla Tipo catalogo", notes = "***")
	@JsonProperty("tipoCatalogo")
	@JsonInclude(Include.NON_NULL)
	private TipoCatalogo tipoCatalogo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ori_id")
	@ApiModelProperty(value = " Clave foránea de la tabla Origen", notes = "***")
	@JsonProperty("origen")
	@JsonInclude(Include.NON_NULL)
	private Origen origen;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Catalogo")
	@Column(name = "cat_id_padre", nullable = false)
	@JsonProperty("catIdPadre")
	@JsonInclude(Include.NON_NULL)
	private Long catIdPadre;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Catalogo")
	@Column(name = "cat_id_hijo", nullable = false)
	@JsonProperty("catIdHijo")
	@JsonInclude(Include.NON_NULL)
	private Long catIdHijo;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "time_stamp")
	@JsonProperty("timeStamp")
	@JsonInclude(Include.NON_NULL)
	private Date timeStamp;

	@ApiModelProperty(value = " Estado de agrupacion", notes = "***", position = 10)
	@Column(name = "estado", length = 2)
	@JsonProperty("estado")
	@JsonInclude(Include.NON_NULL)
	private String estado;

	@PrePersist
	public void prePersist() {
		this.timeStamp = Util.dateNow();
		this.estado = "ac";
	}
}
