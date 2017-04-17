package nn.data;

/**
 * Created by Denis on 16.04.2017.
 */
public class ImgBrightnessMapper {

    public static double map(byte brightness) {
        double res;
        if (brightness >= 0) {
            res =  (double) brightness / 256.0;
        } else {
            res = ((double) brightness + 256.0) / 256.0;
        }
        return res;
    }

}
