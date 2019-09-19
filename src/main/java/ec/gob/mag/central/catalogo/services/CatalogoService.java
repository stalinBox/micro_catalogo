package ec.gob.mag.central.catalogo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.central.catalogo.domain.Catalogo;
import ec.gob.mag.central.catalogo.exception.CatalogoNotFoundException;
import ec.gob.mag.central.catalogo.repository.CatalogoRepository;

/**
 * @author PITPPA
 * @version FINAL
 */
@Service("catalogoservice")
public class CatalogoService {
	@Autowired
	@Qualifier("catalogoRepository")
	private CatalogoRepository catalogoRepository;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Servicio para encontrar todos los catalogos
	 * 
	 * @return Todos los registros de cobertura que estan almacenados en la base de
	 *         datos BDC
	 */
	public List<Catalogo> findAll() {
		List<Catalogo> catalogos = catalogoRepository.findAll();
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));

		return catalogos;
	}

	/**
	 * Servicio para buscar catalogos por id
	 * 
	 * @param id: Identificador de la cobertura
	 * @return catalogo: Retorna todos los catalogos filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Catalogo> findByCatId(Long catId) {
		Optional<Catalogo> catalogo = catalogoRepository.findById(catId);
		if (!catalogo.isPresent())
			throw new CatalogoNotFoundException(String.format(messageSource.getMessage(
					"error.entity_not_exist_id_cat.message", null, LocaleContextHolder.getLocale()), catId));
		return catalogo;
	}

	/**
	 * Servicio para buscar por catalogos hijos
	 * 
	 * @param ids: catalogo padre
	 * @return catalogos hijos
	 */
	public List<Catalogo> findByTipoCatalogoId(List<Long> ids) {
		List<Catalogo> catalogos = catalogoRepository.findByCatIdIn(ids);
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					Catalogo.class.getName()));
		return catalogos;
	}

	/**
	 * Servicio para guardar catálgos
	 * 
	 * @param catalogo: Contiene todos campos de la entidad catalogos para guardar
	 * @return catalogo: El cobertura creado
	 */
	public Catalogo save(Catalogo catalogo) {
		return catalogoRepository.save(catalogo);
	}

	/**
	 * Servicio para eliminar un registro de catalogos
	 * 
	 * @param CobId: Identificador del catálogo
	 */
	public void deleteById(Long cobId) {
		catalogoRepository.deleteById(cobId);
	}

}
