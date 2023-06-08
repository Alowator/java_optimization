import java.lang.ref.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Cache<K, V> {
    private final Map<K, Reference<V>> cache = new HashMap<>();
    private final ReferenceQueue<V> queue = new ReferenceQueue<>();
    private final Map<K, Integer> getsCount = new TreeMap<>();
    private final Map<K, Integer> successfulGetsCount = new TreeMap<>();

    public Cache() {
        startCleanerThread();
    }

    private void startCleanerThread() {
        Thread cleanerThread = new Thread(() -> {
            while (true) {
                try {
                    Reference<?> reference = queue.remove();
                    synchronized (cache) {
                        cache.entrySet().removeIf(entry -> entry.getValue() == reference);
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    public void put(K key, V value) {
        synchronized (cache) {
            cache.put(key, createReference(value));
        }
    }

    public V get(K key) {
        if (getsCount.containsKey(key)) {
            getsCount.put(key, getsCount.get(key) + 1);
        } else {
            getsCount.put(key, 1);
        }

        synchronized (cache) {
            Reference<V> reference = cache.get(key);
            if (reference != null) {
                if (successfulGetsCount.containsKey(key)) {
                    successfulGetsCount.put(key, successfulGetsCount.get(key) + 1);
                } else {
                    successfulGetsCount.put(key, 1);
                }
                return reference.get();
            }
        }
        return null;
    }

    private Reference<V> createReference(V value) {
        return new SoftReference<>(value, queue);
    }

    public Map<K, Integer> getKeyAccessesCount() {
        return getsCount;
    }

    public Map<K, Integer> getSuccessKeyAccessesCount() {
        return successfulGetsCount;
    }
}
