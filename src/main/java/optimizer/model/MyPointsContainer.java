package optimizer.model;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyPointsContainer {
    private List<MyPoint> myPoints;
    @Setter
    private int dataPass;

    public MyPointsContainer(int dataPass) {
        this.myPoints = new ArrayList<>();
        this.dataPass = dataPass;
    }

    public void addNewPoint(Double x, Double y, Double z) {
        myPoints.add(new MyPoint(x, y, z));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        final int[] k = {0};
        myPoints.forEach(m -> {
            sb.append(m);
            k[0]++;
            if (k[0] %dataPass == 0.0) {
                sb.append("\n");
            }
        });

        return sb.toString();
    }
}
