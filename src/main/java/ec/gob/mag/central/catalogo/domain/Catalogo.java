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

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//============== LOMBOK =============
@Getter
@Setter
@ToString(of = "catId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================

@Entity
@Table(name = "tbl_catalogo", schema = "sc_catalogos")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ord", scope = Catalogo.class)

public class Catalogo implements Serializable {
	private static final long serialVersionUID = -1383240679545632350L;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Catalogo")
	@Id
	@Column(name = "cat_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("catId")
	private Long catId;

	@ApiModelProperty(value = "Nombre del Catalogo")
	@Column(name = "cat_nombre", nullable = false)
	@JsonProperty("catNombre")
	@JsonInclude(Include.NON_NULL)
	private String catNombre;

	@ApiModelProperty(value = "Descripcion del Catalogo")
	@Column(name = "cat_descripcion", nullable = false)
	@JsonProperty("catDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String catDescripcion;

	@ApiModelProperty(value = "Abreviatura del Catalogo")
	@Column(name = "cat_abreviatura", nullable = false)
	@JsonProperty("catAbreviatura")
	@JsonInclude(Include.NON_NULL)
	private String catAbreviatura;

	@ApiModelProperty(value = "Identificativo del Catalogo")
	@Column(name = "cat_identificativo", nullable = false)
	@JsonProperty("catIdentificativo")
	@JsonInclude(Include.NON_NULL)
	private String catIdentificativo;

	@ApiModelProperty(value = "Codigo Catalogo")
	@Column(name = "cat_codigo", nullable = false)
	@JsonProperty("catCodigo")
	@JsonInclude(Include.NON_NULL)
	private String catCodigo;

	@ApiModelProperty(value = "Id padre del registro")
	@Column(name = "id_anterior")
	@JsonProperty("idAnterior")
	@JsonInclude(Include.NON_NULL)
	private Long idAnterior;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "time_stamp")
	@JsonProperty("timeStamp")
	@JsonInclude(Include.NON_NULL)
	private Date timeStamp;

	@ApiModelProperty(value = " Estado de catalogo", notes = "***", position = 10)
	@Column(name = "estado", length = 2)
	@JsonProperty("estado")
	@JsonInclude(Include.NON_NULL)
	private String estado;

	@PrePersist
	public void catPersist() {
		this.estado = "ac";
		this.timeStamp = null;
	}

}
