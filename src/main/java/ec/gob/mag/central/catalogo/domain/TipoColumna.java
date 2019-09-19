package ec.gob.mag.central.catalogo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@ToString(of = "tipoColumnaId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================
@Entity
@Table(name = "tbl_tipo_columna", schema = "sc_catalogos")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ord", scope = TipoColumna.class)

public class TipoColumna implements Serializable {
	private static final long serialVersionUID = -3124864204002344027L;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Tipo Columna")
	@Id
	@Column(name = "tipoColumna_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("tipoColumnaId")
	@JsonInclude(Include.NON_NULL)
	private Long tipoColumnaId;

	@ApiModelProperty(value = "Nombre de Tipo Columna")
	@JoinColumn(name = "tipoColumna_Nombre")
	@JsonProperty("tipoColumnaNombre")
	@JsonInclude(Include.NON_NULL)
	private String tipoColumnaNombre;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "time_stamp")
	@JsonProperty("timeStamp")
	@JsonInclude(Include.NON_NULL)
	private Date timeStamp;

	@ApiModelProperty(value = "11=activo  12=inactivo")
	@Column(name = "estado", nullable = false)
	@JsonProperty("estado")
	@JsonInclude(Include.NON_NULL)
	private Long estado;

	@PrePersist
	public void prePersist() {
		this.timeStamp = Util.dateNow();
		this.estado = 11L;
	}

}
