package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.TipoCatalogo;

@Repository("tipoCatalogoRepository")
public interface TipoCatalogoRepository extends CrudRepository<TipoCatalogo, Long> {

	List<TipoCatalogo> findAll();

	Optional<TipoCatalogo> findBytipocatId(Integer tipocatId);

	@SuppressWarnings("unchecked")
	TipoCatalogo save(TipoCatalogo tipocatId);

	void deleteBytipocatId(Integer oriId);

}
