import nn.optimazers.FletcherReeves;
import nn.optimazers.Function;
import nn.optimazers.Gradient;
import nn.optimazers.NNFunc;
import nn.perceptrons.DFFNNTrainer;
import nn.perceptrons.DeepFeedforwardNN;

/**
 * Created by Denis on 09.04.2017.
 */
public class OptimizerTest {

    public static void main(String[] args) {
        int depth = 3;
        int[] sizes = new int[]{2, 2, 2};
        int m = 3;
        double[][] tx = new double[][]{{0.2, 0.5}, {0.3, 0.4}, {-0.2, -0.1}};
        double[][] ty = new double[][]{{0, 1}, {1, 0}, {0, 1}};
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
        Gradient g = new FletcherReeves(trainer.reshapeTheta(), 1e-4, 1e-4);
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
