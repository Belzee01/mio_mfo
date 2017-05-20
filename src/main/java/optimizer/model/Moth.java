package optimizer.model;

import lombok.Data;
import optimizer.TestFunction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@Data
public class Moth implements Comparable<Moth>{

    private double[] position;

    private Double fitnessValue;

    public Moth(int dimensions) {
        this.position = new double[dimensions];
        this.fitnessValue = 0.0;
    }

    public void calculateFitness(TestFunction function) {
        this.fitnessValue = function.functionToProcess(this.position);
    }

    public void setSpecificPositionPoint(int index, double value) {
        this.position[index] = value;
    }

    @Override
    public int compareTo(Moth o) {
        Double fitness = o.getFitnessValue();
        if (this.fitnessValue < fitness)
            return -1;
        if (this.fitnessValue > fitness)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(this.position).forEach(p -> {
            sb.append(p).append("\t");
        });

        return sb.toString();
    }

    public static Comparator<Moth> MothFitnessComparator = Moth::compareTo;
}
