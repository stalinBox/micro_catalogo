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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.central.catalogo.util.Util;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.UpdateTimestamp;

//============== LOMBOK =============
@Getter
@Setter
@ToString(of = "iteId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================
@Entity
@Table(name = "tbl_item", schema = "sc_catalogos")
public class Item implements java.io.Serializable {

	private static final long serialVersionUID = -2631428883075299326L;

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
	@Column(name = "ite_eliminado")
	@JsonProperty("iteEliminado")
	private Boolean iteEliminado;

	@ApiModelProperty(value = "Eliminado logico", notes = "***")
	@Column(name = "ite_estado")
	@JsonProperty("iteEstado")
	private Integer iteEstado;

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

	@PrePersist
	public void prePersist() {
		this.timeStamp = Util.dateNow();
		this.estado = "ac";
	}

}
