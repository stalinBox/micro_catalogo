package ec.gob.mag.central.catalogo.services;

import java.util.List;

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

	/**
	 * Servicio para buscar por el id del catálogo
	 * 
	 * @param id: Identificador del cobertura
	 * @return items: Retorna la cobertura en base al párametro de entrada
	 */
	public List<Item> findByCatalogoId(Long id) {
		List<Item> items = itemRepository.findByCatalogo_catIdAndCatalogo_catEliminadoFalseAndIteEliminadoFalse(id);
		if (items.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					Catalogo.class.getName()));
		return items;
	}

}
