public class ImageJava implements Image {
    private int[][][] pixels;
    private int width;
    private int height;

    public ImageJava(int width, int height) {
        this.height = height;
        this.width = width;
        this.pixels = new int[height][width][3];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getRed(int x, int y) {
        return pixels[x][y][0];
    }

    @Override
    public int getGreen(int x, int y) {
        return pixels[y][x][1];
    }

    @Override
    public int getBlue(int x, int y) {
        return pixels[y][x][2];
    }

    @Override
    public void setRed(int x, int y, int value) {
        pixels[y][x][0] = value;
    }

    @Override
    public void setGreen(int x, int y, int value) {
        pixels[y][x][1] = value;
    }

    @Override
    public void setBlue(int x, int y, int value) {
        pixels[y][x][2] = value;
    }

    @Override
    public void applyBlurFilter(double[] blurMatrix) {
        int[][][] blurredPixels = new int[height][width][3];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int neighborX = x + j;
                        int neighborY = y + i;

                        if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                            double blurFactor = blurMatrix[(i + 1) * 3 + j + 1];
                            redSum += getRed(neighborX, neighborY) * blurFactor;
                            greenSum += getGreen(neighborX, neighborY) * blurFactor;
                            blueSum += getBlue(neighborX, neighborY) * blurFactor;
                        }
                    }
                }

                blurredPixels[y][x][0] = redSum;
                blurredPixels[y][x][1] = greenSum;
                blurredPixels[y][x][2] = blueSum;
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setRed(x, y, blurredPixels[y][x][0]);
                setGreen(x, y, blurredPixels[y][x][1]);
                setBlue(x, y, blurredPixels[y][x][2]);
            }
        }
    }
}