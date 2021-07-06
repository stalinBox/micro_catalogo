package ec.gob.mag.central.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.dto.CatalogoRecursiveDTO;

@Repository("catalogoRecursiveDTORepository")
public interface CatalogoRecursiveDTORepository extends CrudRepository<CatalogoRecursiveDTO, Integer> {

	// RECURSIVA PARA AFC
	@Query(value = "select  distinct agr.tipcat_id AS \"tipCatId\",\r\n"
			+ "       tcat.tipcat_descripcion AS \"tipCatDescripcion\",\r\n"
			+ "       agr.cat_id_padre AS \"catIdPadre\",\r\n" + "       agr.cat_id_hijo AS \"catIdHijo\",\r\n"
			+ "       cath.cat_nombre AS \"catNombre\", \r\n" + "       agri.tipcat_id AS tipcatIdHijo\r\n"
			+ "    from sc_catalogos.tbl_agrupacion agr\r\n"
			+ "    left join sc_catalogos.tbl_tipo_catalogo tcat on tcat.tipcat_id = agr.tipcat_id\r\n"
			+ "    left join sc_catalogos.tbl_catalogo cat on cat.cat_id=agr.cat_id_padre\r\n"
			+ "    left join sc_catalogos.tbl_catalogo cath on cath.cat_id=agr.cat_id_hijo\r\n"
			+ "    left join sc_catalogos.tbl_agrupacion agri on agri.cat_id_padre = agr.cat_id_hijo\r\n"
			+ "    where agr.tipcat_id IN (?1)\r\n" + "    order by agr.cat_id_hijo;", nativeQuery = true)
	List<CatalogoRecursiveDTO> findCatalogoRecursiveDTO(Integer catIdHijo);
}