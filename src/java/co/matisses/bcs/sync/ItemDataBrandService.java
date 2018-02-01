package co.matisses.bcs.sync;

import co.matisses.bcs.mongodb.BrandMongoDBFacade;
import co.matisses.bcs.mongodb.MongoDBClient;
import co.matisses.persistence.sap.entity.BaruMarca;
import co.matisses.persistence.sap.facade.BaruMarcaFacade;
import com.mongodb.MongoClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.Document;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("itemdatabrand")
public class ItemDataBrandService implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(ItemDataBrandService.class.getSimpleName());

    @EJB
    private BaruMarcaFacade marcaFacade;

    @GET
    @Path("listbrands/")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response listBrands(@QueryParam("activeOnly") Boolean activeOnly) {
        long startTime = System.currentTimeMillis();
        CONSOLE.log(Level.INFO, "Listando marcas...");
        BrandMongoDBFacade brandMongoFacade = new BrandMongoDBFacade();
        //consulta materiales en mongo => mapa
        HashMap<String, Object> condiciones = new HashMap<>();
        if (activeOnly != null && activeOnly) {
            condiciones.put("active", activeOnly);
        }
        MongoClient mongo = MongoDBClient.crearConexion();
        HashMap<String, ItemDataBrandDTO> brandsMongo = brandMongoFacade.consultar(mongo.getDatabase("matisses"), condiciones);
        CONSOLE.log(Level.INFO, "Finaliza proceso de consulta de marcas. {0}[ms]", (System.currentTimeMillis() - startTime));
        mongo.close();
        return Response.ok(brandsMongo).build();
    }

    @GET
    @Path("syncbrands/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response syncBrands() {
        CONSOLE.log(Level.INFO, "Inicia proceso de sincronizacion de marcas...");
        BrandMongoDBFacade brandMongoFacade = new BrandMongoDBFacade();
        long startTime = System.currentTimeMillis();
        //consulta marcas en mongo => mapa
        MongoClient mongo = MongoDBClient.crearConexion();
        HashMap<String, ItemDataBrandDTO> brandsMongo = brandMongoFacade.consultar(mongo.getDatabase("matisses"), null);
        //consulta marcas en sap => mapa
        HashMap<String, ItemDataBrandDTO> marcasSAP = new HashMap<>();
        for (BaruMarca entity : marcaFacade.findAll()) {
            marcasSAP.put(entity.getCode(), new ItemDataBrandDTO(null, entity.getCode(), entity.getName()));
        }

        HashMap<String, Document> brandsToAdd = new HashMap<>();
        HashMap<String, Document> brandsToUpdate = new HashMap<>();
        //Clasifica las marcas a eliminar y modificar
        for (String key : brandsMongo.keySet()) {
            ItemDataBrandDTO marca = brandsMongo.get(key);
            if (!marcasSAP.containsKey(key)) {
                marca.setActive(false);
            } else {
                marca.setName(marcasSAP.get(key).getName());
            }
            brandsToUpdate.put(brandsMongo.get(key).getId(), marca.toDocument());
        }

        CONSOLE.log(Level.INFO, "Se encontraron {0} marcas para actualizar y/o inactivar", brandsToUpdate.keySet().size());
        brandMongoFacade.actualizar(mongo.getDatabase("matisses"), new ArrayList(brandsToUpdate.values()), "code");

        //Clasifica las marcas a agregar
        for (String key : marcasSAP.keySet()) {
            if (!brandsMongo.containsKey(key)) {
                brandsToAdd.put(key, marcasSAP.get(key).toDocument());
            }
        }
        CONSOLE.log(Level.INFO, "Se encontraron {0} marcas para agregar", brandsToAdd.keySet().size());
        brandMongoFacade.agregar(mongo.getDatabase("matisses"), new ArrayList(brandsToAdd.values()));

        CONSOLE.log(Level.INFO, "Finaliza proceso de sincronizacion de marcas. {0}[ms]", (System.currentTimeMillis() - startTime));
        mongo.close();
        return Response.ok("{\"status\":200,\"message\":\"ok\"}").build();
    }
}
