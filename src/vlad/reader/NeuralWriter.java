package vlad.reader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public final class NeuralWriter {

    private File file;
    private int depth;
    private int[] layers;
    private double[][][] theta;

    private static NeuralWriter instance;

    public static NeuralWriter getWriter() {
        if(instance == null){
            instance = new NeuralWriter();
        }
        return instance;
    }

    private NeuralWriter(){}


    public String start(String filename, int depth, int[] layers, double theta[][][]){
        this.depth = depth;
        this.layers = layers;
        this.theta = theta;
        try {
            this.file = new File(filename);
            write();
        } catch (IOException e) {
            System.out.println("Error with write file");
            return "File not created";
        }
        return "File created and written";
    }

    public String start(File file, int depth, int[] layers, double theta[][][]){
        this.depth = depth;
        this.layers = layers;
        this.theta = theta;
        try {
            this.file = file;
            write();
        } catch (IOException e) {
            System.out.println("Error with write file");
            return "File not created";
        }
        return "File created and written";
    }

    private void write() throws IOException{

        PrintWriter writer = new PrintWriter(new FileWriter(this.file));
        writer.write(depth + "\n");
        for (int layer : layers) {
            writer.write(layer + " ");
        }
        writer.write("\n");
        for (double[][] aTheta : theta) {
            for (double[] anATheta : aTheta) {
                writer.write(arrayToString(anATheta) + "\n");
            }
        }
        writer.flush();
    }

    private String arrayToString(double[] arr){
        String s = "";
        for (double anArr : arr) {
            s += anArr + " ";
        }
        return s;
    }

}
