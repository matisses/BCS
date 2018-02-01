package co.matisses.bcs.sync;

import co.matisses.bcs.mongodb.MaterialMongoDBFacade;
import co.matisses.bcs.mongodb.MongoDBClient;
import co.matisses.persistence.sap.entity.BaruMateriales;
import co.matisses.persistence.sap.facade.BaruMaterialesFacade;
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
@Path("itemdatamaterial")
public class ItemDataMaterialService implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(ItemDataMaterialService.class.getSimpleName());
    @EJB
    private BaruMaterialesFacade materialSAPFacade;

    @GET
    @Path("listmaterials/")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response listMaterials(@QueryParam("activeOnly") Boolean activeOnly) {
        CONSOLE.log(Level.INFO, "Listando materiales...");
        MaterialMongoDBFacade materialMongoFacade = new MaterialMongoDBFacade();
        long startTime = System.currentTimeMillis();
        //consulta materiales en mongo => mapa
        HashMap<String, Object> condiciones = new HashMap<>();
        if (activeOnly != null && activeOnly) {
            condiciones.put("active", activeOnly);
        }
        MongoClient mongo = MongoDBClient.crearConexion();
        HashMap<String, ItemDataMaterialDTO> materialsMongo = materialMongoFacade.consultar(mongo.getDatabase("matisses"), condiciones);
        CONSOLE.log(Level.INFO, "Finaliza proceso de consulta de materiales. {0}[ms]", (System.currentTimeMillis() - startTime));
        mongo.close();
        return Response.ok(materialsMongo).build();
    }

    @GET
    @Path("syncmaterials/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response syncMaterials() {
        CONSOLE.log(Level.INFO, "Inicia proceso de sincronizacion de materiales...");
        MaterialMongoDBFacade materialMongoFacade = new MaterialMongoDBFacade();
        long startTime = System.currentTimeMillis();
        //consulta materiales en mongo => mapa
        MongoClient mongo = MongoDBClient.crearConexion();
        HashMap<String, ItemDataMaterialDTO> materialsMongo = materialMongoFacade.consultar(mongo.getDatabase("matisses"), null);
        //consulta materiales en sap => mapa
        HashMap<String, ItemDataMaterialDTO> materialsSAP = new HashMap<>();
        for (BaruMateriales entity : materialSAPFacade.findAll()) {
            if (entity.getuNombreWeb() != null && !entity.getuNombreWeb().trim().isEmpty()) {
                materialsSAP.put(entity.getCode(), new ItemDataMaterialDTO(null, entity.getCode(), entity.getuNombreWeb(), entity.getuCuidados()));
            }
        }

        HashMap<String, Document> materialsToAdd = new HashMap<>();
        HashMap<String, Document> materialsToUpdate = new HashMap<>();
        //Clasifica los materiales a eliminar y modificar
        for (String key : materialsMongo.keySet()) {
            ItemDataMaterialDTO material = materialsMongo.get(key);
            if (!materialsSAP.containsKey(key)) {
                material.setActive(false);
            } else {
                material.setName(materialsSAP.get(key).getName());
                material.setCares(materialsSAP.get(key).getCares());
            }
            materialsToUpdate.put(materialsMongo.get(key).getId(), material.toDocument());
        }

        CONSOLE.log(Level.INFO, "Se encontraron {0} materiales para actualizar y/o inactivar", materialsToUpdate.keySet().size());
        materialMongoFacade.actualizar(mongo.getDatabase("matisses"), new ArrayList(materialsToUpdate.values()), "code");

        //Clasifica los materiales a agregar
        for (String key : materialsSAP.keySet()) {
            if (!materialsMongo.containsKey(key)) {
                materialsToAdd.put(key, materialsSAP.get(key).toDocument());
            }
        }
        CONSOLE.log(Level.INFO, "Se encontraron {0} materiales para agregar", materialsToAdd.keySet().size());
        materialMongoFacade.agregar(mongo.getDatabase("matisses"), new ArrayList(materialsToAdd.values()));
        mongo.close();
        CONSOLE.log(Level.INFO, "Finaliza proceso de sincronizacion de materiales. {0}[ms]", (System.currentTimeMillis() - startTime));
        return Response.ok("{\"status\":200,\"message\":\"ok\"}").build();
    }
}
