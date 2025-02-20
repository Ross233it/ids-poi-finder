package org.poifinder.dataMappers;

import jakarta.persistence.MappedSuperclass;
import org.poifinder.models.users.RegisteredUser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Questa classe ha la responsabilità di "tradurre" i dati del baseRepository
 * in oggetti e viceversa.
 * @param <D>
 */
@Service
@Primary
@MappedSuperclass
public abstract class DataMapper<D> {


    /**
     * Mappa una serie di dati provenienti dallo strato di persistenza
     * in una lista di oggetti della classe
     * @param results struttura dati contenenete informazioni sull'oggetto
     * @return istanza dell'oggetto
     */
    public List<D> mapDbDataToObjects(List<Map<String, Object>> results){
        List<D> items = new ArrayList<>();
        for (Map<String, Object> result : results) {
            D item = mapDataToObject(result);
            items.add(item);
        }
        return items;
    };

    /**
     * Mappa una serie di dati provenienti dallo strato di persistenza
     * in un oggetto della classe
     * @param result struttura dati contenenete informazioni sull'oggetto
     * @return istanza dell'oggetto
     */
    public abstract D mapDataToObject(Map<String, Object> result);


    public abstract D updateEntityFromMap(D item, Map<String, Object> result);

    protected  long castIdvalue(Object idValue){
        long id = 0L;

        if (idValue instanceof Integer) {
            id = ((Integer) idValue).longValue();
        } else if (idValue instanceof Long) {
            id = (Long) idValue;
        } else if (idValue instanceof String) {
            try {
                id = Long.parseLong((String) idValue);
            } catch (NumberFormatException e) {
                System.err.println("Errore di conversione String -> Long: " + idValue);
            }
        }
        return id;
    }


    /**
     * Recupera i parametri di un oggetto qualsiasi e li mappa in una stringa
     * in formato json
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    //todo verify use
    public static String mapObjectToJson(Object obj) throws IllegalAccessException {
            if (obj == null) {
                return "null";
            }

            if (obj instanceof String) {
                return "\"" + obj + "\"";
            }

            if (obj instanceof Number || obj instanceof Boolean) {
                return obj.toString();
            }

            if (obj instanceof Collection<?>) {
                return mapCollectionToJson((Collection<?>) obj);
            }

            // Se è un array
            if (obj.getClass().isArray()) {
                int length = Array.getLength(obj);
                StringBuilder jsonBuilder = new StringBuilder("[");
                for (int i = 0; i < length; i++) {
                    jsonBuilder.append(mapObjectToJson(Array.get(obj, i)));
                    if (i < length - 1) {
                        jsonBuilder.append(", ");
                    }
                }
                jsonBuilder.append("]");
                return jsonBuilder.toString();
            }

            // Se è una mappa (Map<K, V>)
            if (obj instanceof Map<?, ?>) {
                Map<?, ?> map = (Map<?, ?>) obj;
                StringBuilder jsonBuilder = new StringBuilder("{");
                Iterator<?> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
                    jsonBuilder.append("\"").append(entry.getKey().toString()).append("\": ");
                    jsonBuilder.append(mapObjectToJson(entry.getValue()));
                    if (iterator.hasNext()) {
                        jsonBuilder.append(", ");
                    }
                }
                jsonBuilder.append("}");
                return jsonBuilder.toString();
            }

            // Se è un oggetto generico
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");

            Class<?> currentClass = obj.getClass();
            List<Field> allFields = new ArrayList<>();

            while (currentClass != null && !currentClass.getName().startsWith("java.")) { // Evita classi del JDK
                allFields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
                currentClass = currentClass.getSuperclass();
            }

            boolean firstField = true;
            for (Field field : allFields) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                Object fieldValue = field.get(obj);

                if (!firstField) {
                    jsonBuilder.append(", ");
                }
                jsonBuilder.append("\"").append(field.getName()).append("\": ");
                jsonBuilder.append(mapObjectToJson(fieldValue));
                firstField = false;
            }

            jsonBuilder.append("}");
            return jsonBuilder.toString();
        }


    /**
     * Mappa uno oggetto Collection in una stringa formato json
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    private static String mapCollectionToJson(Collection<?> obj) throws IllegalAccessException {
        Collection<?> collection = (Collection<?>) obj;
        StringBuilder jsonBuilder = new StringBuilder("[");
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            jsonBuilder.append(mapObjectToJson(iterator.next()));
            if (iterator.hasNext()) {
                jsonBuilder.append(", ");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
