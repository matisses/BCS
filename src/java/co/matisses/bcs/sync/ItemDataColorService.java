package co.matisses.bcs.sync;

import co.matisses.bcs.mongodb.ColorMongoDBFacade;
import co.matisses.bcs.mongodb.MongoDBClient;
import co.matisses.persistence.sap.facade.BaruColorArticuloFacade;
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
@Path("itemdatacolor")
public class ItemDataColorService implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(ItemDataColorService.class.getSimpleName());

    @EJB
    private BaruColorArticuloFacade colorSAPFacade;

    @GET
    @Path("listcolors/")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response listColors(@QueryParam("activeOnly") Boolean activeOnly) {
        CONSOLE.log(Level.INFO, "Listando colores...");
        ColorMongoDBFacade colorMongoFacade = new ColorMongoDBFacade();
        long startTime = System.currentTimeMillis();
        //consulta materiales en mongo => mapa
        HashMap<String, Object> condiciones = new HashMap<>();
        if (activeOnly != null && activeOnly) {
            condiciones.put("active", activeOnly);
        }
        MongoClient mongo = MongoDBClient.crearConexion();
        HashMap<String, ItemDataColorDTO> colorsMongo = colorMongoFacade.consultar(mongo.getDatabase("matisses"), condiciones);
        mongo.close();
        CONSOLE.log(Level.INFO, "Finaliza proceso de consulta de colores. {0}[ms]", (System.currentTimeMillis() - startTime));
        return Response.ok(colorsMongo).build();
    }

    @GET
    @Path("synccolors/")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response syncColors() {
        long startTime = System.currentTimeMillis();
        CONSOLE.log(Level.INFO, "Inicia proceso de sincronizacion de colores...");

        ColorMongoDBFacade colorMongoFacade = new ColorMongoDBFacade();
        //consulta colores en mongo => mapa
        MongoClient mongo = MongoDBClient.crearConexion();
        HashMap<String, ItemDataColorDTO> colorsMongo = colorMongoFacade.consultar(mongo.getDatabase("matisses"), null);
        HashMap<String, ItemDataGenericColorDTO> genericColorsMongo = colorMongoFacade.consultarGenericos(mongo.getDatabase("matisses"), null);
        //consulta colores en sap => mapa
        HashMap<String, ItemDataColorDTO> colorsSAP = new HashMap<>();
        HashMap<String, ItemDataGenericColorDTO> genericColorsSAP = new HashMap<>();
        for (Object[] cols : colorSAPFacade.listarColores()) {
            String colCode = (String) cols[0];
            String colName = (String) cols[1];
            String genCode = (String) cols[2];
            String colHexa = (String) cols[3];
            String genName = (String) cols[4];

            ItemDataGenericColorDTO genericColor = new ItemDataGenericColorDTO(null, genCode, genName);

            colorsSAP.put(colCode, new ItemDataColorDTO(null, colCode, colName, colHexa, genericColor));
            if (!genericColorsSAP.containsKey(genCode)) {
                genericColorsSAP.put(genCode, genericColor);
            }
        }

        HashMap<String, Document> colorsToAdd = new HashMap<>();
        HashMap<String, Document> colorsToUpdate = new HashMap<>();
        HashMap<String, Document> genericColorsToAdd = new HashMap<>();
        HashMap<String, Document> genericColorsToUpdate = new HashMap<>();

        //Clasifica y agrega los colores genericos 
        for (String key : genericColorsSAP.keySet()) {
            if (!genericColorsMongo.containsKey(key)) {
                genericColorsToAdd.put(key, genericColorsSAP.get(key).toDocument());
            }
        }
        CONSOLE.log(Level.INFO, "Se encontraron {0} colores genericos para agregar", genericColorsToAdd.keySet().size());
        if (!genericColorsToAdd.values().isEmpty()) {
            colorMongoFacade.agregarGenericos(mongo.getDatabase("matisses"), new ArrayList(genericColorsToAdd.values()));
        }

        //Recarga la lista de colores genericos
        genericColorsMongo = colorMongoFacade.consultarGenericos(mongo.getDatabase("matisses"), null);

        //Clasifica y agrega los colores 
        for (String key : colorsSAP.keySet()) {
            if (!colorsMongo.containsKey(key)) {
                ItemDataColorDTO color = colorsSAP.get(key);
                color.setGenericColor(genericColorsMongo.get(color.getGenericColor().getCode()));
                colorsToAdd.put(key, color.toDocument());
            }
        }
        CONSOLE.log(Level.INFO, "Se encontraron {0} colores para agregar", colorsToAdd.keySet().size());
        if (!colorsToAdd.values().isEmpty()) {
            colorMongoFacade.agregar(mongo.getDatabase("matisses"), new ArrayList(colorsToAdd.values()));
        }

        //Clasifica los colores a eliminar y modificar
        for (String key : colorsMongo.keySet()) {
            ItemDataColorDTO color = colorsMongo.get(key);
            if (!colorsSAP.containsKey(key)) {
                color.setActive(false);
            } else {
                color.setName(colorsSAP.get(key).getName());
                color.setHexa(colorsSAP.get(key).getHexa());
            }
            colorsToUpdate.put(colorsMongo.get(key).getId(), color.toDocument());
        }
        for (String key : genericColorsMongo.keySet()) {
            ItemDataGenericColorDTO color = genericColorsMongo.get(key);
            if (colorsSAP.containsKey(key)) {
                color.setName(colorsSAP.get(key).getName());
            }
            genericColorsToUpdate.put(genericColorsMongo.get(key).getId(), color.toDocument());
        }

        CONSOLE.log(Level.INFO, "Se encontraron {0} colores para actualizar y/o inactivar", colorsToUpdate.keySet().size());
        if (!colorsToUpdate.values().isEmpty()) {
            colorMongoFacade.actualizar(mongo.getDatabase("matisses"), new ArrayList(colorsToUpdate.values()), "code");
        }
        CONSOLE.log(Level.INFO, "Se encontraron {0} colores genericos para actualizar y/o inactivar", genericColorsToUpdate.keySet().size());
        if (!genericColorsToUpdate.values().isEmpty()) {
            colorMongoFacade.actualizarGenericos(mongo.getDatabase("matisses"), new ArrayList(genericColorsToUpdate.values()));
        }
        mongo.close();
        CONSOLE.log(Level.INFO, "Finaliza proceso de sincronizacion de colores. {0}[ms]", (System.currentTimeMillis() - startTime));
        return Response.ok("{\"result\":\"ok\"}").build();
    }
}
