package ec.gob.mag.central.catalogo.domain;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Catalogo implements Serializable {
	private static final long serialVersionUID = -1383240679545632350L;

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

	@ApiModelProperty(value = "Codigo Catalogo")
	@Column(name = "cat_codigo", nullable = false)
	@JsonProperty("catCodigo")
	private String catCodigo;

	@ApiModelProperty(value = "Id padre del registro")
	@Column(name = "id_anterior")
	@JsonProperty("idAnterior")
	private Long idAnterior;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "time_stamp")
	@JsonProperty("timeStamp")
	private Date timeStamp;

	@ApiModelProperty(value = " Estado de catalogo", notes = "***")
	@Column(name = "estado", length = 2)
	@JsonProperty("estado")
	private String estado;

	@ApiModelProperty(value = "Eliminado logico", notes = "***")
	@Column(name = "cat_eliminado")
	@JsonProperty("catEliminado")
	private Boolean catEliminado;

	@ApiModelProperty(value = "Estado del registro")
	@Column(name = "cat_estado")
	@JsonProperty("catEstado")
	private Integer catEstado;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "agr_id")
	@JsonProperty("agrupacion")
	private List<Agrupacion> agrupacion;

	@PrePersist
	public void catPersist() {
		this.estado = "ac";
		this.timeStamp = new Date();
	}

}
