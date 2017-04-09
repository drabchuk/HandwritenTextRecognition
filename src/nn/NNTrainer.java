package nn;

/**
 * Created by Denis on 04.04.2017.
 */
public abstract class NNTrainer {

    protected NeuralNetwork nn;
    protected double[][] tx;
    protected double[][] ty;

    public NNTrainer(NeuralNetwork nn, double[][] tx, double[][] ty) {
        this.nn = nn;
        this.tx = tx;
        this.ty = ty;
    }

    public abstract void train();

    public abstract void setAccuracy(double accuracy);

    public void setTx(double[][] tx) {
        this.tx = tx;
    }

    public void setTy(double[][] ty) {
        this.ty = ty;
    }
}
