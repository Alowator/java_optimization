public class ImageC implements Image {

    static {
        System.loadLibrary("image");
    }

    private int[][][] pixels;
    private int width;
    private int height;

    public ImageC(int width, int height) {
        init(width, height);
    }


    private native void init(int width, int height);

    @Override
    public native int getWidth();

    @Override
    public native int getHeight();

    @Override
    public native int getRed(int x, int y);

    @Override
    public native int getGreen(int x, int y);

    @Override
    public native int getBlue(int x, int y);

    @Override
    public native void setRed(int x, int y, int value);

    @Override
    public native void setGreen(int x, int y, int value);

    @Override
    public native void setBlue(int x, int y, int value);

    @Override
    public native void applyBlurFilter(double[] blurMatrix);
}