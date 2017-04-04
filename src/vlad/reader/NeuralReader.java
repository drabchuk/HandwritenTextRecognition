package vlad.reader;

import java.io.*;

public final class NeuralReader {

    private File file;
    private static NeuralReader instance;
    private double[][][] theta;
    private BufferedReader reader;

    public static NeuralReader getReader() {
        if(instance == null){
            instance = new NeuralReader();
        }
        return instance;
    }

    private NeuralReader(){}

    public double[][][] start(String file){
        try {
            this.file = new File(file);
            read();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return theta;
    }

    private void read() throws FileNotFoundException {

        String quantityLayers;
        String quantityEachWeight;
        int indexes[];
        reader = new BufferedReader(new FileReader(file));
        try {
            while ((quantityLayers = reader.readLine()) != null){
                try {
                    theta = new double[Integer.parseInt(quantityLayers)-1][][];
                    quantityEachWeight = reader.readLine();
                    indexes = parseIntegerArray(quantityEachWeight.split(" "));
                    for (int i = 1; i < indexes.length; i++) {
                        theta[i-1] = readWeights(indexes[i-1],indexes[i]);
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
