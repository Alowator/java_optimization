import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Main2 {
    private static final Runtime runtime = Runtime.getRuntime();
    static long lastUsedMemory = 0;

    public static void main(String[] args) {
        show();
        Data[] arr = new Data[1000000];
        show();
        for (int i = 0; i < 1000000; i++) {
            arr[i] = new Data(i);
        }
        show();
    }

    private static void show() {
        System.gc();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long maxMemory = runtime.maxMemory();       // Сколько памяти JVM будет есть (не процесс JVM!!!)
        long totalMemory = runtime.totalMemory();   // Сколько памяти выделено
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        String line = String.format("%d, %d, %d, %d", maxMemory, totalMemory, freeMemory, usedMemory);
        System.out.println(line);
        System.out.println("Allocated: " + (usedMemory - lastUsedMemory));
        lastUsedMemory = usedMemory;
    }

    public static class Data {
        private final int i;

        public Data(int i) {
            this.i = i;
        }
    }
}
