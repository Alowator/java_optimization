public interface Image {
    int getWidth();

    int getHeight();

    int getRed(int x, int y);

    int getGreen(int x, int y);

    int getBlue(int x, int y);

    void setRed(int x, int y, int value);

    void setGreen(int x, int y, int value);

    void setBlue(int x, int y, int value);

    void applyBlurFilter(double[] blurMatrix);
}
