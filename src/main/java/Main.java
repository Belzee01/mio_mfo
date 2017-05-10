import optimizer.Bounds;
import optimizer.MothFlameOptimizationAlt;
import optimizer.TestFunctionBenchmark;
import optimizer.TestFunctionBenchmark.Levy20;
import optimizer.TestFunctionBenchmark.Rastrigin;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int dimensions = 10;
        int numberOfMoths = 30;

        int maxIterations = 100000;

        MothFlameOptimizationAlt mothFlameOptimizationAlt = new MothFlameOptimizationAlt(
                numberOfMoths, dimensions, new Rastrigin(), maxIterations, new Bounds(-100.0, 100.0)
        );

        System.out.println("Minimum: " + mothFlameOptimizationAlt.mfo());

        System.out.println("hello");
    }
}

