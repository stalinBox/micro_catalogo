package ec.gob.mag.central.catalogo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.central.catalogo.domain.Catalogo;
import ec.gob.mag.central.catalogo.domain.Item;
import ec.gob.mag.central.catalogo.enums.Constante;
import ec.gob.mag.central.catalogo.exception.MyNotFoundException;
import ec.gob.mag.central.catalogo.repository.ItemRepository;

@Service("itemService")
public class ItemService {

	@Autowired
	@Qualifier("itemRepository")
	private ItemRepository itemRepository;

	@Autowired
	private MessageSource messageSource;

//	public void clearObjectLazyVariables(Catalogo usuario) {
//		usuario.getAgrupacion().stream().map(u -> {
//			u.getTipoCatalogo().setAgrupacion(null);
//			return u;
//		}).collect(Collectors.toList());
//	}
//
//	public List<Catalogo> clearListLazyVariables(List<Catalogo> usuarios) {
//		if (usuarios != null)
//			usuarios = usuarios.stream().map(u -> {
//				clearObjectLazyVariables(u);
//				return u;
//			}).collect(Collectors.toList());
//		return usuarios;
//	}
	/**
	 * Servicio para buscar por el id del catálogo
	 * 
	 * @param id: Identificador del cobertura
	 * @return items: Retorna la cobertura en base al párametro de entrada
	 */
	public List<Item> findByCatalogoId(Long id) {
		List<Item> items = itemRepository
				.findByCatalogo_catIdAndCatalogo_catEliminadoAndCatalogo_catEstadoAndIteEliminadoAndIteEstado(id, false,
						Constante.ESTADO_ACTIVO.getCodigo(), false, Constante.ESTADO_ACTIVO.getCodigo());
		if (items.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					Catalogo.class.getName()));
		return items;
	}

}
