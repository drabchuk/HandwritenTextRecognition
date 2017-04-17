package nn.optimazers;

public interface OptimizationMethod1Order {

    double[] optimize(Function func);
    double[] optimize(Function func, int maxTime);

}
