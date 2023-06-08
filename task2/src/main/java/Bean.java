import java.util.ArrayList;

public class Bean {

    private byte[] byteArray;
    private ArrayList<String> stringList;
    private int primitiveInt;
    private Bean selfReference;

    public Bean() {
        byteArray = new byte[1024];
        stringList = new ArrayList<>();
        primitiveInt = 1;
        selfReference = this;
    }
}