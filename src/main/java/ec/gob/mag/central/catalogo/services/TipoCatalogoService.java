package ec.gob.mag.central.catalogo.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ec.gob.mag.central.catalogo.domain.TipoCatalogo;
import ec.gob.mag.central.catalogo.exception.CatalogoNotFoundException;
import ec.gob.mag.central.catalogo.repository.TipoCatalogoRepository;

/**
 * @author PITPPA
 * @version FINAL
 */
@Service("tipoCatalogoService")
public class TipoCatalogoService {
	@Autowired
	@Qualifier("tipoCatalogoRepository")
	private TipoCatalogoRepository tipoCatalogoRepository;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Servicio para encontrar todos los tipos de catalogos
	 * 
	 * @return Todos los tipos de catalogos
	 */
	public List<TipoCatalogo> findAll() {
		List<TipoCatalogo> tipos = tipoCatalogoRepository.findAll();
		if (tipos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));

		return tipos;
	}
	
	


	/**
	 * Servicio para encontrar todos los tipos de catalogos, excepto los que continenen un nombre
	 * 
	 * @return Todos los tipos de catalogos  excepto los que continenen un nombre
	 */
	public List<TipoCatalogo> findTiposCatalogos(String nombre) {
		List<TipoCatalogo> tipos = tipoCatalogoRepository.findTiposCatalogos(nombre);
		if (tipos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));

		return tipos;
	}
	
	/**
	 * Servicio para buscar catalogos por id
	 * 
	 * @param id: Identificador de la cobertura
	 * @return catalogo: Retorna todos los catalogos filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<TipoCatalogo> findById(Long tipCatId) {
		Optional<TipoCatalogo> tipo = tipoCatalogoRepository.findById(tipCatId);
		if (!tipo.isPresent())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					tipCatId));
		return tipo;
	}

	

}
