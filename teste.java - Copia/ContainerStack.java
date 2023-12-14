import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContainerStack implements Serializable {
    private String position;
    private List<Container> containers;
    

    public ContainerStack(String position) {
        this.position = position;
        this.containers = new ArrayList<>();
    }

    public boolean pushContainer(Container container) {
        boolean verifier;

        if (this.containers.size() < 5) {
            this.containers.add(container);
            verifier = true;
        } else {
            verifier = false;
        }
        return verifier;
    }

    public boolean popContainer(String containerId) {
        boolean verifier;
        int containerIndex = -1;

        for (int i = 0; i < this.containers.size(); i++) {
            if (this.containers.get(i).getId().equals(containerId)) {
                containerIndex = i;
                break;
            }
        }

        if (containerIndex != -1) {
            Container removedContainer = this.containers.remove(containerIndex);
            System.out.println();
            System.out.println("Contêiner " + removedContainer.getId() + " excluído com sucesso.");
            verifier = true;
        } else {
            verifier = false;
        }
        return verifier;
    }

    public Container getTopContainer() {
        return this.containers.size() > 0 ? this.containers.get(this.containers.size() - 1) : null;
    }

    public boolean isEmpty() {
        return this.containers.isEmpty();
    }

    public boolean isFull() {
        return this.containers.size() == 5;
    }

    public List<Container> getContainers() {
        return null;
    }

    public Object getPosition() {
        return null;
    }


}

