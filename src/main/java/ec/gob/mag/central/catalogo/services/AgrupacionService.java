package ec.gob.mag.central.catalogo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.central.catalogo.domain.Agrupacion;
import ec.gob.mag.central.catalogo.exception.CatalogoNotFoundException;
import ec.gob.mag.central.catalogo.repository.AgrupacionRepository;

/**
 * @author PITPPA
 * @version FINAL
 */
@Service("agrupacionService")
public class AgrupacionService {
	@Autowired
	@Qualifier("agrupacionRepository")
	private AgrupacionRepository agrupacionRepository;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Servicio para encontrar todos los registros de la tabla agrupación
	 * 
	 * @return Todos los registros de cobertura que estan almacenados en la base de
	 *         datos BDC
	 */
	public List<Agrupacion> findAll() {
		List<Agrupacion> catalogos = agrupacionRepository.findAll();
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					Agrupacion.class.getName()));
		return catalogos;
	}

	/**
	 * Servicio para actualizar los registros de agrupación
	 * 
	 * @param cobId: Identificador del catalogo a actualizar
	 * @return catalogo: retorna el regitro actualizado
	 */
	public Optional<Agrupacion> findByCobId(Long cobId) {
		Optional<Agrupacion> catalogo = agrupacionRepository.findById(cobId);
		if (!catalogo.isPresent())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_not_exist.message", null, LocaleContextHolder.getLocale()),
					cobId));
		return catalogo;
	}

	/**
	 * Servicio para buscar por identificador de agrupación
	 * 
	 * @param id: Identificador de la agrupación
	 * @return catalogos: Retorna la agrupacion recuperado de la base de datos BDC
	 */
	public List<Agrupacion> findByTipoCatalogoId(Long id) {
		List<Agrupacion> catalogos = agrupacionRepository.findAll();
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					Agrupacion.class.getName()));
		return catalogos;
	}

	/**
	 * Servicio para buscar por id de agrupación
	 * 
	 * @param id: Identificador de la tabla de agrupación
	 * @return lista
	 */
	public List<Long> findIdAgrupacionByTipoCatalogoId(Long id) {
		List<Agrupacion> catalogos = agrupacionRepository.findByTipoCatalogo_tipocatId(id);
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));

		List<Long> lista = new ArrayList<>();
		for (int contador = 0; contador < catalogos.size(); contador++) {
			lista.add(catalogos.get(contador).getCatIdHijo());
		}
		return lista;
	}

	/**
	 * Servicio para buscar todos los registros de la tabla agrupaciones por nivel
	 * 
	 * @param id: Identificador de agrupacion
	 * @return lista: Liste recuperada en base a los parámetros
	 */
	public List<Long> findFirstLevelByTipoCatalogoId(Long id) {
		List<Agrupacion> catalogos = agrupacionRepository.findByCatIdPadreAndTipoCatalogo_tipocatId(0L, id);
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		List<Long> lista = new ArrayList<>();
		for (int contador = 0; contador < catalogos.size(); contador++) {
			lista.add(catalogos.get(contador).getCatIdHijo());
		}
		return lista;
	}

	/**
	 * Servicio para buscar por segundo nivel en la tabla de agrupacion
	 * 
	 * @param id: Identificador padre para buscar
	 * @return lista: Todos los registros por segundo nivel de busqueda
	 */
	public List<Long> findSecondLevelByTipoCatalogoId(Long id) {
		List<Agrupacion> catalogos = agrupacionRepository.findByCatIdPadreNotAndTipoCatalogo_tipocatId(0L, id);// .findAll();
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));

		List<Long> lista = new ArrayList<>();
		for (int contador = 0; contador < catalogos.size(); contador++) {
			lista.add(catalogos.get(contador).getCatIdHijo());
		}
		return lista;
	}

	/**
	 * Servicio para buscar por Identificardor de catalogo padre
	 * 
	 * @param id: Identificador del catalogo padre
	 * @return lista: Listado de catalogos hijos
	 */
	public List<Long> findIdAgrupacionByCatalogoPadre(Long id) {
		List<Agrupacion> catalogos = agrupacionRepository.findByCatIdPadre(id);
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		List<Long> lista = new ArrayList<>();
		for (int contador = 0; contador < catalogos.size(); contador++) {
			lista.add(catalogos.get(contador).getCatIdHijo());
		}
		return lista;
	}

	/**
	 * Servicio para buscar por Identificardor de catalogo padre
	 * 
	 * @param id: Identificador del catalogo padre
	 * @return lista: Listado de catalogos hijos
	 */
	public List<Long> findcatIdHijo(Long id) {
		List<Agrupacion> catalogos = agrupacionRepository.findByCatIdHijo(id);
		if (catalogos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		List<Long> lista = new ArrayList<>();
		for (int contador = 0; contador < catalogos.size(); contador++) {
			lista.add(catalogos.get(contador).getCatIdHijo());
		}
		return lista;
	}

	/**
	 * Servicio para guardar una agrupación
	 * 
	 * @param catalogo
	 * @return La agrupación creada
	 */
	public Agrupacion save(Agrupacion catalogo) {
		return agrupacionRepository.save(catalogo);
	}

	/**
	 * Servicio para eliminar un catalogo
	 * 
	 * @param CobId
	 */
	public void deleteById(Long cobId) {
		agrupacionRepository.deleteById(cobId);
	}
}
