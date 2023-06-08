import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CacheTest {
    private static final int MAX_KEY_VALUE = 1000;
    private static final int ITERATIONS = 10 * 1000 * 1000;
    private static final Random random = new Random();
    private static final double STANDARD_DEVIATION = 100.0;

    public static void main(String[] args) throws IOException {
        Cache<Integer, Integer> cache = new Cache<>();

        for (int i = 0; i < ITERATIONS; i++) {
            Integer key = getRandomKey();
            if (key != null) {
                Integer value = cache.get(key);
                if (value == null) {
                    value = random.nextInt();
                    cache.put(key, value);
                }
            }
            if (i % 1000 == 0) {
                System.gc();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resCount.csv"))) {
            Map<Integer, Integer> map = cache.getSuccessKeyAccessesCount();
            for (int i = 0; i <= MAX_KEY_VALUE; i++) {
                writer.write(String.format("%d,%d\n", i, map.getOrDefault(i, 0)));
            }
        }
    }

    private static Integer getRandomKey() {
        double value = random.nextGaussian() * STANDARD_DEVIATION + (MAX_KEY_VALUE / 2.0);
        int v = (int) Math.round(value);
        if (v < 0 || v > 1000) {
            return null;
        } else {
            return v;
        }
    }

    public static void mainOld(String[] args) throws IOException {
        Cache<Integer, Integer> cache = new Cache<>();
        Deque<Integer> queueElem = new ArrayDeque<>();
        Deque<Long> queueTime = new ArrayDeque<>();
        Long[] time = new Long[MAX_KEY_VALUE + 1];
        for (int i = 0; i <= MAX_KEY_VALUE; i++) {
            time[i] = 0L;
        }

        for (int i = 0; i < ITERATIONS; i++) {
            Integer key = getRandomKey();
            if (key != null) {
                Integer value = cache.get(key);
                if (value == null) {
                    value = random.nextInt();
                    cache.put(key, value);
                }
                queueElem.addLast(key);
                queueTime.addLast(System.currentTimeMillis());

                if (queueElem.size() > ITERATIONS / 10) {
                    time[queueElem.pollFirst()] += System.currentTimeMillis() - queueTime.pollFirst();
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resCount.csv"))) {
            Map<Integer, Integer> map = cache.getKeyAccessesCount();
            for (int i = 0; i <= MAX_KEY_VALUE; i++) {
                writer.write(String.format("%d,%d\n", i, map.getOrDefault(i, 0)));
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resTime.csv"))) {
            Map<Integer, Integer> map = cache.getKeyAccessesCount();
            for (int i = 0; i <= MAX_KEY_VALUE; i++) {
                writer.write(String.format("%d,%d\n", i, time[i] / map.getOrDefault(i, 1)));
            }
        }
    }
}