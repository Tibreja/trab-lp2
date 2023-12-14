import java.io.Serializable;
import java.util.List;

public class PortCrane implements Serializable {
    private PortSystem portSystem;

    public PortCrane(PortSystem portSystem) {
        this.portSystem = portSystem;
    }

    public boolean moveContainer(String containerId, String newPosition) {
        Container container = portSystem.getContainerById(containerId);
        if (container == null) {
            System.out.println("Contêiner não encontrado.");
            return false;
        }

        ContainerStack currentStack = null;
        for (ContainerStack stack : portSystem.getYard().getStacks()) {
            if (stack.getContainers().contains(container)) {
                currentStack = stack;
                break;
            }
        }

        if (currentStack == null) {
            System.out.println("Contêiner não encontrado na posição atual.");
            return false;
        }

        List<Container> containersInCurrentStack = currentStack.getContainers();
        if (!containersInCurrentStack.isEmpty()) {
            Container lastContainerInCurrentStack = containersInCurrentStack.get(containersInCurrentStack.size() - 1);
            if (!lastContainerInCurrentStack.getId().equals(container.getId())) {
                System.out.println("Somente o contêiner no topo da pilha pode ser movido.");
                return false;
            }
        }

        if (newPosition.length() != 1 || newPosition.charAt(0) < 'A' || newPosition.charAt(0) > 'J') {
            System.out.println("Posição de empilhamento inválida.");
            return false;
        }

        // Verificar se a posição de destino está cheia
        ContainerStack targetStack = null;
        for (ContainerStack stack : portSystem.getYard().getStacks()) {
            if (stack.getPosition().equals(newPosition)) {
                targetStack = stack;
                break;
            }
        }

        if (targetStack.isFull()) {
            System.out.println("A posição de empilhamento de destino está cheia.");
            return false;
        }

        // Mover o contêiner
        currentStack.pushContainer(container);
        targetStack.pushContainer(container);
        return true;
    }
}
