package ec.gob.mag.central.catalogo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ec.gob.mag.central.catalogo.domain.TipoCatalogo;
import ec.gob.mag.central.catalogo.enums.Constante;
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

	public void clearObjectLazyVariables(TipoCatalogo tipoCatalogo) {
		tipoCatalogo.getAgrupacion().stream().map(pt -> {
			pt.setTipoCatalogo(null);
			return pt;
		}).collect(Collectors.toList());
	}

	public List<TipoCatalogo> clearListLazyVariables(List<TipoCatalogo> tipoCatalogo) {
		if (tipoCatalogo != null)
			tipoCatalogo = tipoCatalogo.stream().map(u -> {
				clearObjectLazyVariables(u);
				return u;
			}).collect(Collectors.toList());
		return tipoCatalogo;
	}

	/**
	 * Servicio para encontrar todos los tipos de catalogos
	 * 
	 * @return Todos los tipos de catalogos
	 */
	public List<TipoCatalogo> findAll() {
		List<TipoCatalogo> tipos = tipoCatalogoRepository.findByTipcatEliminadoAndTipcatEstado(false,
				Constante.ESTADO_ACTIVO.getCodigo());
		if (tipos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));

		return tipos;
	}

	/**
	 * Servicio para encontrar todos los tipos de catalogos, excepto los que
	 * continenen un nombre
	 * 
	 * @return Todos los tipos de catalogos excepto los que continenen un nombre
	 */
	@JsonIgnore
	public List<TipoCatalogo> findTiposCatalogos(String nombre) {
		List<TipoCatalogo> tipos = tipoCatalogoRepository.findTiposCatalogos(nombre);
		if (tipos.isEmpty())
			throw new CatalogoNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(tipos);
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
