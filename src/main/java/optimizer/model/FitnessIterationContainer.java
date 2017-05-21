package optimizer.model;

import java.util.ArrayList;
import java.util.List;

public class FitnessIterationContainer {

    private List<Double> value;

    public FitnessIterationContainer() {
        this.value = new ArrayList<>();
    }

    public void addNewFitness(Double fitnessValue) {
        this.value.add(fitnessValue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < value.size()+1; i++) {
            sb.append(i).append("\t").append(value.get(i-1)).append("\n");
        }

        return sb.toString();
    }

}
