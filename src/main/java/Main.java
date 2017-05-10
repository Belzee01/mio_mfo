import Jama.Matrix;
import optimizer.Bounds;
import optimizer.MothFlameOptimization;
import optimizer.TestFunction;

import java.util.Arrays;

public class Main {

    public static class Levy20 implements TestFunction {

        public double functionToProcess(double[] args) {
            int n = args.length;
            double z[] = new double[n];
            for (int i = 0; i < n; i++) {
                z[i] = 1.0 + ((args[i] - 1.0) / 4.0);
            }
            double s = Math.pow(Math.sin(3.1415 * z[0]), 2.0);
            for (int i = 0; i < n - 1; i++) {
                s += Math.pow((z[i] - 1.0), 2.0) * (1.0 + 10.0 * Math.pow(Math.sin(3.1415 * z[i] + 1.0), 2.0));
            }
            return s + Math.pow(z[n - 1] - 1.0, 2.0) * (Math.pow(Math.sin(2.0 * 3.1415 * z[n - 1]), 2.0) + 1.0);
        }
    }

    public static void main(String[] args) {

        int dimensions = 2;
        int numberOfMoths = 30;

        MothFlameOptimization mothFlameOptimization = new MothFlameOptimization(
                new Levy20(),
                new Bounds(-10.0, 10.0),
                2500, numberOfMoths, dimensions);

        System.out.println(Arrays.deepToString(mothFlameOptimization.mfo()));

        System.out.println("hello");
    }
}

