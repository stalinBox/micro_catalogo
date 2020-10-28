package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Agrupacion;

@Repository("agrupacionRepository")
public interface AgrupacionRepository extends CrudRepository<Agrupacion, Long> {

	List<Agrupacion> findAll();

	List<Agrupacion> findByAgrId(Long agrId);

	List<Agrupacion> findByTipoCatalogo_tipocatId(Long tipocatId);

	List<Agrupacion> findByCatIdPadre(Long catPadre);

	List<Agrupacion> findByCatIdHijo(Long catHijo);

	@SuppressWarnings("unchecked")
	Agrupacion save(Agrupacion agrupacion);

	void deleteByagrId(Integer agrId);

	List<Agrupacion> findByCatIdPadreAndTipoCatalogo_tipocatId(Long catIdPadre, Long tipocatId);

	List<Agrupacion> findByCatIdPadreNotAndTipoCatalogo_tipocatId(Long catIdPadre, Long tipocatId);

}
