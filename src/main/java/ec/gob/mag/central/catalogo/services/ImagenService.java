package ec.gob.mag.central.catalogo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.central.catalogo.domain.Imagen;
import ec.gob.mag.central.catalogo.enums.Constante;
import ec.gob.mag.central.catalogo.exception.MyNotFoundException;
import ec.gob.mag.central.catalogo.repository.ImagenRepository;

@Service("imagenService")
public class ImagenService {

	@Autowired
	@Qualifier("imagenRepository")
	private ImagenRepository imagenRepository;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<Imagen> findAll() {
		List<Imagen> Imagen = imagenRepository.findByImgEliminadoAndImgEstadoEquals(false,
				Constante.ESTADO_ACTIVO.getCodigo());
		if (Imagen.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		return Imagen;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Imagen> findById(Long id) {
		Optional<Imagen> Imagen = imagenRepository.findByImgIdAndImgEliminadoAndImgEstadoEquals(id, false,
				Constante.ESTADO_ACTIVO.getCodigo());
		if (!Imagen.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return Imagen;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Imagen update(Imagen Imagen) {
		Optional<Imagen> off = findById(Imagen.getImgId());
		if (!off.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					off.get().getImgId()));
		return imagenRepository.save(Imagen);
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Imagen save(Imagen officer) {
		return imagenRepository.save(officer);
	}
}
