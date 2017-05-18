import file.CustomFileReader;
import file.CustomFileWriter;
import optimizer.Bounds;
import optimizer.MothFlameOptimizationAlt;
import optimizer.TestFunction;
import optimizer.TestFunctionBenchmark.BentCigar;
import optimizer.TestFunctionBenchmark.Rastrigin;
import optimizer.TestFunctionBenchmark.Rosenbrock;
import optimizer.TestFunctionBenchmark.Zakharov;
import optimizer.model.MothContainer;
import optimizer.model.MyPointsContainer;

public class Main {

    private static int[] numberOfAgents = {60, 120, 180};

    private static int numberOfExecutions = 30;

    private static Bounds bounds = new Bounds(-100.0, 100.0);

    public static void initialTests() {

        TestFunction[] testFunctions = {
                new Rastrigin(), new Zakharov(), new Rosenbrock(), new BentCigar()
        };

        MyPointsContainer[] myPointsContainer = {
                new MyPointsContainer(2001), new MyPointsContainer(2001), new MyPointsContainer(2001), new MyPointsContainer(2001)
        };

        String[] names = {
                "rastrigin", "zakharov", "rosenbrock", "bentcigar"
        };

        for (int t = 0; t < testFunctions.length; t++) {
            double[] points = new double[2];
            for (double i = -100.0; i <= 100.0; i += 1.0) {
                for (double j = -100.0; j <= 100.0; j += 1.0) {
                    points[0] = i;
                    points[1] = j;
                    myPointsContainer[t].addNewPoint(i, j, testFunctions[t].functionToProcess(points));
                }
            }

            System.out.println(names[t] + " :\n" + new MothFlameOptimizationAlt(
                            200, 2, testFunctions[t], 100000, new Bounds(-100.0, 100.0)
                    ).mfo()
            );

            CustomFileWriter.writeToFile(myPointsContainer[t], names[t] + ".dat");
        }
    }

    public static MothContainer shiftedAndRotatedBentCigar(double[] shift, double[] rotation, int maxIterations, int numberOfAgents, int dimensions) {
        MothContainer mothContainer = new MothContainer();
        MothFlameOptimizationAlt mothFlameOptimizationAlt;
        for (int i = 0; i < numberOfExecutions; i++) {
            mothFlameOptimizationAlt = new MothFlameOptimizationAlt(
                    numberOfAgents,
                    dimensions,
                    new BentCigar(true, true, 1.0, shift, rotation),
                    maxIterations,
                    bounds
            );
            mothContainer.addMoth(mothFlameOptimizationAlt.mfo());
        }
        return mothContainer;
    }

    public static MothContainer shiftedAndRotatedZakharov(double[] shift, double[] rotation, int maxIterations, int numberOfAgents, int dimensions) {
        MothContainer mothContainer = new MothContainer();
        MothFlameOptimizationAlt mothFlameOptimizationAlt;
        for (int i = 0; i < numberOfExecutions; i++) {
            mothFlameOptimizationAlt = new MothFlameOptimizationAlt(
                    numberOfAgents,
                    dimensions,
                    new Zakharov(true, true, 1.0, shift, rotation),
                    maxIterations,
                    bounds
            );
            mothContainer.addMoth(mothFlameOptimizationAlt.mfo());
        }
        return mothContainer;
    }

    public static MothContainer shiftedAndRotatedRastrigin(double[] shift, double[] rotation, int maxIterations, int numberOfAgents, int dimensions) {
        MothContainer mothContainer = new MothContainer();
        MothFlameOptimizationAlt mothFlameOptimizationAlt;
        for (int i = 0; i < numberOfExecutions; i++) {
            mothFlameOptimizationAlt = new MothFlameOptimizationAlt(
                    numberOfAgents,
                    dimensions,
                    new Rastrigin(true, true, 1.0, shift, rotation),
                    maxIterations,
                    bounds
            );
            mothContainer.addMoth(mothFlameOptimizationAlt.mfo());
        }
        return mothContainer;
    }

    public static MothContainer shiftedAndRotatedRosenbrock(double[] shift, double[] rotation, int maxIterations, int numberOfAgents, int dimensions) {
        MothContainer mothContainer = new MothContainer();
        MothFlameOptimizationAlt mothFlameOptimizationAlt;
        for (int i = 0; i < numberOfExecutions; i++) {
            mothFlameOptimizationAlt = new MothFlameOptimizationAlt(
                    numberOfAgents,
                    dimensions,
                    new Rosenbrock(true, true, 1.0, shift, rotation),
                    maxIterations,
                    bounds
            );
            mothContainer.addMoth(mothFlameOptimizationAlt.mfo());
        }
        return mothContainer;
    }

    public static void main(String[] args) {

        int dimensions = 10;
        int maxIterations = 100000;

        //initialTests();

        double[] shiftedBentCigar = new CustomFileReader().readFile(dimensions, "./input_data/shift_data_1.txt");
        double[] shiftedZakharov = new CustomFileReader().readFile(dimensions, "./input_data/shift_data_3.txt");
        double[] shiftedRosenbrock = new CustomFileReader().readFile(dimensions, "./input_data/shift_data_4.txt");
        double[] shiftedRastrigin = new CustomFileReader().readFile(dimensions, "./input_data/shift_data_5.txt");

        double[] rotatedBentCigar = new CustomFileReader().readFile(dimensions * dimensions, "input_data/M_1_D10.txt");
        double[] rotatedZakharov = new CustomFileReader().readFile(dimensions * dimensions, "input_data/M_3_D10.txt");
        double[] rotatedRosenbrock = new CustomFileReader().readFile(dimensions * dimensions, "input_data/M_4_D10.txt");
        double[] rotatedRastrigin = new CustomFileReader().readFile(dimensions * dimensions, "input_data/M_5_D10.txt");


        //Shifted and Rotated Bent Cigar
        System.out.println("Calculating SaR Bent Cigar");
        for (int i = 0; i < numberOfAgents.length; i++) {
            System.out.println("Number of moths :" + numberOfAgents[i]);
            CustomFileWriter.writeToFile(
                    shiftedAndRotatedBentCigar(shiftedBentCigar, rotatedBentCigar, maxIterations, numberOfAgents[i], dimensions),
                    "sarBentCigar_agents" + numberOfAgents[i] + ".dat"
            );
            System.out.println("\n");
        }
        System.out.println("Finished calculating SaR Bent Cigar\n");


        //Shifted and Rotated Zakharov
        System.out.println("Calculating SaR Zakharov");
        for (int i = 0; i < numberOfAgents.length; i++) {
            System.out.println("Number of moths :" + numberOfAgents[i]);
            CustomFileWriter.writeToFile(
                    shiftedAndRotatedZakharov(shiftedZakharov, rotatedZakharov, maxIterations, numberOfAgents[i], dimensions),
                    "sarZakharov_agents" + numberOfAgents[i] + ".dat"
            );
            System.out.println("\n");
        }
        System.out.println("Finished calculating SaR Zakharov\n");


        //Shifted and Rotated Rosenbrock
        System.out.println("Calculating SaR Rosenbrock");
        for (int i = 0; i < numberOfAgents.length; i++) {
            System.out.println("Number of moths :" + numberOfAgents[i]);
            CustomFileWriter.writeToFile(
                    shiftedAndRotatedRosenbrock(shiftedRosenbrock, rotatedRosenbrock, maxIterations, numberOfAgents[i], dimensions),
                    "sarRosenbrock_agents" + numberOfAgents[i] + ".dat"
            );

            System.out.println("\n");
        }
        System.out.println("Finished calculating SaR Rosenbrock\n");


        //Shifted and Rotated Rastrigin
        System.out.println("Calculating SaR Rastrigin");
        for (int i = 0; i < numberOfAgents.length; i++) {
            System.out.println("Number of moths :" + numberOfAgents[i]);
            CustomFileWriter.writeToFile(
                    shiftedAndRotatedRastrigin(shiftedRastrigin, rotatedRastrigin, maxIterations, numberOfAgents[i], dimensions),
                    "sarRastrigin_agents" + numberOfAgents[i] + ".dat"
            );

            System.out.println("\n");
        }
        System.out.println("Finished calculating SaR Rastrigin");

        System.out.println("hello");
    }
}

