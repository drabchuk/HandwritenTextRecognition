import nn.NeuralNetwork;
import nn.perceptrons.DeepFeedforwardNN;

import java.io.File;
import java.io.IOException;

/**
 * Created by Denis on 06.04.2017.
 */
public class TestIOWriter {

    public static void main(String[] args){
        int depth = 5;
        int[] sizes = new int[]{5, 4, 3, 2, 3};
        NeuralNetwork nn = new DeepFeedforwardNN(depth, sizes);
        try {
            //nn.saveWeights(new File("D:\\dev\\study\\HandwritenTextRecognition\\resources\\networks\\test1.txt"));
            nn.saveWeights(new File("resources\\networks\\test1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
