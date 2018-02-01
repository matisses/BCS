package co.matisses.bcs.mongodb;

import co.matisses.bcs.sync.ItemDataColorDTO;
import co.matisses.bcs.sync.ItemDataGenericColorDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author dbotero
 */
public class ColorMongoDBFacade extends MongoDBClient {
    private static final Logger CONSOLE = Logger.getLogger(ColorMongoDBFacade.class.getSimpleName());
    private static final String COLLECTION_NAME = "colors";

    public ColorMongoDBFacade() {
        super(COLLECTION_NAME);
    }
    
    public void agregarGenericos(MongoDatabase db, List documents) {
        if(documents == null || documents.isEmpty()){
            return;
        }
        try {
            //MongoDatabase db = conectar();
            MongoCollection<Document> collection = db.getCollection("generic" + COLLECTION_NAME);
            collection.insertMany(documents);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al agregar documentos a MongoDB. ", e);
        }
    }
    
    public void actualizarGenericos(MongoDatabase db, List documents) {
        if(documents == null || documents.isEmpty()){
            return;
        }
        try {
            //MongoDatabase db = conectar();
            MongoCollection<Document> collection = db.getCollection("Generic" + COLLECTION_NAME);
            for (Document doc : (List<Document>)documents) {
                BasicDBObject searchQuery = new BasicDBObject();
                searchQuery.put("code", doc.get("code"));

                collection.findOneAndReplace(searchQuery, doc);
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar documentos en MongoDB. ", e);
        }
    }
    
    @Override
    public HashMap<String, ItemDataColorDTO> consultar(MongoDatabase db, HashMap conditions) {
        try {
            HashMap<String, ItemDataColorDTO> result = new HashMap<>();
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
                    result.put(doc.get("code", String.class), new ItemDataColorDTO(doc));
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
    
    public HashMap<String, ItemDataGenericColorDTO> consultarGenericos(MongoDatabase db, HashMap conditions) {
        try {
            HashMap<String, ItemDataGenericColorDTO> result = new HashMap<>();
            //MongoDatabase db = conectar();
            MongoCollection<Document> table = db.getCollection("generic" + COLLECTION_NAME);

            BasicDBObject searchQuery = new BasicDBObject();
            if (conditions != null && !conditions.keySet().isEmpty()) {
                searchQuery.putAll(conditions);
            }

            FindIterable find = table.find(searchQuery);
            MongoCursor<Document> cursor = find.iterator();
            while (cursor.hasNext()) {
                try {
                    Document doc = cursor.next();
                    result.put(doc.get("code", String.class), new ItemDataGenericColorDTO(doc));
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
