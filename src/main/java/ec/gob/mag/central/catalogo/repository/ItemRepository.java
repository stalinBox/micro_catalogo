package ec.gob.mag.central.catalogo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.central.catalogo.domain.Item;

@Repository("itemRepository")
public interface ItemRepository extends CrudRepository<Item, Long> {

	List<Item> findAll();

	List<Item> findByIteId(Long catId);

	@SuppressWarnings("unchecked")
	Item save(Item item);

	List<Item> findByCatalogo_catIdAndCatalogo_catEliminadoFalseAndIteEliminadoFalse(Long catId);

	void deleteByIteId(Integer itemId);

}
