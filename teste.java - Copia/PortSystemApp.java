import java.util.Scanner;

public class PortSystemApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PortSystem portSystem = new PortSystem();
    private static final PortCrane portCrane = new PortCrane(portSystem);

    public static void main(String[] args) {
        displayMenu();
        processInput();
    }

    private static void processInput() {
        System.out.print("Escolha uma ação (1/2/3/4/5/6/7/8/9/10): ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                insertContainer();
                break;
            case 2:
                moveContainer();
                break;
            case 3:
                queryContainerData();
                break;
            case 4:
                queryTopContainerAtPosition();
                break;
            case 5:
                queryContainersByCargoType();
                break;
            case 6:
                queryContainersByOperationType();
                break;
            case 7:
                queryTotalWeightByCargoType();
                break;
            case 8:
                queryEmptyPositions();
                break;
            case 9:
                deleteContainer();
                break;
            case 10:
                scanner.close();
                break;
            default:
                System.out.println("Escolha inválida. Tente novamente.");
                break;
        }
    }

    private static void deleteContainer() {
    }

    private static void queryEmptyPositions() {
    }

    private static void queryTotalWeightByCargoType() {
    }

    private static void queryContainersByOperationType() {
    }

    private static void queryContainersByCargoType() {
    }

    private static void queryTopContainerAtPosition() {
    }

    private static void queryContainerData() {
    }

    private static void moveContainer() {
        System.out.print("ID do contêiner a ser movido: ");
        String containerId = scanner.nextLine();

        System.out.print("Nova posição (A até J): ");
        String newPosition = scanner.nextLine().toUpperCase();

        if (portCrane.moveContainer(containerId, newPosition)) {
            System.out.println("Contêiner movido com sucesso.");
        } else {
            System.out.println("Falha ao mover o contêiner.");
        }

        displayMenu();
        processInput();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("Selecione uma ação:");
        System.out.println("1. Inserir contêiner");
        System.out.println("2. Mover contêiner");
        System.out.println("3. Consultar dados de contêiner por ID");
        System.out.println("4. Consultar contêiner no topo de uma posição (A até J)");
        System.out.println("5. Consultar quantidade de contêineres por tipo de carga");
        System.out.println("6. Consultar quantidade de contêineres por tipo de operação");
        System.out.println("7. Consultar peso total por tipo de carga");
        System.out.println("8. Consultar posições vazias");
        System.out.println("9. Excluir contêiner");
        System.out.println("10. Sair");
    }

    private static void insertContainer() {
        System.out.print("Nome do proprietário: ");
        String owner = scanner.nextLine();

        System.out.println();
        System.out.println("Selecione o tipo de carga:");
        System.out.println("1. Carga seca");
        System.out.println("2. Commodities");
        System.out.println("3. Produtos perigosos");
        System.out.println("4. Produtos perecíveis");

        String[] validCargoTypes = {"Carga seca", "Commodities", "Produtos perigosos", "Produtos perecíveis"};

        System.out.print("Escolha uma opção (1/2/3/4): ");
        int cargoTypeChoice = Integer.parseInt(scanner.nextLine()) - 1;
        if (cargoTypeChoice >= 0 && cargoTypeChoice < validCargoTypes.length) {
            String cargoType = validCargoTypes[cargoTypeChoice];
            selectOperationType(owner, cargoType);
        } else {
            System.out.println("Opção de tipo de carga inválida. Tente novamente.");
            insertContainer();
        }
    }

    private static void selectOperationType(String owner, String cargoType) {
        System.out.println();
        System.out.println("Selecione o tipo de operação:");
        System.out.println("1. Embarque");
        System.out.println("2. Desembarque");

        System.out.print("Escolha uma opção (1/2): ");
        int operationTypeChoice = Integer.parseInt(scanner.nextLine()) - 1;
        if (operationTypeChoice == 0 || operationTypeChoice == 1) {
            String operationType = (operationTypeChoice == 0) ? "Embarque" : "Desembarque";
            askForWeight(owner, cargoType, operationType);
        } else {
            System.out.println("Opção de tipo de operação inválida. Tente novamente.");
            selectOperationType(owner, cargoType);
        }
    }

    private static void askForWeight(String owner, String cargoType, String operationType) {
        System.out.print("Peso da carga (em kg): ");
        double weight = Double.parseDouble(scanner.nextLine());

        System.out.print("Posição de empilhamento (A até J): ");
        String position = scanner.nextLine().toUpperCase();
        ContainerStack stack = portSystem.getYard().getStackByPosition(position);
        if (stack != null) {
            if (stack.isFull()) {
                System.out.println("A posição de empilhamento está cheia. O contêiner não foi inserido.");
            } else {
                String containerId = generateContainerId(position);
                Container container = new Container(containerId, owner, cargoType, weight, operationType);
                stack.pushContainer(container);
                System.out.println("Contêiner " + containerId + " inserido na posição " + position);
            }
        } else {
            System.out.println("Posição de empilhamento inválida. O contêiner não foi inserido.");
        }
        displayMenu();
        processInput();
    }

    private static String generateContainerId(String position) {
        ContainerStack stack = portSystem.getYard().getStackByPosition(position);
        if (stack != null) {
            int orderNumber = stack.getContainers().size() + 1;
            return position + "." + orderNumber;
        } else {
            return "N/A";
        }
    }

    

}