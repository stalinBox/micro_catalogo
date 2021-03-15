package ec.gob.mag.central.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ec.gob.mag.central.catalogo.domain.TipoCatalogo;

@Repository("tipoCatalogoRepository")
public interface TipoCatalogoRepository extends CrudRepository<TipoCatalogo, Long> {

	List<TipoCatalogo> findByTipcatEliminadoAndTipcatEstado(boolean tipocatEliminado, Integer tipcatEstado);

	@Query("SELECT t FROM TipoCatalogo t WHERE UPPER(t.tipocatNombre) NOT LIKE %?1% AND UPPER(t.tipocatDescripcion) NOT LIKE %?1% AND t.tipcatEliminado = false AND t.tipcatEstado=11")
	List<TipoCatalogo> findTiposCatalogos(String nombre);

	@SuppressWarnings("unchecked")
	TipoCatalogo save(TipoCatalogo tipocatId);

}
