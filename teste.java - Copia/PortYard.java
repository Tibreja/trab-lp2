import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PortYard implements Serializable {
    private List<ContainerStack> stacks;

    public PortYard() {
        String[] stackPositions = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        stacks = new ArrayList<>();

        for (String p : stackPositions) {
            ContainerStack stack = new ContainerStack(p);
            stacks.add(stack);
        }
    }

    public List<ContainerStack> getStacks() {
        return stacks;
    }

    public List<String> getEmptyPositions() {
        return null;
    }

    public ContainerStack getStackByPosition(String position) {
        return null;
    }
}

