package classes;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Kolia on 30.10.2016.
 */
abstract public class Parser {

    public static byte[][] parseX(RandomAccessFile file) throws IOException{
        byte[][] pixels;
        int magicNumber = file.readInt();
        if (magicNumber != 2051) {
            System.out.println("ERROR");
            return null;
        }
        int size = file.readInt();
        int height = file.readInt();
        int width = file.readInt();
        int resolution = height * width;
        pixels = new byte[size][resolution];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < resolution; j++) {
                pixels[i][j] = file.readByte();
            }
        }
        return pixels;
    }

    public static byte[] parseY(RandomAccessFile file) throws IOException{
        byte[] labels;
        int magicNumber = file.readInt();
        if (magicNumber != 2049) {
            System.out.println("ERROR");
            return null;
        }
        int size = file.readInt();
        labels = new byte[size];
        for (int i = 0; i < size; i++) {
            labels[i] = file.readByte();
        }
        return labels;
    }
}
