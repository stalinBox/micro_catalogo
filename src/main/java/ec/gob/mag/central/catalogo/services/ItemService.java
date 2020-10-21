package ec.gob.mag.central.catalogo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.central.catalogo.domain.Catalogo;
import ec.gob.mag.central.catalogo.domain.Item;
import ec.gob.mag.central.catalogo.exception.CatalogoNotFoundException;
import ec.gob.mag.central.catalogo.repository.CatalogoRepository;
import ec.gob.mag.central.catalogo.repository.ItemRepository;

/**
 * @author PITPPA
 * @version FINAL
 */
@Service("itemservice")
public class ItemService {
	@Autowired
	@Qualifier("itemRepository")
	private ItemRepository itemRepository;
	@Autowired
	private MessageSource messageSource;

	@Autowired
	@Qualifier("catalogoRepository")
	private CatalogoRepository catalogoRepository;

	public List<Item> clearList(List<Item> its) {
		its.stream().map(i -> {
			clearObject(i);
			return i;
		}).collect(Collectors.toList());

		return its;
	}

	public void clearObject(Item item) {
		Catalogo c = catalogoRepository.findById(item.getCatalogo().getCatId()).get();
		item.setCatalogoTR(c);
		// return item;
	}

	/**
	 * Servicio para buscar por el id del catálogo
	 * 
	 * @param id: Identificador del cobertura
	 * @return items: Retorna la cobertura en base al párametro de entrada
	 */
	public List<Item> findByCatalogoId(Long id) {
		List<Item> items = itemRepository.findByCatalogo_catId(id);
		if (items.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					Catalogo.class.getName()));
//		clearList(items);
		return items;
	}

}
