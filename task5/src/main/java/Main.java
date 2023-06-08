import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int width = 2048;
        int height = 2048;
        double[] blurMatrix = {
            1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9, 1.0 / 9
        };

        Image imageA = new ImageJava(width, height);

        fill(imageA);

        long startA = System.currentTimeMillis();
        imageA.applyBlurFilter(blurMatrix);
        System.out.println("imageA: " + (System.currentTimeMillis() - startA));
    }

    private static void check(Image imageA, Image imageB) {
        boolean res = imageA.getWidth() == imageB.getWidth() && imageA.getHeight() == imageB.getHeight();

        for (int i = 0; i < imageA.getWidth() && res; i++) {
            for (int j = 0; j < imageA.getHeight() && res; j++) {
                if (imageA.getRed(i, j) != imageB.getRed(i, j)) {
                    res = false;
                }
                if (imageA.getGreen(i, j) != imageB.getGreen(i, j)) {
                    res = false;
                }
                if (imageA.getBlue(i, j) != imageB.getBlue(i, j)) {
                    res = false;
                }
            }
        }

        if (res) {
            System.out.println("OK");
        } else {
            System.out.println("not equals");
        }
    }

    private static void fill(Image image) {
        Random random = new Random(54);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int red = (int) (random.nextDouble(0., 1.) * 256);
                int green = (int) (random.nextDouble(0., 1.) * 256);
                int blue = (int) (random.nextDouble(0., 1.) * 256);

                image.setRed(x, y, red);
                image.setGreen(x, y, green);
                image.setBlue(x, y, blue);
            }
        }
    }
}