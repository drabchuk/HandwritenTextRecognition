package nn.optimazers;

public interface Function {

    double cost(double[] x);

    double[] grad(double[] x);

}
