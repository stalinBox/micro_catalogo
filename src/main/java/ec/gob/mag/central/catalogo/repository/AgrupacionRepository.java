package ec.gob.mag.central.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Agrupacion;

@Repository("agrupacionRepository")
public interface AgrupacionRepository extends CrudRepository<Agrupacion, Long> {

	List<Agrupacion> findAll();

	List<Agrupacion> findByAgrId(Long agrId);
	
	List<Agrupacion> findByTipoCatalogo_tipocatIdAndTipoCatalogo_tipocatEliminadoFalseAndAgrEliminadoFalse(Long tipocatId);

	@Query("SELECT a FROM Agrupacion a JOIN Catalogo c ON a.catIdPadre = c.catId WHERE a.catIdPadre = ?1 AND a.agrEliminado = false AND c.catEliminado = false")
	List<Agrupacion> findByCatalogoPadre(Long catPadre);

	List<Agrupacion> findByCatIdHijo(Long catHijo);

	@SuppressWarnings("unchecked")
	Agrupacion save(Agrupacion agrupacion);

	void deleteByagrId(Integer agrId);

	List<Agrupacion> findByCatIdPadreAndTipoCatalogo_tipocatIdAndTipoCatalogo_tipocatEliminadoFalseAndAgrEliminadoFalse(Long catIdPadre, Long tipocatId);

	List<Agrupacion> findByCatIdPadreNotAndTipoCatalogo_tipocatIdAndTipoCatalogo_tipocatEliminadoFalseAndAgrEliminadoFalse(Long catIdPadre, Long tipocatId);

}
