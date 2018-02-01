package co.matisses.bcs.mongodb;

import co.matisses.bcs.sync.ItemDataStockDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteError;
import com.mongodb.BulkWriteException;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author dbotero
 */
public class ItemStockMongoDBFacade extends MongoDBClient {

    private static final Logger CONSOLE = Logger.getLogger(ItemStockMongoDBFacade.class.getSimpleName());
    private static final String COLLECTION_NAME = "stock";

    public ItemStockMongoDBFacade() {
        super(COLLECTION_NAME);
    }

    public void marcarSinInventario(MongoDatabase db, String itemCode) {
        //MongoDatabase db = conectar();
        MongoCollection<Document> mongoCollection = db.getCollection(COLLECTION_NAME);
        BasicDBObject updateFilter = new BasicDBObject();
        updateFilter.put("itemCode", itemCode);

        Document setDocument = new Document();
        setDocument.put("quantity", 0);

        Document updateDocument = new Document();
        updateDocument.append("$set", setDocument);

        try {
            mongoCollection.updateMany(updateFilter, updateDocument);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al marcar el producto sin inventario. ", e);
        }

    }

    public void actualizarMultiple(MongoDatabase db, List<ItemDataStockDTO> stock) {
        List<WriteModel<Document>> updateDocuments = new ArrayList<>();
        for (ItemDataStockDTO stockRecord : stock) {
            //Finder doc
            Document filterDocument = new Document();
            filterDocument.put("itemCode", stockRecord.getItemCode());
            filterDocument.put("whsCode", stockRecord.getWhsCode());

            //Update doc
            Document setDocument = new Document();
            setDocument.put("quantity", stockRecord.getQuantity());

            Document updateDocument = new Document();
            updateDocument.put("$set", setDocument);

            //Update option
            UpdateOptions updateOptions = new UpdateOptions();
            updateOptions.upsert(true); //if true, will create a new doc in case of unmatched find
            updateOptions.bypassDocumentValidation(true); //set true/false

            //Prepare list of Updates
            updateDocuments.add(
                    new UpdateOneModel<>(
                            filterDocument,
                            updateDocument,
                            updateOptions));

        }

        //Bulk write options
        BulkWriteOptions bulkWriteOptions = new BulkWriteOptions();
        bulkWriteOptions.ordered(false);
        bulkWriteOptions.bypassDocumentValidation(true);

        //MongoDatabase db = conectar();
        MongoCollection<Document> mongoCollection = db.getCollection(COLLECTION_NAME);

        BulkWriteResult bulkWriteResult = null;
        try {
            //Perform bulk update
            bulkWriteResult = mongoCollection.bulkWrite(updateDocuments, bulkWriteOptions);
            int rowsUpdated = bulkWriteResult.getModifiedCount();
            CONSOLE.log(Level.INFO, "Se procesaron {0} registros", rowsUpdated);
        } catch (BulkWriteException e) {
            //Handle bulkwrite exception
            List<BulkWriteError> bulkWriteErrors = e.getWriteErrors();
            for (BulkWriteError bulkWriteError : bulkWriteErrors) {
                int failedIndex = bulkWriteError.getIndex();
                System.out.println("Failed record: " + stock.get(failedIndex));
                //handle rollback
            }
        }

    }

    @Override
    public HashMap<String, ItemDataStockDTO> consultar(MongoDatabase db, HashMap conditions) {
        try {
            HashMap<String, ItemDataStockDTO> result = new HashMap<>();
            //MongoDatabase db = conectar();
            MongoCollection<Document> table = db.getCollection(COLLECTION_NAME);

            BasicDBObject searchQuery = new BasicDBObject();
            if (conditions != null && !conditions.keySet().isEmpty() && conditions.containsKey("itemCode")) {
                searchQuery.putAll(conditions);
            }

            FindIterable find = table.find(searchQuery);
            MongoCursor<Document> cursor = find.iterator();
            while (cursor.hasNext()) {
                try {
                    Document doc = cursor.next();
                    result.put(doc.get("whsCode", String.class), new ItemDataStockDTO(doc));
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
