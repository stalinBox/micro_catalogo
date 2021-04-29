package ec.gob.mag.central.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.central.catalogo.domain.Catalogo;
import ec.gob.mag.central.catalogo.domain.Item;
import ec.gob.mag.central.catalogo.domain.TipoCatalogo;
import ec.gob.mag.central.catalogo.services.AgrupacionService;
import ec.gob.mag.central.catalogo.services.CatalogoService;
import ec.gob.mag.central.catalogo.services.ItemService;
import ec.gob.mag.central.catalogo.services.TipoCatalogoService;
import ec.gob.mag.central.catalogo.util.ResponseController;
import ec.gob.mag.central.catalogo.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api")
@Api(value = "Rest Api example", tags = "CATALOGO")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class CatalogosController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(CatalogosController.class);

	@Autowired
	@Qualifier("catalogoService")
	private CatalogoService catalogoService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	@Autowired
	@Qualifier("agrupacionService")
	private AgrupacionService agrupacionservice;

	@Autowired
	@Qualifier("tipoCatalogoService")
	private TipoCatalogoService tipoCatalogoService;

	@Autowired
	@Qualifier("itemService")
	private ItemService itemService;

	@Autowired
	@Qualifier("util")
	private Util util;

	/**
	 * Controller para buscar tipo de catalogo
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos: Retorna todos los catalogos por agrupación
	 */
	@RequestMapping(value = "/catalogo/findByIdTipoCatalogo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Agrupacion by id", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> getCatalogo(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
		List<Long> agrupacion = agrupacionservice.findIdAgrupacionByTipoCatalogoId(id);
		List<Catalogo> catalogos = catalogoService.findByTipoCatalogoId(agrupacion);
		LOGGER.info(
				"/catalogo/findByIdTipoCatalogo/{id}" + catalogos.toString() + " usuario: " + util.filterUsuId(token));
		return catalogos;
	}

	/**
	 * Controller para buscar por el CAT ID PADRE de la tabla agrupacion
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos
	 */
	@RequestMapping(value = "/catalogo/findFirtsLevelByIdTipoCatalogo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Agrupacion by id", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> firstLevel(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
		List<Long> agrupacion = agrupacionservice.findFirstLevelByTipoCatalogoId(id);
		List<Catalogo> catalogos = catalogoService.findByTipoCatalogoId(agrupacion);
		LOGGER.info("/catalogo/findFirtsLevelByIdTipoCatalogo/{id}" + catalogos.toString() + " usuario: "
				+ util.filterUsuId(token));
		return catalogos;
	}

	/**
	 * Controller para buscar por el CAT ID HIJO de la tabla agrupacion
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos
	 */
	@RequestMapping(value = "/catalogo/findSecondLevelByIdTipoCatalogo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Agrupacion by id", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> secondLevel(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
		List<Long> agrupacion = agrupacionservice.findSecondLevelByTipoCatalogoId(id);
		List<Catalogo> catalogos = catalogoService.findByTipoCatalogoId(agrupacion);
		LOGGER.info("/catalogo/findSecondLevelByIdTipoCatalogo/{id}" + catalogos.toString() + " usuario: "
				+ util.filterUsuId(token));
		return catalogos;
	}

	/**
	 * Controller para buscar un catalogo por identificador
	 * 
	 * @param id: Identificador del catalogo
	 * @return items: retorna el catalogo
	 */
	@RequestMapping(value = "/item/findByCatalogo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Items by Catalogo", response = Item.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Item> getItem(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
		List<Item> items = itemService.findByCatalogoId(id);
		LOGGER.info("/item/findByCatalogo/{id}" + items.toString() + " usuario: " + util.filterUsuId(token));
		return items;
	}

	/**
	 * Controller para buscar un catalogo padre
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos: los catalogos padres
	 */
	@RequestMapping(value = "/catalogo/findByCatalogoPadre/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Catalago Hijo by Catalogo Padre", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> getCatalogoHijo(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
		List<Long> agrupacion = agrupacionservice.findIdAgrupacionByCatalogoPadre(id);
		List<Catalogo> catalogos = catalogoService.findByTipoCatalogoId(agrupacion);
		LOGGER.info(
				"/catalogo/findByCatalogoPadre/{id}" + catalogos.toString() + " usuario: " + util.filterUsuId(token));
		return catalogos;
	}

	/**
	 * Controller para buscar un catalogo hijo por un catalogo padre
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos: Retorna los catalogos hijosF
	 */
//	@RequestMapping(value = "/catalogo/findByIdCatalogo/{id}", method = RequestMethod.GET)
//	@ApiOperation(value = "Obtiene Catalago Hijo by Catalogo Padre", response = Catalogo.class)
//	@ResponseStatus(HttpStatus.OK)
//	public Catalogo finByIdCatalago(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
//		Catalogo catalogo = catalogoService.findByCatId(id).get();
//		LOGGER.info("/catalogo/findByIdCatalogo/{id}" + catalogo.toString() + " usuario: " + util.filterUsuId(token));
//		return catalogo;
//	}

	/**
	 * Controller para buscar todos los tipos Catalogos (Excepto RENAGRO)
	 * 
	 * @param
	 * @return List<tipo_catalogo>: Retorna todos los tipos de catalogos
	 */
	@RequestMapping(value = "/tipoCatalogo/findAll", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene todos los tipo Catalogos")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoCatalogo> findAllTipoCatalogo(@RequestHeader(name = "Authorization") String token) {
		List<TipoCatalogo> tipos = tipoCatalogoService.findTiposCatalogos("RENAGRO");
		LOGGER.info("/tipoCatalogo/findAll" + tipos.toString() + " usuario: " + util.filterUsuId(token));
		return tipos;
	}

	/**
	 * Controller para buscar un catalogo por id
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogo
	 */
	@RequestMapping(value = "/catalogo/findByCatCodigo/{catCodigo}/{tipCatCodigo}", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene un catalogo por cat Codigo", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public Catalogo finByCatCodigoCatalago(@PathVariable String catCodigo, @PathVariable Long tipCatCodigo,
			@RequestHeader(name = "Authorization") String token) {
		Catalogo catalogo = catalogoService.findByCatCodigoAndTipCatId(catCodigo, tipCatCodigo).get();
		LOGGER.info("/catalogo/findByIdCatalogo/{id}" + catalogo.toString() + " usuario: " + util.filterUsuId(token));
		return catalogo;
	}

	/**
	 * Controller para buscar un catalogo por id anterior
	 * 
	 * @param id: Identificador del catalogo anterior
	 * @return catalogo
	 */
//	@RequestMapping(value = "/catalogo/findByIdAnterior/{idanterior}", method = RequestMethod.GET)
//	@ApiOperation(value = "Obtiene un catalogo por id anterior", response = Catalogo.class)
//	@ResponseStatus(HttpStatus.OK)
//	public Catalogo findByIdAnterior(@PathVariable Long idanterior,
//			@RequestHeader(name = "Authorization") String token) {
//		Catalogo catalogo = catalogoService.findByIdAnterior(idanterior).get();
//		LOGGER.info("/catalogo/findByIdAnterior/{idanterior}" + catalogo.toString() + " usuario: "
//				+ util.filterUsuId(token));
//		return catalogo;
//	}

	// ---------------------------------- NUEVOS METODOS

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@PostMapping(value = "/catalogo/create/")
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> postEntity(@Validated @RequestBody Catalogo Catalogo,
			@RequestHeader(name = "Authorization") String token) {
		Catalogo off = catalogoService.save(Catalogo);
		LOGGER.info("Creado: " + Catalogo + " usuario: " + Catalogo.getCatRegUsu());
		return ResponseEntity.ok(new ResponseController(off.getCatId(), "Creado"));
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param usuId:   Identificador del catalogo que va a actualizar
	 * 
	 * @param entidad: entidad a actualizar
	 * @return ResponseController: Retorna el id actualizado
	 */
	/*@PostMapping(value = "/catalogo/update/{origenId}")
	@ApiOperation(value = "Actualizar registro de catalogos", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> updateCatalogo(@Validated @RequestBody Catalogo update, @PathVariable Long origenId,
			@RequestHeader(name = "Authorization") String token) {
		update.setCatActUsu(Integer.parseInt(util.filterUsuId(token)));
		Catalogo off = catalogoService.update(update, origenId);
		LOGGER.info("catalogo/update/" + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getCatId(), "Actualizado"));
		
		
	}*/

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	/*@GetMapping(value = "/catalogo/delete/{idCatalogo}/{origenId}")
	@ApiOperation(value = "Remove Catalogo by id Catalogo y Origen Id")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> deleteCatalogo(@PathVariable String idCatalogo, @PathVariable Long origenId,
			@PathVariable Integer usuId, @RequestHeader(name = "Authorization") String token) {
		Catalogo deleteCatalogo = catalogoService.findCatalogo(idCatalogo, origenId)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Catalogo", "Id", idCatalogo));
		deleteCatalogo.setCatEliminado(true);
		deleteCatalogo.setCatActUsu(usuId);
		Catalogo catalogoDel = catalogoService.save(deleteCatalogo);
		LOGGER.info("/catalogo/delete/" +idCatalogo +" usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(catalogoDel.getCatId(), "eliminado"));
	}*/
	
	
	//IMPLEMENTACIONES DE HOMOLOGACION
	/**
	 * Controller para buscar un catalogo por id
	 * 
	 * @param id: Identificador del catalogo (homologado en caso de )
	 * @return catalogo
	 */
	@RequestMapping(value = "/catalogo/findByIdRna/{idCatalogo}/{origenId}", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene un catalogo por Id Catalogo y Origen", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public Catalogo findByIdRna(@PathVariable String idCatalogo, @PathVariable Long origenId,
			@RequestHeader(name = "Authorization") String token) {
		Catalogo catalogo = catalogoService.findByIdRna(idCatalogo, origenId).get();
		LOGGER.info("/catalogo/findByIdRna/" + " usuario: " + util.filterUsuId(token));
		return catalogo;
	}	
	
	@RequestMapping(value = "/catalogo/findById/{idCatalogo}", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene un catalogo por Id Codigo", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public Catalogo findCatalogoById(@PathVariable String idCatalogo, @RequestHeader(name = "Authorization") String token) {
		Catalogo catalogo = catalogoService.findById(idCatalogo).get();
		LOGGER.info("/catalogo/findById/" + " usuario: " + util.filterUsuId(token));
		return catalogo;
	}
	
	@RequestMapping(value = "/catalogo/findCatalogosByTipo/{tipoCatalogo}", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene los catalogos por tipo", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> findCatalogosByTipo(@PathVariable Long tipoCatalogo, @RequestHeader(name = "Authorization") String token) {
		List<Catalogo> catalogos = catalogoService.findCatalogosByTipo(tipoCatalogo);
		LOGGER.info(
				"catalogo/findCatalogosByTipo" + catalogos.toString() + " usuario: " + util.filterUsuId(token));
		return catalogos;
	}
	
	
	@RequestMapping(value = "/catalogo/findCatalogosRnaByTipo/{tipoCatalogo}", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene los catalogos RNA por tipo", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> findCatalogosRnaByTipo(@PathVariable Long tipoCatalogo, @RequestHeader(name = "Authorization") String token) {
		List<Catalogo> catalogos = catalogoService.findCatalogosRnaByTipo(tipoCatalogo);
		LOGGER.info(
				"catalogo/findCatalogosRnaByTipo" + catalogos.toString() + " usuario: " + util.filterUsuId(token));
		return catalogos;
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}

}
