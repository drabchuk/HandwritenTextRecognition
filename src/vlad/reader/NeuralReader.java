package vlad.reader;

import nn.NeuralNetwork;
import nn.perceptrons.DeepFeedforwardNN;

import java.io.*;

public final class NeuralReader {

    private File file;
    private static NeuralReader instance;
    private int depth;
    private int[] layers;
    private double[][][] theta;
    private BufferedReader reader;

    public static NeuralReader getReader() {
        if(instance == null){
            instance = new NeuralReader();
        }
        return instance;
    }

    private NeuralReader(){}

    public NeuralNetwork start(String file){
        try {
            this.file = new File(file);
            read();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return new DeepFeedforwardNN(depth,layers,theta);
    }


    public NeuralNetwork start(File file){
        try {
            this.file = file;
            read();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return new DeepFeedforwardNN(depth,layers,theta);
    }

    private void read() throws FileNotFoundException {

        String quantityLayers;
        String quantityEachWeight;
        reader = new BufferedReader(new FileReader(file));
        try {
            while ((quantityLayers = reader.readLine()) != null){
                try {
                    depth = Integer.parseInt(quantityLayers);
                    theta = new double[depth-1][][];
                    quantityEachWeight = reader.readLine();
                    layers = parseIntegerArray(quantityEachWeight.split(" "));
                    for (int i = 1; i < layers.length; i++) {
                        theta[i-1] = readWeights(layers[i-1],layers[i]);
                    }
                }catch (NumberFormatException e){
                    System.out.println("Incorrect data");
                }
            }
        } catch (IOException e) {
            System.out.println("Error with read file");
        }
    }

    private double[][] readWeights(int n, int m) {
        double arr[][] = new double[m][n + 1];
        String weight;
        try {
            for (int i = 0; i < arr.length; i++) {
                weight = reader.readLine();
                arr[i] = parseDoubleArray(weight.split(" "));
            }
        } catch (IOException e) {
            System.out.println("Error with read weights");
        }
        return arr;
    }

    private int[] parseIntegerArray(String[] arr){
        int[] iArr = new int[arr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = Integer.parseInt(arr[i]);
        }
        return iArr;
    }

    private double[] parseDoubleArray(String[] arr){
        double[] dArr = new double[arr.length];
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = Double.parseDouble(arr[i]);
        }
        return dArr;
    }

}
