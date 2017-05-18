import optimizer.Bounds;
import optimizer.MothFlameOptimizationAlt;
import optimizer.TestFunction;
import optimizer.TestFunctionBenchmark;
import optimizer.TestFunctionBenchmark.*;
import optimizer.model.Moth;
import optimizer.model.MyPointsContainer;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void initialTests() {
        TestFunction rastrigin = new Rastrigin();
        TestFunction zakharov = new Zakharov();
        TestFunction rosenbrock = new Rosenbrock();
        TestFunction bentCigar = new BentCigar();

        MyPointsContainer myPointsContainer = new MyPointsContainer();

        for (double i = -100.0; i <= 100.0; i++) {

        }
    }

    public static void main(String[] args) {

        int dimensions = 10;
        int numberOfMoths = 100;

        int maxIterations = 100000;

        double[] xData = new double[10];
        double[] yData = new double[10];

        System.out.println("hello");
    }
}

