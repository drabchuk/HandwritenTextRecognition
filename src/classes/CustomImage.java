package classes;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

/**
 * Created by Kolia on 30.10.2016.
 */
public class CustomImage {

    byte[][] pixels;

    public CustomImage(byte[][] pixels) {
        this.pixels = pixels;
    }

    public CustomImage(byte[] pixels1d) {
        int size = (int) Math.sqrt(pixels1d.length);
        pixels = new byte[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(pixels1d, i * size, pixels[i], 0, size - 1);
        }
    }

    public WritableImage getWritableImage() {
        BufferedImage imgBuf = this.getBufferedImg();
        WritableImage wr = null;
        if (imgBuf != null) {
            wr = new WritableImage(imgBuf.getWidth(), imgBuf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < imgBuf.getWidth(); x++) {
                for (int y = 0; y < imgBuf.getHeight(); y++) {
                    pw.setArgb(x, y, imgBuf.getRGB(y, x));
                }
            }

        }
        return wr;
    }

    public BufferedImage getBufferedImg() {
        int height = pixels.length;
        int width = pixels[0].length;
        BufferedImage imgBuf = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imgBuf.setRGB(i, j, 5 * pixels[i][j]);
            }
        }
        return imgBuf;
    }
}