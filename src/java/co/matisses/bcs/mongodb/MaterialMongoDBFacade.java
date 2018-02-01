package co.matisses.bcs.mongodb;

import co.matisses.bcs.sync.ItemDataMaterialDTO;
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
public class MaterialMongoDBFacade extends MongoDBClient {
    
    private static final Logger CONSOLE = Logger.getLogger(MaterialMongoDBFacade.class.getSimpleName());
    private static final String COLLECTION_NAME = "materials";

    public MaterialMongoDBFacade() {
        super(COLLECTION_NAME);
    }

    @Override
    public HashMap<String, ItemDataMaterialDTO> consultar(MongoDatabase db, HashMap conditions) {
        try {
            HashMap<String, ItemDataMaterialDTO> result = new HashMap<>();
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
                    result.put(doc.get("code", String.class), new ItemDataMaterialDTO(doc));
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al procesar la respuesta.", e);
                }
            }
            cursor.close();
            return result;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar documentos en MongoDB. ", e);
            return null;
        }
    }
}
