package unipotsdam.gf.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CollectionUtil {

    /**
     * assumes C is an ArrayList or will crash and burn
     * @param mapToUpdate
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <K, V> void updateValueInMap(
            Map<K, ArrayList<V>> mapToUpdate, K key, V value)
            throws IllegalAccessException, InstantiationException {
        if (mapToUpdate.containsKey(key)) {
            ArrayList<V> existingSet = mapToUpdate.get(key);
            existingSet.add(value);
            mapToUpdate.put(key, existingSet);
        } else {
            //C vs = typeclass.newInstance();
            ArrayList<V> newSet = new ArrayList<V>();
            newSet.add(value);
            mapToUpdate.put(key, newSet);
        }
    }
}
