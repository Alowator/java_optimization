import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("res.csv"))) {

            Runtime runtime = Runtime.getRuntime();
            long maxMemory = runtime.maxMemory();       // Сколько памяти JVM будет есть (не процесс JVM!!!)
            long totalMemory = runtime.totalMemory();   // Сколько памяти выделено
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;

            String line = String.format("%d,%d,%d,%d\n", maxMemory, totalMemory, freeMemory, usedMemory);
            writer.write(line);
            writer.flush();
            ArrayList<Data> list = new ArrayList<>(10000000);
            while (true) {
                for (int i = 0; i < 1000; i++) {
                    list.add(new Data(i));
                }
                maxMemory = runtime.maxMemory();
                totalMemory = runtime.totalMemory();
                freeMemory = runtime.freeMemory();
                usedMemory = totalMemory - freeMemory;
                line = String.format("%d,%d,%d,%d\n", maxMemory, totalMemory, freeMemory, usedMemory);
                writer.write(line);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Data {
        public long a = 2;

        public Data(int i) {
            this.a = i;
        }
    }

}