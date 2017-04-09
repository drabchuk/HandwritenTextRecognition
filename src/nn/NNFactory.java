package nn;

import vlad.reader.NeuralReader;

import java.io.File;
import java.io.IOException;

public class NNFactory {

    public static NeuralNetwork createDFFNN(File file) throws IOException {
        return NeuralReader.getReader().start(file);
    }

}
