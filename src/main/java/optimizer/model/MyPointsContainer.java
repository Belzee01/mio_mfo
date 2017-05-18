package optimizer.model;

import lombok.Data;

import java.util.List;

@Data
public class MyPointsContainer {
    private List<MyPoint> myPoints;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        myPoints.forEach(p -> {
            sb.append(p).append("\n");
        });

        return sb.toString();
    }
}
