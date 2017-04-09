package nn;

import java.io.File;
import java.io.IOException;

public abstract class NeuralNetwork {

    public abstract double[] predict(double[] features);
    public abstract double[][] predict(double[][] features);
    public abstract void saveWeights(File file)  throws IOException;

}
