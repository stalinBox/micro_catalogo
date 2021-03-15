package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Origen;

@Repository("origenRepository")
public interface OrigenRepository extends CrudRepository<Origen, Long> {

	List<Origen> findByOriEliminadoAndOriEstado(boolean oriEliminado, Integer oriEstado);

	Optional<Origen> findByoriIdAndOriEliminadoAndOriEstado(Integer oriId, boolean oriEliminado, Integer oriEstado);

	@SuppressWarnings("unchecked")
	Origen save(Origen origen);

}
