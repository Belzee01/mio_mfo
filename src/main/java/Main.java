import optimizer.Bounds;
import optimizer.MothFlameOptimizationAlt;
import optimizer.TestFunction;
import optimizer.TestFunctionBenchmark;
import optimizer.TestFunctionBenchmark.Levy20;
import optimizer.TestFunctionBenchmark.Rastrigin;
import optimizer.TestFunctionBenchmark.Rosenbrock;
import optimizer.TestFunctionBenchmark.Zakharov;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        int dimensions = 10;
        int numberOfMoths[] = {30, 100, 300};

        int numberOfExecutions = 6;

        int maxIterations = 100000;

        TestFunction[] testFunctions = {
                //new TestFunctionBenchmark.BentCigar(),
                new Rosenbrock(),
//                new Rastrigin(),
//                new Zakharov()
        };

        MothFlameOptimizationAlt[] mothFlameOptimizationAlts = new MothFlameOptimizationAlt[30];

        List<XYChart> charts = new ArrayList<>();

        for (int z = 0; z < testFunctions.length; z ++) {
            for (int j = 0; j < 1; j++) {
                for (int i = 0; i < numberOfExecutions; i++) {
                    mothFlameOptimizationAlts[i] = new MothFlameOptimizationAlt(numberOfMoths[j], dimensions, testFunctions[z], maxIterations, new Bounds(-100.0, 100.0));
                }

                for (int i = 0; i < numberOfExecutions; i++) {
                    mothFlameOptimizationAlts[i].mfo();
                }

                for (int i = 0; i < numberOfExecutions; i++) {
                    List<Double> solutionsFitness = mothFlameOptimizationAlts[i].getSolutionsByIterations();

                    double[] solvs = new double[solutionsFitness.size()];

                    for (int k = 0; k < solutionsFitness.size(); k++) {
                        solvs[k] = solutionsFitness.get(k);
                    }

                    double[] iterations = new double[solutionsFitness.size()];

                    for (int k = 1; k <= solutionsFitness.size(); k++) {
                        iterations[k-1] = (double) k;
                    }

                    XYChart chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
                    chart.getStyler().setXAxisLogarithmic(true);
                    XYSeries series = chart.addSeries("" + i, iterations, solvs);
                    series.setMarker(SeriesMarkers.NONE);
                    charts.add(chart);
                }
            }
        }

        new SwingWrapper<>(charts).displayChartMatrix();

        System.out.println("hello");
    }
}

