package ec.gob.mag.central.catalogo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.central.catalogo.domain.Imagen;
import ec.gob.mag.central.catalogo.services.ImagenService;
import ec.gob.mag.central.catalogo.util.ResponseController;
import ec.gob.mag.central.catalogo.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/imagen")
@Api(value = "Rest Api example", tags = "IMAGEN")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class ImagenController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(ImagenController.class);

	@Autowired
	@Qualifier("imagenService")
	private ImagenService imagenService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	@Autowired
	@Qualifier("util")
	private Util util;

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros.
	 * @RequestHeader(name = "Authorization") String token
	 */
	@GetMapping(value = "/findAll")
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = Imagen.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String token) {
		List<Imagen> officer = imagenService.findAll();

		LOGGER.info("template/findAll: " + officer.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(officer);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@GetMapping(value = "/findById/{id}")
	@ApiOperation(value = "Get Imagen by id", response = Imagen.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Optional<?>> findById(@Validated @PathVariable Long id,
			@RequestHeader(name = "Authorization") String token) {
		Optional<Imagen> officer = imagenService.findById(id);
		LOGGER.info("findById: " + officer.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(officer);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@GetMapping(value = "/findCatId/{id}")
	@ApiOperation(value = "Get Imagen by id", response = Imagen.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<?>> findByCatId(@Validated @PathVariable Integer id,
			@RequestHeader(name = "Authorization") String token) {
		List<Imagen> officer = imagenService.findByCatId(id);
		LOGGER.info("findById: " + officer.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(officer);
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@PostMapping(value = "/create/")
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> postEntity(@Validated @RequestBody Imagen template,
			@RequestHeader(name = "Authorization") String token) {
		Imagen off = imagenService.save(template);
		LOGGER.info("Creado: " + template + " usuario: " + template.getImgRegUsu());
		return ResponseEntity.ok(new ResponseController(off.getImgId(), "Creado"));
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param usuId:   Identificador del usuario que va a actualizar
	 * 
	 * @param entidad: entidad a actualizar
	 * @return ResponseController: Retorna el id actualizado
	 */
	@PostMapping(value = "/update/{usuId}")
	@ApiOperation(value = "Actualizar los registros", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> update(@Validated @RequestBody Imagen update, @PathVariable Integer usuId,
			@RequestHeader(name = "Authorization") String token) {
		update.setImgActUsu(usuId);
		Imagen off = imagenService.update(update);
		LOGGER.info("Actualizado: " + off + " usuario: " + usuId);
		return ResponseEntity.ok(new ResponseController(off.getImgId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@GetMapping(value = "/delete/{id}/{usuId}")
	@ApiOperation(value = "Remove template by id")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> deleteOfficer(@PathVariable Long id, @PathVariable Integer usuId,
			@RequestHeader(name = "Authorization") String token) {
		Imagen deleteTemplate = imagenService.findById(id)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Imagen", "Id", id.toString()));
		deleteTemplate.setImgEliminado(true);
		deleteTemplate.setImgActUsu(usuId);
		Imagen officerDel = imagenService.save(deleteTemplate);
		LOGGER.info("Eliminado: " + id + " usuario: " + usuId);
		return ResponseEntity.ok(new ResponseController(officerDel.getImgId(), "eliminado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
