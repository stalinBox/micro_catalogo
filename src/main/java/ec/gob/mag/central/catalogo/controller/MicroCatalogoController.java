package ec.gob.mag.central.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.central.catalogo.domain.Catalogo;
import ec.gob.mag.central.catalogo.domain.Item;
import ec.gob.mag.central.catalogo.services.AgrupacionService;
import ec.gob.mag.central.catalogo.services.CatalogoService;
import ec.gob.mag.central.catalogo.services.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

/**
 * @author PITPPA
 * @version FINAL
 */
@RestController
@RequestMapping("/api")
@Api(value = "Agrupacion", description = "Rest Api example", tags = "CATALOGO_RNA")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 201, message = "Objeto creado"),
		@ApiResponse(code = 404, message = "Recurso no encontrado") })
public class MicroCatalogoController implements ErrorController {
	private static final String PATH = "/error";

	public static final Logger LOGGER = LoggerFactory.getLogger(MicroCatalogoController.class);
	@Autowired
	@Qualifier("catalogoservice")
	private CatalogoService catalogoservice;

	@Autowired
	@Qualifier("agrupacionService")
	private AgrupacionService agrupacionservice;

	@Autowired
	@Qualifier("itemservice")
	private ItemService itemservice;

	/**
	 * Controller para buscar tipo de catalogo
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos: Retorna todos los catalogos por agrupación
	 */
	@RequestMapping(value = "/catalogo/findByIdTipoCatalogo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Agrupacion by id", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> getCatalogo(@PathVariable Long id) {
		List<Long> agrupacion = agrupacionservice.findIdAgrupacionByTipoCatalogoId(id);
		List<Catalogo> catalogos = catalogoservice.findByTipoCatalogoId(agrupacion);
		return catalogos;
	}

	/**
	 * Controller para buscar por el primer nivel en base al tipo de catalogo
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos
	 */
	@RequestMapping(value = "/catalogo/findFirtsLevelByIdTipoCatalogo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Agrupacion by id", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> firstLevel(@PathVariable Long id) {
		List<Long> agrupacion = agrupacionservice.findFirstLevelByTipoCatalogoId(id);
		List<Catalogo> catalogos = catalogoservice.findByTipoCatalogoId(agrupacion);
		return catalogos;
	}

	/**
	 * Controller para buscar por segundo nivel en base al tipo de catalogo
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos
	 */
	@RequestMapping(value = "/catalogo/findSecondLevelByIdTipoCatalogo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Agrupacion by id", response = Catalogo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> secondLevel(@PathVariable Long id) {
		List<Long> agrupacion = agrupacionservice.findSecondLevelByTipoCatalogoId(id);
		List<Catalogo> catalogos = catalogoservice.findByTipoCatalogoId(agrupacion);
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
	public List<Item> getItem(@PathVariable Long id) {
		List<Item> items = itemservice.findByCatalogoId(id);
		return items;
	}

	/**
	 * Controller para buscar un catalogo padre
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos: los catalogos padres
	 */
	@RequestMapping(value = "/catalogo/findByCatalogoPadre/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Catalago Hijo by Catalogo Padre", response = Item.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Catalogo> getCatalogoHijo(@PathVariable Long id) {
		List<Long> agrupacion = agrupacionservice.findIdAgrupacionByCatalogoPadre(id);
		List<Catalogo> catalogos = catalogoservice.findByTipoCatalogoId(agrupacion);
		return catalogos;
	}

	/**
	 * Controller para buscar un catalogo hijo por un catalogo padre
	 * 
	 * @param id: Identificador del catalogo
	 * @return catalogos: Retorna los catalogos hijosF
	 */
	@RequestMapping(value = "/catalogo/findByIdCatalogo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene Catalago Hijo by Catalogo Padre", response = Item.class)
	@ResponseStatus(HttpStatus.OK)
	public Catalogo finByIdCatalago(@PathVariable Long id) {
		Catalogo catalogo = catalogoservice.findByCatId(id).get();
		return catalogo;
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}

}
