package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.TipoColumna;

@Repository("tipoColumnaRepository")
public interface TipoColumnaRepository extends CrudRepository<TipoColumna, Long> {

	List<TipoColumna> findAll();

	Optional<TipoColumna> findBytipoColumnaId(Integer tipoColumnaId);

	@SuppressWarnings("unchecked")
	TipoColumna save(TipoColumna tipo_Columna);

	void deleteBytipoColumnaId(Integer tipoColumnaId);

}
