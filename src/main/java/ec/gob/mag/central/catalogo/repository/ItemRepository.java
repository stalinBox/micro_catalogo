package ec.gob.mag.central.catalogo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Item;

@Repository("itemRepository")
public interface ItemRepository extends CrudRepository<Item, Long> {

	List<Item> findByCatalogo_catIdAndCatalogo_catEliminadoAndCatalogo_catEstadoAndIteEliminadoAndIteEstado(Long catId,
			boolean catEliminado, Integer catEstado, boolean iteEliminado, Integer iteEstado);

}
