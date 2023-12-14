import java.io.Serializable;

public class Container implements Serializable {
    private String id;
    private String owner;
    private String cargoType;
    private double weight;
    private String operationType;
    

    public Container(String id, String owner, String cargoType, double weight, String operationType) {
        this.id = id;
        this.owner = owner;
        this.cargoType = cargoType;
        this.weight = weight;
        this.operationType = operationType;
    }

    // Getters and setters (if needed)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", cargoType='" + cargoType + '\'' +
                ", weight=" + weight +
                ", operationType='" + operationType + '\'' +
                '}';
    }
}
