import nn.optimazers.FletcherReeves;
import nn.optimazers.Function;
import nn.optimazers.Gradient;
import nn.optimazers.MockFunc;

/**
 * Created by Vlad on 09.04.2017.
 */
public class TestMO {

    public static void main(String[] args) {
        Function f = new MockFunc();
        Gradient g = new FletcherReeves(new double[]{1,2,3,4},0.01,0.05, 100);
        double arr[] = g.optimize(f);
        for (double anArr : arr) {
            System.out.println(anArr);
        }

    }

}
