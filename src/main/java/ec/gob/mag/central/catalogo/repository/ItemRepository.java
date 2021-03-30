package ec.gob.mag.central.catalogo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Item;

@Repository("itemRepository")
public interface ItemRepository extends CrudRepository<Item, Long> {

	List<Item> findByIteEliminadoAndIteEstado(boolean iteEliminado, Integer iteEstado);

	List<Item> findByIteIdAndIteEliminadoAndIteEstado(Long catId, boolean iteEliminado, Integer iteEstado);

	@SuppressWarnings("unchecked")
	Item save(Item item);

	List<Item> findByCatalogo_catIdAndCatalogo_catEliminadoAndCatalogo_catEstado(Long catId, boolean tipocatEliminado,
			Integer tipcatEstado);

}
