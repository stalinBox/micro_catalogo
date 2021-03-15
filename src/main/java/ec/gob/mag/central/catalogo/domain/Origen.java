package ec.gob.mag.central.catalogo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.central.catalogo.util.Util;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//============== LOMBOK =============
@Getter
@Setter
@ToString(of = "oriId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================

@Entity
@Table(name = "tbl_origen", schema = "sc_catalogos")
public class Origen implements Serializable {
	private static final long serialVersionUID = 1722665206845571246L;

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

	@ApiModelProperty(value = "Fecha en la que hizo la actualización")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "time_stamp")
	@JsonProperty("timeStamp")
	private Date timeStamp;

	@ApiModelProperty(value = " Estado de tipo catalogo", notes = "***", position = 10)
	@Column(name = "estado", length = 2)
	@JsonProperty("estado")
	private String estado;

	@ApiModelProperty(value = "Eliminado logico", notes = "***")
	@Column(name = "ori_eliminado")
	@JsonProperty("oriEliminado")
	private Boolean oriEliminado;

	@ApiModelProperty(value = "Estado del registro", notes = "***")
	@Column(name = "ori_estado")
	@JsonProperty("oriEstado")
	private Integer oriEstado;

	@PrePersist
	public void prePersist() {
		this.timeStamp = Util.dateNow();
		this.estado = "ac";
	}
}
