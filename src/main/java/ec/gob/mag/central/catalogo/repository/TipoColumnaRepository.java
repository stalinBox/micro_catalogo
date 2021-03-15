package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.TipoColumna;

@Repository("tipoColumnaRepository")
public interface TipoColumnaRepository extends CrudRepository<TipoColumna, Long> {

	List<TipoColumna> findByTipcolEliminadoAndTipcolEstado(boolean tipColEliminado, Integer tipColEstado);

	Optional<TipoColumna> findBytipoColumnaIdAndTipcolEliminadoAndTipcolEstado(Integer tipoColumnaId,
			boolean tipColEliminado, Integer tipColEstado);

	@SuppressWarnings("unchecked")
	TipoColumna save(TipoColumna tipo_Columna);
}
