package ec.gob.mag.central.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Imagen;

@Repository("imagenRepository")
public interface ImagenRepository extends CrudRepository<Imagen, Long> {

	// FIND ALL
	List<Imagen> findByImgEliminadoAndImgEstadoEquals(boolean tmpEliminado, Integer tmpEstado);

	// FIN BY ID
	Optional<Imagen> findByImgIdAndImgEliminadoAndImgEstadoEquals(Long id, boolean tmpEliminado, Integer tmpEstado);
}
