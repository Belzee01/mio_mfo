package optimizer;

import Jama.Matrix;

import java.util.Arrays;

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

    private Matrix flamesPositios;
    private Matrix flamesFitness;

    public MothFlameOptimization(TestFunction testFunction, Bounds bounds, int maxNumberOfIterations, int numberOfMoths, int dimensions) {
        this.testFunction = testFunction;
        this.bounds = bounds;
        this.maxNumberOfIterations = maxNumberOfIterations;

        this.dimensions = dimensions;
        this.numberOfMoths = numberOfMoths;
        this.currentIteration = 0;

        this.mothsPositions = new Matrix(numberOfMoths, dimensions);
        this.mothsFitness = new Matrix(numberOfMoths, 1);

        this.flamesPositios = new Matrix(numberOfMoths, dimensions);
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

    public void mfo() {
        Matrix previousMothsFitness = new Matrix(numberOfMoths, 1);
        Matrix previousMothsPositions = new Matrix(numberOfMoths, dimensions);

        evaluateNewPositions(previousMothsPositions);
        evaluateFitness(previousMothsFitness, this.mothsPositions);

        while (this.currentIteration < this.maxNumberOfIterations) {
            this.evaluateNumberOfFlames();
            this.checkAndUpdateBoundaryConditions();

            this.evaluateFitness(this.mothsFitness, this.mothsPositions);

            if (this.currentIteration == 1) {

            } else {

            }

            this.currentIteration++;
        }
    }
}

