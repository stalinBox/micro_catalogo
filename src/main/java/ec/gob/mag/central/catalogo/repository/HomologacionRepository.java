package ec.gob.mag.central.catalogo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import ec.gob.mag.central.catalogo.domain.Homologacion;

@Repository("homologacionRepository")
public interface HomologacionRepository extends CrudRepository<Homologacion, Long> {

	Optional<Homologacion> findByCatId(Long catId);
}
