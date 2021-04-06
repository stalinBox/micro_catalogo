package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Catalogo;

@Repository("catalogoRepository")
public interface CatalogoRepository extends CrudRepository<Catalogo, Long> {

	List<Catalogo> findByCatEliminadoAndCatEstadoEquals(boolean catEliminado, Integer catEstado);

	Optional<Catalogo> findByCatCodigoAndAgrupacion_TipoCatalogo_tipocatIdAndCatEliminadoAndCatEstadoEquals(String id,
			Long tipCatId, boolean catEliminado, Integer catEstado);

//	List<Catalogo> findByCatCodigoInAndCatEliminadoAndCatEstado(List<String> catCodigo, boolean tipocatEliminado,
//			Integer tipcatEstado);

	List<Catalogo> findByCatIdInAndCatEliminadoAndCatEstado(List<Long> catCodigo, boolean tipocatEliminado,
			Integer tipcatEstado);

}
