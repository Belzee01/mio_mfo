package optimizer;

import Jama.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Matrix mothsFitness;

    private Matrix flamesPositions;
    private Matrix flamesFitness;

    private Matrix sortedMothsPositions;

    public MothFlameOptimization(TestFunction testFunction, Bounds bounds, int maxNumberOfIterations, int numberOfMoths, int dimensions) {
        this.testFunction = testFunction;
        this.bounds = bounds;
        this.maxNumberOfIterations = maxNumberOfIterations;

        this.dimensions = dimensions;
        this.numberOfMoths = numberOfMoths;
        this.currentIteration = 0;

        this.mothsPositions = new Matrix(numberOfMoths, dimensions);
        this.sortedMothsPositions = new Matrix(numberOfMoths, dimensions);
        this.mothsFitness = new Matrix(numberOfMoths, 1);

        this.flamesPositions = new Matrix(numberOfMoths, dimensions);
        this.flamesFitness = new Matrix(numberOfMoths, 1);

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

    private void evaluateFitness(Matrix matrix, Matrix toEvaluation) {
        for (int i = 0; i < numberOfMoths; i++) {
            matrix.set(i, 0, testFunction.functionToProcess(toEvaluation.getArray()[i]));
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
     *
     * @param matrix
     * @return Returns sorted fitness in first column and indexes from old fitness matrix in second column
     */
    private Matrix sortFitness(Matrix matrix) {
        ArrayList<Double> fitnessCopy = DoubleStream.of(matrix.getArray()[0]).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Double> oldFitness = new ArrayList<>(fitnessCopy);
        Collections.sort(fitnessCopy);

        ArrayList<Integer> indexes = new ArrayList<>();
        for (Double aFitnessCopy : fitnessCopy) {
            indexes.add(oldFitness.indexOf(aFitnessCopy));
        }

        Matrix out = new Matrix(fitnessCopy.size(), 2);
        for (int i = 0; i < fitnessCopy.size(); i++) {
            out.set(i, 0, fitnessCopy.get(i));
            out.set(i, 1, indexes.get(i));
        }
        return out;
    }

    public void mfo() {
        Matrix previousMothsFitness = new Matrix(numberOfMoths, 1);
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
                Matrix sortedFitnessAndIndexes = this.sortFitness(this.mothsFitness);
                for (int i = 0; i < numberOfMoths; i++) {
                    fitnessSorted[i] = sortedFitnessAndIndexes.get(0, i);
                    indexes[i] = sortedFitnessAndIndexes.get(1, i);
                }
                for (int i = 0; i < numberOfMoths; i++) {
                    double[] temp = new double[dimensions];
                    System.arraycopy(mothsPositions.getArray()[(int) indexes[i]], 0, temp, 0, dimensions);
                    for (int j = 0; i < dimensions; j++) {
                        this.sortedMothsPositions.set(i, j, temp[j]);
                    }
                }
                for (int i = 0; i < numberOfMoths; i++) {
                    System.arraycopy(sorted_population[i], 0, best_flames[i], 0, D);
                    best_flame_fitness[i] = fitness_sorted[i];
                }
            } else {

            }

            this.currentIteration++;
        }
    }
}

