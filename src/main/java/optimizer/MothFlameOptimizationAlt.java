package optimizer;

import lombok.Getter;
import optimizer.model.Moth;
import optimizer.model.MothContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MothFlameOptimizationAlt {

    private List<Moth> moths;
    private int numberOfMoths;
    private int dimensions;

    private TestFunction testFunction;

    private int currentIteration;
    private int numberOfFlames;
    private int maxNumberOfIterations;

    private Bounds bounds;
    private List<Moth> sortedFlames;

    private List<Moth> previousAndSortedPopulation;
    @Getter
    private List<Moth> sortedPopulation;

    private Double bestFlameFitness;

    @Getter
    private Moth minimum;

    private MothContainer fitnessByIteration;

    public MothFlameOptimizationAlt(int numberOfMoths, int dimensions, TestFunction testFunction, int maxNumberOfIterations, Bounds bounds) {
        this.moths = new ArrayList<>(numberOfMoths);
        this.numberOfMoths = numberOfMoths;
        this.numberOfFlames = numberOfMoths;
        this.dimensions = dimensions;
        this.sortedFlames = new ArrayList<>();
        this.previousAndSortedPopulation = new ArrayList<>();
        this.sortedPopulation = new ArrayList<>();

        this.testFunction = testFunction;
        this.currentIteration = 0;
        this.maxNumberOfIterations = maxNumberOfIterations;
        this.bounds = bounds;
        this.fitnessByIteration = new MothContainer();

        initialization();
    }

    private void initialization() {
        fillMothArray();
        evaluateNewPositions(this.moths);
        evaluateFitness(this.moths);
    }

    private void fillMothArray() {
        for (int i = 0; i < numberOfMoths; i++) {
            this.moths.add(new Moth(this.dimensions));
            this.sortedFlames.add(new Moth(this.dimensions));
        }

        for (int i = 0; i < 2 * numberOfMoths; i++) {
            this.previousAndSortedPopulation.add(new Moth(dimensions));
        }
    }

    private void evaluateNewPositions(List<Moth> moths) {
        moths.forEach(m -> {
            for (int j = 0; j < dimensions; j++) {
                double value = (bounds.getUpperBound() - bounds.getLowerBound()) * Math.random() + bounds.getLowerBound();
                m.setSpecificPositionPoint(j, value);
            }
        });
    }

    private void evaluateFitness(List<Moth> moths) {
        moths.forEach(m -> m.calculateFitness(this.testFunction));
    }

    private void evaluateNumberOfFlames() {
        this.numberOfFlames = Math.round(numberOfMoths - this.currentIteration * ((numberOfMoths - 1) / maxNumberOfIterations));
    }

    private void checkAndUpdateBoundaryConditions() {
        this.moths.forEach(m -> {
            for (int j = 0; j < dimensions; j++) {
                if ((m.getPosition()[j] < bounds.getLowerBound()) || (m.getPosition()[j] > bounds.getUpperBound())) {
                    double value = bounds.getLowerBound() + ((bounds.getUpperBound() - bounds.getLowerBound()) * Math.random());
                    m.setSpecificPositionPoint(j, value);
                }
            }
        });
    }

    private List<Moth> sortMoths(List<Moth> moths) {
        Collections.sort(moths);
        return moths;
    }

    private void fillArray(List<Moth> moths, int size) {
        for (int i = 0; i < size; i++) {
            moths.add(new Moth(dimensions));
        }
    }

    public Moth mfo() {

        double a = 0.0;
        final double b = 1.0;
        double t = 0.0;

        List<Moth> previousMothPopulation = new ArrayList<>();
        fillArray(previousMothPopulation, numberOfMoths);
        evaluateNewPositions(previousMothPopulation);
        evaluateFitness(previousMothPopulation);

        while (this.currentIteration < this.maxNumberOfIterations) {
            evaluateNumberOfFlames();
            checkAndUpdateBoundaryConditions();

            evaluateFitness(this.moths);

            if (this.currentIteration == 1) {
                this.sortedPopulation = sortMoths(this.moths);

                for (int i = 0; i < numberOfMoths; i++) {
                    this.sortedFlames.set(i, sortedPopulation.get(i));
                }
            } else {
                for (int i = 0; i < numberOfMoths; i++) {
                    previousAndSortedPopulation.set(i, previousMothPopulation.get(i));
                }
                for (int i = numberOfMoths; i < 2 * numberOfMoths; i++) {
                    previousAndSortedPopulation.set(i, this.sortedFlames.get(i - numberOfMoths));
                }

                this.sortedPopulation = sortMoths(this.previousAndSortedPopulation);

                for (int i = 0; i < numberOfMoths; i++) {
                    this.sortedFlames.set(i, sortedPopulation.get(i));
                }
            }

            this.bestFlameFitness = this.sortedPopulation.get(0).getFitnessValue();

            for (int i = 0; i < numberOfMoths; i++) {
                previousMothPopulation.set(i, moths.get(i));
            }

            a = -1.0 + (double) currentIteration * (-1.0 / (double) maxNumberOfIterations);

            double mothDistanceToFlame = 0.0;
            for (int i = 0; i < numberOfMoths; i++) {
                for (int j = 0; j < dimensions; j++) {
                    if (i <= this.numberOfFlames) {
                        mothDistanceToFlame = Math.abs(sortedPopulation.get(i).getPosition()[j] - moths.get(i).getPosition()[j]);
                        t = (a - 1.0) * Math.random() + 1.0;
                        moths.get(i).setSpecificPositionPoint(j, mothDistanceToFlame * Math.exp(b * t) * Math.cos(t * 2.0 * Math.PI) + sortedPopulation.get(i).getPosition()[j]);
                    }

                    if (i > this.numberOfFlames) {
                        mothDistanceToFlame = Math.abs(sortedPopulation.get(i).getPosition()[j] - moths.get(i).getPosition()[j]);
                        t = (a - 1.0) * Math.random() + 1.0;
                        moths.get(i).setSpecificPositionPoint(j, mothDistanceToFlame * Math.exp(b * t) * Math.cos(t * 2.0 * Math.PI) + sortedPopulation.get(this.numberOfFlames).getPosition()[j]);
                    }
                }
            }

            this.fitnessByIteration.addMoth(this.sortedPopulation.get(0));
            this.currentIteration++;
        }

        this.minimum = this.sortedPopulation.get(0);

        return this.sortedPopulation.get(0);
    }

    public MothContainer getFitnessByIteration() {
        return fitnessByIteration;
    }
}
