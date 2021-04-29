package ec.gob.mag.central.catalogo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.central.catalogo.domain.Agrupacion;
import ec.gob.mag.central.catalogo.domain.Catalogo;
import ec.gob.mag.central.catalogo.domain.Homologacion;
import ec.gob.mag.central.catalogo.enums.Constante;
import ec.gob.mag.central.catalogo.exception.MyNotFoundException;
import ec.gob.mag.central.catalogo.repository.AgrupacionRepository;
import ec.gob.mag.central.catalogo.repository.CatalogoRepository;
import ec.gob.mag.central.catalogo.repository.HomologacionRepository;

@Service("catalogoService")
public class CatalogoService {

	@Autowired
	@Qualifier("catalogoRepository")
	private CatalogoRepository catalogoRepository;

	@Autowired
	@Qualifier("agrupacionRepository")
	private AgrupacionRepository agrupacionRepository;
	
	@Autowired
	@Qualifier("homologacionRepository")
	private HomologacionRepository homologacionRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Catalogo usuario) {
		usuario.getAgrupacion().stream().map(u -> {
			u.getTipoCatalogo().setAgrupacion(null);
			return u;
		}).collect(Collectors.toList());
	}

	public List<Catalogo> clearListLazyVariables(List<Catalogo> usuarios) {
		if (usuarios != null)
			usuarios = usuarios.stream().map(u -> {
				clearObjectLazyVariables(u);
				return u;
			}).collect(Collectors.toList());
		return usuarios;
	}
	// ====================================================== OLD

	/**
	 * Servicio para encontrar todos los catalogos
	 * 
	 * @return Todos los registros de cobertura que estan almacenados en la base de
	 *         datos BDC
	 */
//	public List<Catalogo> findAll() {
//		List<Catalogo> catalogos = catalogoRepository.findByCatEliminadoAndCatEstado(false,
//				Constante.ESTADO_ACTIVO.getCodigo());
//		if (catalogos.isEmpty())
//			throw new CatalogoNotFoundException(String.format(
//					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
//					this.getClass().getName()));
//		clearListLazyVariables(catalogos);
//		return catalogos;
//	}

	/**
	 * Servicio para buscar catalogos por id
	 * 
	 * @param id: Identificador de la cobertura
	 * @return catalogo: Retorna todos los catalogos filtrados por el parámetros de
	 *         entrada
	 */
//	public Optional<Catalogo> findByCatId(Long catId) {
//		Optional<Catalogo> catalogo = catalogoRepository.findById(catId);
//		if (!catalogo.isPresent())
//			throw new MyNotFoundException(String.format(
//					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
//					catId));
//		clearObjectLazyVariables(catalogo.get());
//		return catalogo;
//	}

	/**
	 * Servicio para buscar catalogos por car Codigo
	 * 
	 * @param id: Identificador de la cobertura
	 * @return catalogo: Retorna todos los catalogos filtrados por el parámetros de
	 *         entrada
	 */
//	public Optional<Catalogo> findByCatCodigo(String catCodigo) {
//		Optional<Catalogo> catalogo = catalogoRepository.findByCatCodigoAndCatEliminadoAndCatEstado(catCodigo, false,
//				Constante.ESTADO_ACTIVO.getCodigo());
//		if (!catalogo.isPresent())
//			throw new MyNotFoundException(String.format(
//					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
//					catCodigo));
//		clearObjectLazyVariables(catalogo.get());
//		return catalogo;
//	}

	/**
	 * Servicio para buscar catalogos por car Codigo
	 * 
	 * @param id: Identificador de la cobertura
	 * @return catalogo: Retorna todos los catalogos filtrados por el parámetros de
	 *         entrada
	 */
//	public Optional<Catalogo> findByIdAnterior(Long catCodigo) {
//		Optional<Catalogo> catalogo = catalogoRepository.findByIdAnteriorAndCatEliminadoAndCatEstado(catCodigo, false,
//				Constante.ESTADO_ACTIVO.getCodigo());
//		if (!catalogo.isPresent())
//			throw new MyNotFoundException(String.format(
//					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
//					catCodigo));
//		clearObjectLazyVariables(catalogo.get());
//		return catalogo;
//	}

	/**
	 * Servicio para buscar por catalogos hijos
	 * 
	 * @param ids: catalogo padre
	 * @return catalogos hijos
	 */
	// ******************************************* PENDIENTE
	public List<Catalogo> findByTipoCatalogoId(List<Long> ids) {
		List<Catalogo> catalogos = catalogoRepository.findByCatIdInAndCatEliminadoAndCatEstado(ids, false,
				Constante.ESTADO_ACTIVO.getCodigo());
		if (catalogos.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					Catalogo.class.getName()));

		catalogos.stream().forEach(m -> {
			List<Agrupacion> agr = agrupacionRepository.findByCatIdHijoAndAgrEliminadoAndAgrEstado(m.getCatId(), false,
					Constante.ESTADO_ACTIVO.getCodigo());
			m.setAgrupacion(agr);
		});

		clearListLazyVariables(catalogos);
		return catalogos;
	}

	// ========================================= END OLD
	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<Catalogo> findAll() {
		List<Catalogo> catalogo = catalogoRepository.findByCatEliminadoAndCatEstadoEquals(false,
				Constante.ESTADO_ACTIVO.getCodigo());
		if (catalogo.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(catalogo);
		return catalogo;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Catalogo> findByCatCodigoAndTipCatId(String id, Long tipCatId) {
		Optional<Catalogo> catalogo = null;
		        /*catalogoRepository
				.findByCatCodigoAndAgrupacion_TipoCatalogo_tipocatIdAndCatEliminadoAndCatEstadoEquals(id, tipCatId,
						false, Constante.ESTADO_ACTIVO.getCodigo());*/
		if (!catalogo.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(catalogo.get());
		return catalogo;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	/*public Catalogo update(Catalogo catalogo, Long origen) {
		Optional<Catalogo> off = this.findCatalogo(catalogo.getCatId().toString(), origen);
		if (!off.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					off.get().getCatId()));
		return catalogoRepository.save(catalogo);
	}*/
	
	
	
	/**
	 * Busca un registro por Id
	 * 
	 * @param catId: Identificador del registro
	 * @return entidad: Retorna el registro cuyo id es el ingresado
	 */
	public Optional<Catalogo> findById(String catId) {
		
		Optional<Catalogo> catalogo = catalogoRepository.findByCatIdAndCatEliminadoAndCatEstado(Long.parseLong(catId), false, Constante.ESTADO_ACTIVO.getCodigo());
		
		if (!catalogo.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					catId));
		clearObjectLazyVariables(catalogo.get());
		return catalogo;
	}
	
	
	/**
	 * Busca un registro por Id y origen
	 * 
	 * @param catId: Identificador del registro
	 * @param origen: Id de Origen
	 * @return entidad: Retorna el registro cuyo id y origen es el ingresado
	 */
	public Optional<Catalogo> findByIdRna(String catId, Long origen) {
		
		Optional<Catalogo> catalogo = null;
		if ((origen.equals( new Long(1))) || (origen.equals(new Long(7))))
					catalogo=catalogoRepository
				.findCatalogo(catId, origen, false, Constante.ESTADO_ACTIVO.getCodigo());
		
		if (!catalogo.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					catId));
		
		catalogo.get().setCatId(Long.parseLong(catId));
		clearObjectLazyVariables(catalogo.get());
		return catalogo;
	}
	
	
	/**
	 * Busca catalogos por tipo
	 * 
	 * @param tipCatalogo: Tipo Catalogo
	 * @return List<Catalogo>: Lista de catálogo
	 */
	public List<Catalogo> findCatalogosByTipo(Long tipCatalogo)
	{
		List<Catalogo> catalogos = catalogoRepository.findCatalogosByTipo(tipCatalogo);
		if (catalogos.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		
		catalogos.stream().forEach(c->c.setAgrupacion(null));
		return catalogos;
	}
	
	
	
	/**
	 * Busca catalogos RNA por tipo
	 * 
	 * @param tipCatalogo: Tipo Catalogo
	 * @return List<Catalogo>: Lista de catálogo
	 */
	public List<Catalogo> findCatalogosRnaByTipo(Long tipCatalogo)
	{
		List<Catalogo> catalogos = this.findCatalogosByTipo(tipCatalogo);
		
		catalogos.stream().forEach(c->{
			try {
			Optional <Homologacion> h = homologacionRepository.findByCatId(c.getCatId());
			c.setCatId(new Long(h.get().getHomIdHomologado()));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		});
		return catalogos;
	}
	
	

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Catalogo save(Catalogo officer) {
		return catalogoRepository.save(officer);
	}
}
