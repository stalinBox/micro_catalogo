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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.gob.mag.central.catalogo.util.Util;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Data
//========== JPA ======================

@Entity
@Table(name = "tbl_agrupacion", schema = "sc_catalogos")
public class Agrupacion implements Serializable {
	private static final long serialVersionUID = -4017650183258693515L;

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

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Catalogo")
	@Column(name = "cat_id_hijo", nullable = false)
	@JsonProperty("catIdHijo")
	private Long catIdHijo;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "time_stamp")
	@JsonProperty("timeStamp")
	private Date timeStamp;

	@ApiModelProperty(value = " Estado de agrupacion")
	@Column(name = "estado", length = 2)
	@JsonProperty("estado")
	private String estado;

	@ApiModelProperty(value = "Eliminado logico")
	@Column(name = "agr_eliminado")
	@JsonProperty("agrEliminado")
	private Boolean agrEliminado;

	@ApiModelProperty(value = "Estado del campo")
	@Column(name = "agr_estado")
	@JsonProperty("agrEstado")
	private Integer agrEstado;

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

	@PrePersist
	public void prePersist() {
		this.timeStamp = Util.dateNow();
		this.estado = "ac";
	}
}
