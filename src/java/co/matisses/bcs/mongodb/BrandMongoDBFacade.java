package co.matisses.bcs.mongodb;

import co.matisses.bcs.sync.ItemDataBrandDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author dbotero
 */
public class BrandMongoDBFacade extends MongoDBClient {
    private static final Logger CONSOLE = Logger.getLogger(BrandMongoDBFacade.class.getSimpleName());
    private static final String COLLECTION_NAME = "brands";

    public BrandMongoDBFacade() {
        super(COLLECTION_NAME);
    }

    @Override
    public HashMap<String, ItemDataBrandDTO> consultar(MongoDatabase db, HashMap conditions) {
        try {
            HashMap<String, ItemDataBrandDTO> result = new HashMap<>();
            //MongoDatabase db = conectar();
            MongoCollection<Document> table = db.getCollection(COLLECTION_NAME);

            BasicDBObject searchQuery = new BasicDBObject();
            if (conditions != null && !conditions.keySet().isEmpty()) {
                searchQuery.putAll(conditions);
            }

            FindIterable find = table.find(searchQuery);
            MongoCursor<Document> cursor = find.iterator();
            while (cursor.hasNext()) {
                try {
                    Document doc = cursor.next();
                    result.put(doc.get("code", String.class), new ItemDataBrandDTO(doc));
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al procesar la respuesta.", e);
                }
            }
            cursor.close();
            return result;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar documentos de la coleccion " + COLLECTION_NAME + " en MongoDB. ", e);
            return null;
        }
    }
}
