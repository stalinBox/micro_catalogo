package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Catalogo;

@Repository("catalogoRepository")
public interface CatalogoRepository extends CrudRepository<Catalogo, Long> {

	List<Catalogo> findAll();

	List<Catalogo> findByCatIdIn(List<Long> catId);

	Optional<Catalogo> findByCatId(Integer catId);

	@SuppressWarnings("unchecked")
	Catalogo save(Catalogo catalogo);

	void deleteBycatId(Integer catId);

}
