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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ord", scope = Item.class)

public class Item implements java.io.Serializable {

	private static final long serialVersionUID = -2631428883075299326L;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Item")
	@Id
	@Column(name = "ite_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("iteId")
	private Long iteId;

	@ApiModelProperty(value = "Nombre del Item")
	@Column(name = "ite_nombre", nullable = false)
	@JsonProperty("itemNombre")
	@JsonInclude(Include.NON_NULL)
	private String iteNombre;

	@ApiModelProperty(value = "Valor del Item")
	@Column(name = "ite_valor", nullable = false)
	@JsonProperty("iteValor")
	@JsonInclude(Include.NON_NULL)
	private Float iteValor;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cat_id")
	@ApiModelProperty(value = " Clave foránea de la tabla Catalogo", notes = "***")
	@JsonProperty("catalogo")
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value = "item-catalogo")
	private Catalogo catalogo;

	@Transient
	@ApiModelProperty(value = " Información Catalogo", notes = "***")
	@JsonProperty("catalogoTR")
	@JsonInclude(Include.NON_NULL)
	private Catalogo catalogoTR;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipcol_id")
	@ApiModelProperty(value = " Clave foránea de la tabla Tipo Columna", notes = "***")
	@JsonProperty("tipoColumna")
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value = "item-tipoColumna")
	private TipoColumna tipoColumna;

	@PrePersist
	public void prePersist() {
		this.timeStamp = Util.dateNow();
		this.estado = "ac";
	}

}
