package cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    WeakHashMap<K, V> map;
    List<HwListener<K, V>> listenerList;

    enum MyCacheAction {
        PUT,
        GET,
        REMOVE
    }

    public MyCache() {
        map = new WeakHashMap<>();
        listenerList = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
        notifyListeners(key, value, MyCacheAction.PUT);
    }

    @Override
    public void remove(K key) {
        V value = map.remove(key);
        notifyListeners(key, value, MyCacheAction.REMOVE);
    }

    @Override
    public V get(K key) {
        V value = map.get(key);
        notifyListeners(key, value, MyCacheAction.GET);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }

    private void notifyListeners(K key, V value, MyCacheAction action) {
        listenerList.forEach(listener -> listener.notify(key, value, action.name()));
    }
}
