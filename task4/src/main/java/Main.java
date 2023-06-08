import java.nio.ByteBuffer;

public class Main {

    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) throws InterruptedException {
        // - Показать, что будет если сожрать память внутри C
        System.out.println("Calling native function that consumes all available memory...");
        allocateMemory();
        System.out.println("Native function returned\n");

        // - Получить строку String и вернуть длину строки
        String message = "Hello from Java!";
        int length = getStringLength(message);
        System.out.println("String length is " + length + "\n");

        // - Получить объект и вызвать у него метод
        Main mainInstance = createObject();
        System.out.println("Object created\n");

        // - Получить объект и поменять значение его java-поля
        setObjectField(mainInstance, 42);
        System.out.println("New field value: " + mainInstance.testField);

        // - Сделать три метода:
        //    1. Аллоцировать структуру, вернуть указатель
        long structPointer = allocateStruct();
        System.out.println("Structure allocated at " + structPointer);

        //    2. Получить указатель на структуру, вернуть значение изнутри структуры
        int value = getStructValue(structPointer);
        System.out.println("Struct value: " + value);

        //    3. Получить указатель и освободить память
        freeStruct(structPointer);

        // - Что будет если сломаться внутри C (поделиться на ноль)
        divideByZero();

        Thread.sleep(100000);
    }

    public void testMethod() {
        System.out.println("Hello from Main");
    }

    public int testField = 0;

    private static native void allocateMemory();

    private static native int getStringLength(String str);

    private static native Main createObject();

    private static native void setObjectField(Main mainINstance, int value);

    private static native long allocateStruct();

    private static native int getStructValue(long structPointer);

    private static native void freeStruct(long structPointer);

    private static native int divideByZero();
}