package optimizer;

import Jama.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class MothFlameOptimization {

    private TestFunction testFunction;
    private Bounds bounds;
    private int maxNumberOfIterations;

    private int numberOfMoths;
    private int dimensions;
    private int numberOfFlames;
    private int currentIteration;

    private Matrix mothsPositions;
    private double[] mothsFitness;

    private Matrix sortedMothsPositions;
    private Matrix sortedFlamesPositions;
    private double[] bestFlameFitness;

    private double flameBestScore;
    private double[] flameBestPosition;

    public MothFlameOptimization(TestFunction testFunction, Bounds bounds, int maxNumberOfIterations, int numberOfMoths, int dimensions) {
        this.testFunction = testFunction;
        this.bounds = bounds;
        this.maxNumberOfIterations = maxNumberOfIterations;

        this.dimensions = dimensions;
        this.numberOfMoths = numberOfMoths;
        this.currentIteration = 0;

        this.mothsPositions = new Matrix(numberOfMoths, dimensions);
        this.sortedMothsPositions = new Matrix(numberOfMoths, dimensions);
        this.mothsFitness = new double[numberOfMoths];

        this.bestFlameFitness = new double[numberOfMoths];
        this.sortedFlamesPositions = new Matrix(numberOfMoths, dimensions);

        this.flameBestPosition = new double[numberOfMoths];

        this.initialization();
    }

    private void initialization() {
        evaluateNewPositions(this.mothsPositions);
        evaluateFitness(this.mothsFitness, this.mothsPositions);
    }

    private void evaluateNewPositions(Matrix matrix) {
        for (int i = 0; i < numberOfMoths; i++) {
            for (int j = 0; j < dimensions; j++) {
                double value = (bounds.getUpperBound() - bounds.getLowerBound()) * Math.random() + bounds.getLowerBound();
                matrix.set(i, j, value);
            }
        }
    }

    private void evaluateFitness(double[] matrix, Matrix toEvaluation) {
        for (int i = 0; i < numberOfMoths; i++) {
            matrix[i] = testFunction.functionToProcess(toEvaluation.getArray()[i]);
        }
    }

    private void evaluateNumberOfFlames() {
        this.numberOfFlames = Math.round(numberOfMoths - this.currentIteration * ((numberOfMoths - 1) / maxNumberOfIterations));
    }

    private void checkAndUpdateBoundaryConditions() {
        for (int i = 0; i < numberOfMoths; i++) {
            for (int j = 0; j < dimensions; j++) {
                if ((mothsPositions.get(i, j) < bounds.getLowerBound()) || (mothsPositions.get(i, j) > bounds.getUpperBound())) {
                    double value = bounds.getLowerBound() + ((bounds.getUpperBound() - bounds.getLowerBound()) * Math.random());
                    mothsPositions.set(i, j, value);
                }
            }
        }
    }

    /**
     * @param matrix
     * @return Returns sorted fitness in first column and indexes from old fitness matrix in second column
     */
    private double[][] sortFitness(double[] matrix) {
        ArrayList<Double> fitnessCopy = DoubleStream.of(matrix).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Double> oldFitness = new ArrayList<>(fitnessCopy);
        Collections.sort(fitnessCopy);

        ArrayList<Integer> indexes = new ArrayList<>();
        for (Double aFitnessCopy : fitnessCopy) {
            indexes.add(oldFitness.indexOf(aFitnessCopy));
        }

        double[][] out = new double[2][fitnessCopy.size()];
        for (int i = 0; i < fitnessCopy.size(); i++) {
            out[0][i] = fitnessCopy.get(i);
            out[1][i] = indexes.get(i);
        }
        return out;
    }

    public double[][] mfo() {
        double a = 0.0;

        double[] previousMothsFitness = new double[numberOfMoths];
        Matrix previousMothsPositions = new Matrix(numberOfMoths, dimensions);

        evaluateNewPositions(previousMothsPositions);
        evaluateFitness(previousMothsFitness, this.mothsPositions);

        double[] fitnessSorted = new double[numberOfMoths];
        double[] indexes = new double[numberOfMoths];

        while (this.currentIteration < this.maxNumberOfIterations) {
            this.evaluateNumberOfFlames();
            this.checkAndUpdateBoundaryConditions();

            this.evaluateFitness(this.mothsFitness, this.mothsPositions);

            if (this.currentIteration == 1) {

                double[][] sortedFitnessAndIndexes = this.sortFitness(this.mothsFitness);
                for (int i = 0; i < numberOfMoths; i++) {
                    fitnessSorted[i] = sortedFitnessAndIndexes[0][i];
                    indexes[i] = sortedFitnessAndIndexes[1][i];
                }
                for (int i = 0; i < numberOfMoths; i++) {
                    double[] temp = new double[dimensions];
                    System.arraycopy(mothsPositions.getArray()[(int) indexes[i]], 0, temp, 0, dimensions);
                    for (int j = 0; j < dimensions; j++) {
                        this.sortedMothsPositions.set(i, j, temp[j]);
                    }
                }
                for (int i = 0; i < numberOfMoths; i++) {
                    double[] temp = new double[dimensions];
                    System.arraycopy(this.sortedMothsPositions.getArray()[i], 0, temp, 0, dimensions);
                    for (int j = 0; j < dimensions; j++) {
                        this.sortedFlamesPositions.set(i, j, temp[j]);
                    }
                    this.bestFlameFitness[i] = fitnessSorted[i];
                }
            } else {

                Matrix twiceSizePopulation = new Matrix(2 * numberOfMoths, dimensions);
                double[] twiceSizeFitness = new double[2 * numberOfMoths];
                for (int i = 0; i < numberOfMoths; i++) {
                    double[] temp = new double[dimensions];
                    System.arraycopy(previousMothsPositions.getArray()[i], 0, temp, 0, dimensions);
                    for (int j = 0; j < dimensions; j++) {
                        twiceSizePopulation.set(i, j, temp[j]);
                    }
                }

                for (int i = numberOfMoths; i < 2 * numberOfMoths; i++) {
                    double[] temp = new double[dimensions];
                    System.arraycopy(sortedFlamesPositions.getArray()[i - numberOfMoths], 0, temp, 0, dimensions);
                    for (int j = 0; j < dimensions; j++) {
                        twiceSizePopulation.set(i, j, temp[j]);
                    }
                }

                System.arraycopy(previousMothsFitness, 0, twiceSizeFitness, 0, numberOfMoths);
                System.arraycopy(bestFlameFitness, 0, twiceSizeFitness, numberOfMoths, 2 * numberOfMoths - numberOfMoths);

                double[][] sortedFitnessAndIndexes = this.sortFitness(twiceSizeFitness);
                double[] twiceSizeFitnessSorted = new double[2 * numberOfMoths];
                double[] twiceSizeIndexes = new double[2 * numberOfMoths];
                for (int i = 0; i < 2 * numberOfMoths; i++) {
                    twiceSizeFitnessSorted[i] = sortedFitnessAndIndexes[0][i];
                    twiceSizeIndexes[i] = sortedFitnessAndIndexes[1][i];
                }

                Matrix twiceSizeSortedPopulation = new Matrix(2 * numberOfMoths, dimensions);
                for (int i = 0; i < 2 * numberOfMoths; i++) {
                    double[] temp = new double[dimensions];
                    System.arraycopy(twiceSizePopulation.getArray()[(int) twiceSizeIndexes[i]], 0, temp, 0, dimensions);
                    for (int j = 0; j < dimensions; j++) {
                        twiceSizeSortedPopulation.set(i, j, temp[j]);
                    }
                }

                System.arraycopy(twiceSizeFitnessSorted, 0, fitnessSorted, 0, numberOfMoths);
                for (int i = 0; i < numberOfMoths; i++) {
                    double[] temp = new double[dimensions];
                    System.arraycopy(twiceSizeSortedPopulation.getArray()[i], 0, temp, 0, dimensions);
                    for (int j = 0; j < dimensions; j++) {
                        this.sortedMothsPositions.set(i, j, temp[j]);
                    }
                }

                for (int i = 0; i < numberOfMoths; i++) {
                    double[] temp = new double[dimensions];
                    System.arraycopy(sortedMothsPositions.getArray()[i], 0, temp, 0, dimensions);
                    for (int j = 0; j < dimensions; j++) {
                        this.sortedFlamesPositions.set(i, j, temp[j]);
                    }
                    bestFlameFitness[i] = fitnessSorted[i];
                }
            }

            this.flameBestScore = fitnessSorted[0];

            for (int i = 0; i < dimensions; i++) {
                this.flameBestPosition[i] = sortedMothsPositions.get(0, i);
            }

            for (int i = 0; i < numberOfMoths; i++) {
                double[] temp = new double[numberOfMoths];
                System.arraycopy(mothsPositions.getArrayCopy()[i], 0, temp, 0, dimensions);
                for (int j = 0; j < dimensions; j++) {
                    previousMothsPositions.set(i, j, temp[j]);
                }
                previousMothsFitness[i] = mothsFitness[i];
            }

            a = -1.0 + (double) currentIteration * (-1.0 / (double) maxNumberOfIterations);

            double b = 1.0;
            double t = 0.0;
            double mothDistanceToFlame = 0.0;
            for (int i = 0; i < numberOfMoths; i++) {
                for (int j = 0; j < dimensions; j++) {
                    if (i <= this.numberOfFlames) {
                        mothDistanceToFlame = Math.abs(sortedMothsPositions.get(i, j) - mothsPositions.get(i, j));
                        t = (a - 1.0) * Math.random() + 1.0;
                        mothsPositions.set(i, j, mothDistanceToFlame * Math.exp(b * t) * Math.cos(t * 2.0 * Math.PI) + sortedMothsPositions.get(i, j));
                    }

                    if (i > this.numberOfFlames) {
                        mothDistanceToFlame = Math.abs(sortedMothsPositions.get(i, j) - mothsPositions.get(i, j));
                        t = (a - 1.0) * Math.random() + 1.0;
                        mothsPositions.set(i, j, mothDistanceToFlame * Math.exp(b * t) * Math.cos(t * 2.0 * Math.PI) + sortedMothsPositions.get(this.numberOfFlames, j));
                    }
                }
            }
            this.currentIteration++;
        }

        double[][] out = new double[2][dimensions];
        out[0][0] = flameBestScore;
        System.arraycopy(flameBestPosition, 0, out[1], 0, dimensions);

        return out;
    }
}

