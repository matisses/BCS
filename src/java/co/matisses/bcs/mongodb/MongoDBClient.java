package co.matisses.bcs.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
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
public abstract class MongoDBClient {

    private static final Logger CONSOLE = Logger.getLogger(MongoDBClient.class.getSimpleName());
    private String collectionName;

    public MongoDBClient(String collectionName) {
        this.collectionName = collectionName;
    }

    public final void agregar(MongoDatabase db, List documents) {
        if (documents == null || documents.isEmpty()) {
            return;
        }
        try {
            //MongoDatabase db = conectar();
            MongoCollection<Document> collection = db.getCollection(collectionName);
            collection.insertMany(documents);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al agregar documentos a la coleccion " + collectionName + " de MongoDB. ", e);
        }
    }

    public final void actualizar(MongoDatabase db, List documents, String idColumnName) {
        if (documents == null || documents.isEmpty()) {
            return;
        }
        try {
            //MongoDatabase db = conectar();
            MongoCollection<Document> collection = db.getCollection(collectionName);
            for (Document doc : (List<Document>) documents) {
                BasicDBObject searchQuery = new BasicDBObject();
                searchQuery.put(idColumnName, doc.get(idColumnName));

                collection.findOneAndReplace(searchQuery, doc);
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar documentos en la coleccion " + collectionName + " en MongoDB. ", e);
        }
    }

    public abstract <T> HashMap<String, T> consultar(MongoDatabase db, HashMap<String, String> conditions);

    public static MongoClient crearConexion() {

        // Set credentials      
        //MongoCredential credential = MongoCredential.createCredential("web", "matisses", "Baru1234.".toCharArray());
        //ServerAddress serverAddress = new ServerAddress("192.168.5.78", 27017);

        // Mongo Client
        //MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
        //return mongoClient;

        MongoClientURI uri = new MongoClientURI("mongodb://192.168.5.78:27017");
        MongoClient mongo = new MongoClient(uri);
        return mongo;
        //MongoDatabase db = mongo.getDatabase("matisses");
        //return db;
    }
}
