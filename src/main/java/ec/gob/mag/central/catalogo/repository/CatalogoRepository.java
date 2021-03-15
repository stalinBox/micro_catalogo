package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Catalogo;

@Repository("catalogoRepository")
public interface CatalogoRepository extends CrudRepository<Catalogo, Long> {

	List<Catalogo> findByCatEliminadoAndCatEstado(boolean tipocatEliminado, Integer tipcatEstado);

	List<Catalogo> findByCatIdInAndCatEliminadoAndCatEstado(List<Long> catId, boolean tipocatEliminado,
			Integer tipcatEstado);

	Optional<Catalogo> findByCatIdAndCatEliminadoAndCatEstado(Integer catId, boolean tipocatEliminado,
			Integer tipcatEstado);

	Optional<Catalogo> findByCatCodigoAndCatEliminadoAndCatEstado(String catCodigo, boolean tipocatEliminado,
			Integer tipcatEstado);

	Optional<Catalogo> findByIdAnteriorAndCatEliminadoAndCatEstado(Long idAnterior, boolean tipocatEliminado,
			Integer tipcatEstado);

	@SuppressWarnings("unchecked")
	Catalogo save(Catalogo catalogo);
}
