import file.CustomFileReader;
import file.CustomFileWriter;
import optimizer.Bounds;
import optimizer.MothFlameOptimizationAlt;
import optimizer.TestFunction;
import optimizer.TestFunctionBenchmark.BentCigar;
import optimizer.TestFunctionBenchmark.Rastrigin;
import optimizer.TestFunctionBenchmark.Rosenbrock;
import optimizer.TestFunctionBenchmark.Zakharov;
import optimizer.model.MyPointsContainer;

public class Main {

    private static int[] numberOfAgents = {10, 100, 1000};

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

    public static void shiftedAndRotatedBentCigar(double[] shift, double[] rotation, int maxIterations, int numberOfAgents, int dimensions) {

    }

    public static void shiftedAndRotatedZakharov(double[] shift, double[] rotation, int maxIterations, int numberOfAgents, int dimensions) {

    }

    public static void shiftedAndRotatedRastrigin(double[] shift, double[] rotation, int maxIterations, int numberOfAgents, int dimensions) {

    }

    public static void shiftedAndRotatedRosenbrock(double[] shift, double[] rotation, int maxIterations, int numberOfAgents, int dimensions) {

    }

    public static void main(String[] args) {

        int dimensions = 10;
        int maxIterations = 100000;

        initialTests();

        double[] shiftedBentCigar = CustomFileReader.readFile(dimensions, "input_data/shifted_data_1.txt");
        double[] shiftedZakharov = CustomFileReader.readFile(dimensions, "input_data/shifted_data_3.txt");
        double[] shiftedRosenbrock = CustomFileReader.readFile(dimensions, "input_data/shifted_data_4.txt");
        double[] shiftedRastrigin = CustomFileReader.readFile(dimensions, "input_data/shifted_data_5.txt");

        double[] rotatedBentCigar = CustomFileReader.readFile(dimensions * dimensions, "input_data/M_1_D10.txt");
        double[] rotatedZakharov = CustomFileReader.readFile(dimensions * dimensions, "input_data/M_3_D10.txt");
        double[] rotatedRosenbrock = CustomFileReader.readFile(dimensions * dimensions, "input_data/M_4_D10.txt");
        double[] rotatedRastrigin = CustomFileReader.readFile(dimensions * dimensions, "input_data/M_5_D10.txt");


        //Shifted and Rotated Bent Cigar
        System.out.println("Calculating SaR Bent Cigar");
        for (int i = 0; i < numberOfAgents.length; i++) {
            shiftedAndRotatedBentCigar(shiftedBentCigar, rotatedBentCigar, maxIterations, numberOfAgents[i], dimensions);
        }
        System.out.println("Finished calculating SaR Bent Cigar");


        //Shifted and Rotated Zakharov
        System.out.println("Calculating SaR Zakharov");
        for (int i = 0; i < numberOfAgents.length; i++) {
            shiftedAndRotatedZakharov(shiftedZakharov, rotatedZakharov, maxIterations, numberOfAgents[i], dimensions);
        }
        System.out.println("Calculating SaR Zakharov");


        //Shifted and Rotated Rosenbrock
        System.out.println("Calculating SaR Rosenbrock");
        for (int i = 0; i < numberOfAgents.length; i++) {
            shiftedAndRotatedRosenbrock(shiftedRosenbrock, rotatedRosenbrock, maxIterations, numberOfAgents[i], dimensions);
        }
        System.out.println("Calculating SaR Rosenbrock");


        //Shifted and Rotated Rastrigin
        System.out.println("Calculating SaR Rastrigin");
        for (int i = 0; i < numberOfAgents.length; i++) {
            shiftedAndRotatedRastrigin(shiftedRastrigin, rotatedRastrigin, maxIterations, numberOfAgents[i], dimensions);
        }
        System.out.println("Calculating SaR Rastrigin");

        System.out.println("hello");
    }
}

