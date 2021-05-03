package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Catalogo;

@Repository("catalogoRepository")
public interface CatalogoRepository extends CrudRepository<Catalogo, Long> {

	List<Catalogo> findByCatEliminadoAndCatEstadoEquals(boolean catEliminado, Integer catEstado);

	List<Catalogo> findByCatIdInAndCatEliminadoAndCatEstado(List<Long> catCodigo, boolean tipocatEliminado,
			Integer tipcatEstado);

	@Query("select ct from Catalogo ct " + "inner join Homologacion hm on ct.catId = hm.catId "
			+ "where hm.homIdHomologado = ?1 and hm.oriId = ?2 and ct.catEliminado = ?3 and ct.catEstado = ?4")
	Optional<Catalogo> findCatalogo(String catalogo, Long origen, boolean catEliminado, Integer catEstado);

	@Query("select distinct ct from Catalogo ct inner join Agrupacion ag on (ct.catId = ag.catIdHijo) "
			+ "inner join TipoCatalogo tc on ag.tipoCatalogo.tipocatId = tc.tipocatId "
			+ "where  tc.tipcatEstado = 11 and tc.tipcatEliminado = false and ag.tipoCatalogo.tipocatId = ?1 and ag.agrEstado = 11 and ag.agrEliminado = false "
			+ "and ct.catEliminado = false and ct.catEstado = 11 ")
	List<Catalogo> findCatalogosByTipo(Long tipCatalogo);

	Optional<Catalogo> findByCatIdAndCatEliminadoAndCatEstado(Long catId, boolean catEliminado, Integer catEstado);

	// RECURSIVA
	@Query(value = "SELECT * FROM sc_catalogos.tbl_agrupacion agr "
			+ "    LEFT JOIN sc_catalogos.tbl_catalogo cat on cat.cat_id = agr.cat_id_hijo "
			+ "    LEFT JOIN sc_catalogos.tbl_agrupacion agr1 on agr1.cat_id_hijo=agr.cat_id_padre "
			+ "    LEFT JOIN sc_catalogos.tbl_catalogo cat1 on cat1.cat_id = agr.cat_id_padre "
			+ "    WHERE agr.cat_id_padre =?1  AND cat.cat_estado = 11 AND cat.cat_eliminado = false", nativeQuery = true)
	List<Catalogo> findByAgrupacion_catIdPadre(Long catIdHijo);
}
