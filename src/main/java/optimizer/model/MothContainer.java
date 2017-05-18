package optimizer.model;

import java.util.ArrayList;
import java.util.List;

public class MothContainer {

    private List<Moth> moths;

    public MothContainer() {
        this.moths = new ArrayList<>();
    }

    public void addMoth(Moth moth) {
        this.moths.add(moth);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        moths.forEach(m -> {
            sb.append(m).append("\n");
        });
        return sb.toString();
    }
}
