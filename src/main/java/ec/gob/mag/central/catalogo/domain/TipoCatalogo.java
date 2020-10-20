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
@ToString(of = "tipocatId")
@EqualsAndHashCode(of = "tipocatId")
@NoArgsConstructor
@AllArgsConstructor
@Builder

//========== JPA ======================
@Entity
@Table(name = "tbl_tipo_catalogo", schema = "sc_catalogos")
public class TipoCatalogo implements Serializable {
	private static final long serialVersionUID = -3124864204002344027L;

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

	@ApiModelProperty(value = "Fecha en la que hizo la actualización")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "time_stamp")
	@JsonProperty("timeStamp")
	@JsonInclude(Include.NON_NULL)
	private Date timeStamp;

	@ApiModelProperty(value = " Estado de tipo catalogo", notes = "***", position = 10)
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
