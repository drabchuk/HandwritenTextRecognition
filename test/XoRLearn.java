import nn.optimazers.*;
import nn.perceptrons.DFFNNTrainer;
import nn.perceptrons.DeepFeedforwardNN;

/**
 * Created by Denis on 09.04.2017.
 */
public class XoRLearn {

    public static void main(String[] args) {
        int depth = 3;
        int[] sizes = new int[]{2, 2, 1};
        int m = 3;
        double[][] tx = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] ty = new double[][]{{0}, {1}, {1}, {0}};
        DeepFeedforwardNN nn = new DeepFeedforwardNN(depth, sizes);
        DFFNNTrainer trainer = new DFFNNTrainer(nn, tx, ty);

        double[][] prediction = nn.predict(tx);
        for (int i = 0; i < prediction.length; i++) {
            System.out.println();
            for (int j = 0; j < prediction[0].length; j++) {
                System.out.println(prediction[i][j] + " ");
            }
        }

        Function f = new NNFunc(trainer);
        //OptimizationMethod1Order g = new GradientDescent(trainer.reshapeTheta(), 1000);
        //OptimizationMethod1Order g = new FletcherReeves(trainer.reshapeTheta(), 1e-3, 0.1);
        OptimizationMethod1Order g = new FletcherReeves(trainer.reshapeTheta(), 1e-5, 3, 100);
        double[] min = g.optimize(f);
        trainer.setTheta(min);
        prediction = nn.predict(tx);
        for (int i = 0; i < prediction.length; i++) {
            System.out.println();
            for (int j = 0; j < prediction[0].length; j++) {
                System.out.println(prediction[i][j] + " ");
            }
        }
    }

}
