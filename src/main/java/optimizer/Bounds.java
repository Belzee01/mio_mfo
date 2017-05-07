package optimizer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bounds {

    private Double lowerBound;
    private Double upperBound;

}
