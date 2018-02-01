package co.matisses.bcs.sync;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author dbotero
 * @param <T>
 */
public abstract class MongoDataDTO<T> {

    private static final Logger CONSOLE = Logger.getLogger(MongoDataDTO.class.getSimpleName());

    private Class<T> entityClass;

    public MongoDataDTO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Deprecated
    private MongoDataDTO(Class<T> entityClass, Document doc) {
        this.entityClass = entityClass;
        if (doc == null) {
            return;
        }
        for (String key : doc.keySet()) {
            String methodName;
            if (key.equals("_id")) {
                methodName = "setId";
            } else {
                methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
            }
            for (Method method : entityClass.getDeclaredMethods()) {
                if (method.getName().equals(methodName)) {
                    CONSOLE.log(Level.INFO, "Invocando metodo {0}", methodName);
                    Object value = doc.get(key);
                    try {
                        if (value.getClass().toString().startsWith("co.matisses.bcs.sync")) {
                            method.invoke(this, value);
                        } else {
                            method.invoke(this, value.toString());
                        }
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al ejecutar el metodo. ", e);
                    }
                }
            }
            CONSOLE.log(Level.INFO, this.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        Document doc = new Document();
        doc.put("_id", new ObjectId("5996f23f70f01768178bf052"));

        Document color = new Document();
        color.put("code", "123456");
        color.put("name", "nombre color");
        color.put("hexa", "aaa");
        color.put("active", true);
        doc.put("color", color);

        new ItemDataDTO(doc);

    }

    public Document toDocument2(String s) {
        return null;
    }

    public Document toDocument() {
        Document doc = new Document();
        for (Method method : entityClass.getDeclaredMethods()) {
            if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
                //CONSOLE.log(Level.INFO, "Metodo {0} retorna {1}", new Object[]{method.getName(), method.getReturnType().getName()});
                try {
                    //CONSOLE.log(Level.INFO, "{0} => {1}", new Object[]{method.getName(), method.invoke(this)});
                    Object invokeResult = method.invoke(this);
                    if (method.getName().endsWith("Id")) {
                        if (invokeResult != null) {
                            doc.put("_id", new ObjectId((String) invokeResult));
                        }
                    } else if (method.getReturnType().getName().startsWith("co.matisses.bcs.sync") && invokeResult != null) {
                        //CONSOLE.log(Level.INFO, "Agregando documento anidado {0}",
                        //        new Object[]{method.getName().substring(3).toLowerCase(), ((MongoDataDTO) method.invoke(this)).toDocument()});
                        doc.put(method.getName().substring(3).toLowerCase(), ((MongoDataDTO) invokeResult).toDocument());
                    } else if (method.getReturnType().getName().equals("java.util.List") && invokeResult != null) {
                        List<Object> documents = new ArrayList<>();
                        for (Object obj : (List) invokeResult) {
                            if (obj.getClass().getName().startsWith("co.matisses.bcs.sync")) {
                                documents.add(((MongoDataDTO) obj).toDocument());
                            } else {
                                documents.add(obj);
                            }
                        }
                        doc.put(method.getName().substring(3).toLowerCase(), documents);
                    } else {
                        doc.put(method.getName().substring(3).toLowerCase(), invokeResult);
                    }
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al convertir el DTO a Document. ", e);
                }
            }
        }
        //CONSOLE.log(Level.INFO, "Document: {0}", doc.toJson());
        return doc;
    }
}
