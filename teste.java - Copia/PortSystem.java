import java.io.Serializable;
import java.util.List;


public class PortSystem implements Serializable {
    private PortYard yard;

    public PortSystem() {
        this.yard = new PortYard();
    }

    public double getTotalWeightByCargoType(String cargoType) {
        double totalWeight = 0;
        for (ContainerStack stack : yard.getStacks()) {
            for (Container container : stack.getContainers()) {
                if (container.getCargoType().equals(cargoType)) {
                    totalWeight += container.getWeight();
                }
            }
        }
        return totalWeight;
    }

    public Container getContainerById(String containerId) {
        for (ContainerStack stack : yard.getStacks()) {
            for (Container container : stack.getContainers()) {
                if (container.getId().equals(containerId)) {
                    return container;
                }
            }
        }
        return null;
    }

    public Container getTopContainerAtPosition(String position) {
        ContainerStack stack = yard.getStackByPosition(position);
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        Container topContainer = stack.getTopContainer();
        return new Container(
                topContainer.getId(),
                topContainer.getOwner(),
                topContainer.getCargoType(),
                topContainer.getWeight(),
                topContainer.getOperationType()
        );
    }

    /**
     * @param cargoType
     * @return
     */
    public int getContainersByCargoType(String cargoType) {
        int count = 0;
        for (ContainerStack stack : yard.getStacks()) {
            for (Container container : stack.getContainers()) {
                if (container.getCargoType().equals(cargoType)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @param operationType
     * @return
     */
    public int getContainersByOperationType(String operationType) {
        int count = 0;
        for (ContainerStack stack : yard.getStacks()) {
            for (Container container : stack.getContainers()) {
                if (container.getOperationType().equals(operationType)) {
                    count++;
                }
            }
        }
        return count;
    }

    public List<String> getEmptyPositions() {
        return yard.getEmptyPositions();
    }

    public void setYard(PortYard yard) {
        this.yard = yard;
    }

    public PortYard getYard() {
        return this.yard;
    }
    
}

