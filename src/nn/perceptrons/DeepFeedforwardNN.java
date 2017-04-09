package nn.perceptrons;

import static nn.algebra.Alg.*;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import nn.NeuralNetwork;
import vlad.reader.NeuralWriter;

import java.io.File;
import java.io.IOException;

/**
 * Created by Denis on 30.03.2017.
 */
public class DeepFeedforwardNN extends NeuralNetwork {

    private int depth;
    private int[] layerSizes;
    private double[][][] theta;

    public DeepFeedforwardNN(int depth, int[] layerSizes) {
        this.depth = depth;
        this.layerSizes = layerSizes;
        this.theta = new double[depth - 1][][];
        for (int i = 0; i < depth - 1; i++) {
            this.theta[i] = randInitForWeights(layerSizes[i + 1], layerSizes[i] + 1);
        }
    }

    public DeepFeedforwardNN(int depth, int[] layerSizes, double[][][] theta) {
        this.depth = depth;
        this.layerSizes = layerSizes;
        this.theta = theta;
    }

    @Override
    public double[] predict(double[] features) {
        double[] a = features;
        for (int i = 0; i < depth - 1; i++) {
            a = addForwardNum(1.0, a);
            double[] z = mult(theta[i], a);
            a = sigmoid(z);
        }
        return a;
    }

    @Override
    public double[][] predict(double[][] features) {
        double[][] res = new double[features.length][];
        for (int j = 0; j < features.length; j++) {
            double[] a = features[j];
            for (int i = 0; i < depth - 1; i++) {
                a = addForwardNum(1.0, a);
                double[] z = mult(theta[i], a);
                a = sigmoid(z);
            }
            res[j] = a;
        }

        return res;
    }

    @Override
    public void saveWeights(File file) throws IOException {
        NeuralWriter.getWriter().start(file, depth, layerSizes, theta);
    }

}
