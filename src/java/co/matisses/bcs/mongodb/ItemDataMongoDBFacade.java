package co.matisses.bcs.mongodb;

import co.matisses.bcs.sync.ItemDataDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author dbotero
 */
public class ItemDataMongoDBFacade extends MongoDBClient {

    private static final Logger CONSOLE = Logger.getLogger(ItemDataMongoDBFacade.class.getSimpleName());
    private static final String COLLECTION_NAME = "items";

    public ItemDataMongoDBFacade() {
        super(COLLECTION_NAME);
    }

    /**
     * Actualiza o inserta el item, dependiendo de si ya existe el itemcode en
     * la base de datos
     *
     * @param db
     * @param docToProcess el documento a insertar/actualizar
     */
    public void insertualizar(MongoDatabase db, Document docToProcess) {
        //CONSOLE.log(Level.INFO, "Insertualizar: {0}", docToProcess.toJson());
        //MongoDatabase db = conectar();
        MongoCollection<Document> mongoCollection = db.getCollection(COLLECTION_NAME);
        BasicDBObject filter = new BasicDBObject();
        filter.put("itemcode", docToProcess.get("itemcode"));

        //Update option
        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true); //if true, will create a new doc in case of unmatched find
        updateOptions.bypassDocumentValidation(true); //set true/false

        mongoCollection.replaceOne(filter, docToProcess, updateOptions);
    }

    public void actualizarImagenes(MongoDatabase db, String itemCode, List<String> images) {
        MongoCollection<Document> mongoCollection = db.getCollection(COLLECTION_NAME);
        
        BasicDBObject filter = new BasicDBObject();
        filter.put("itemcode", itemCode);
        
        BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("images", images));
        mongoCollection.updateOne(filter, set);

        //mongoCollection.replaceOne(filter, new Document("images", docToProcess.get("images")), updateOptions);
    }

    @Override
    public HashMap<String, ItemDataDTO> consultar(MongoDatabase db, HashMap conditions) {
        try {
            HashMap<String, ItemDataDTO> result = new HashMap<>();
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
                    result.put(doc.get("itemcode", String.class), new ItemDataDTO(doc));
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
