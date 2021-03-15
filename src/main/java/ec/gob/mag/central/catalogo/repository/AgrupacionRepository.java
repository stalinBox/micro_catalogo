package ec.gob.mag.central.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Agrupacion;

@Repository("agrupacionRepository")
public interface AgrupacionRepository extends CrudRepository<Agrupacion, Long> {

	List<Agrupacion> findByAgrEliminadoAndAgrEstado(boolean agrEliminado, Integer agrEstado);

	List<Agrupacion> findByAgrIdAndAgrEliminadoAndAgrEstado(Long agrId, boolean agrEliminado, Integer agrEstado);

	List<Agrupacion> findByTipoCatalogo_tipocatIdAndTipoCatalogo_tipcatEliminadoAndTipoCatalogo_TipcatEstadoAndAgrEliminadoAndAgrEstado(
			Long tipocatId, boolean tipocatEliminado, Integer tipcatEstado, boolean agrEliminado, Integer agrEstado);

	@Query("SELECT a FROM Agrupacion a JOIN Catalogo c ON a.catIdPadre = c.catId "
			+ "WHERE a.catIdPadre = ?1 AND a.agrEliminado = false AND a.agrEstado = 11 AND c.catEliminado = false AND c.catEstado = 11 ")
	List<Agrupacion> findByCatalogoPadreAndAgrEliminadoAndAgrEstado(Long catPadre, boolean agrEliminado,
			Integer agrEstado);

	List<Agrupacion> findByCatIdHijoAndAgrEliminadoAndAgrEstado(Long catHijo, boolean agrEliminado, Integer agrEstado);

	List<Agrupacion> findByCatIdPadreAndTipoCatalogo_tipocatIdAndTipoCatalogo_tipcatEliminadoAndTipoCatalogo_TipcatEstadoAndAgrEliminadoAndAgrEstado(
			Long catIdPadre, Long tipocatId, boolean tipocatEliminado, Integer tipcatEstado, boolean agrEliminado,
			Integer agrEstado);

	List<Agrupacion> findByCatIdPadreNotAndTipoCatalogo_tipocatIdAndTipoCatalogo_tipcatEliminadoAndTipoCatalogo_TipcatEstadoAndAgrEliminadoAndAgrEstado(
			Long catIdPadre, Long tipocatId, boolean tipocatEliminado, Integer tipcatEstado, boolean agrEliminado,
			Integer agrEstado);

	@SuppressWarnings("unchecked")
	Agrupacion save(Agrupacion agrupacion);

}
