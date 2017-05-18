package optimizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyPoint {
    private Double x;
    private Double y;
    private Double z;

    @Override
    public String toString() {
        return x + "\t" + y + "\t" + z + "\n";
    }
}

