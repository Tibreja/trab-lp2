import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        QuadraEmpilhamento quadra = carregarQuadra(); // Carrega os dados da quadra se existirem

        Scanner scanner = new Scanner(System.in);
        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    empilharConteiner(quadra, scanner);
                    break;
                case 2:
                    desempilharConteiner(quadra, scanner);
                    break;
                case 3:
                    consultarTopo(quadra, scanner);
                    break;
                case 4:
                    consultarQuantidadePorTipoCarga(quadra, scanner);
                    break;
                case 5:
                    consultarPesoPorTipoCarga(quadra, scanner);
                    break;
                case 6:
                    consultarQuantidadePorTipoOperacao(quadra, scanner);
                    break;
                case 7:
                    consultarPosicoesVazias(quadra);
                    break;
                case 8:
                    salvarQuadra(quadra);
                    break;
                case 9:
                    System.out.println("Encerrando...");
                    salvarQuadra(quadra);
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\nEscolha a operação:");
        System.out.println("1 - Empilhar contêiner");
        System.out.println("2 - Desempilhar contêiner");
        System.out.println("3 - Consultar topo da posição");
        System.out.println("4 - Quantidade de contêineres por tipo de carga");
        System.out.println("5 - Peso total de cargas por tipo de carga");
        System.out.println("6 - Quantidade de contêineres por tipo de operação");
        System.out.println("7 - Posições de empilhamento vazias");
        System.out.println("8 - Salvar dados da quadra");
        System.out.println("9 - Sair");
        System.out.println("Escolha a opção: ");
    }

    private static void empilharConteiner(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o nome do proprietário:");
        String proprietario = scanner.nextLine();
        System.out.println("Informe o tipo de carga:");
        String tipoCarga = scanner.nextLine();
        System.out.println("Informe o peso da carga:");
        double pesoCarga = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer
        System.out.println("Informe o tipo de operação (embarque/desembarque):");
        String tipoOperacao = scanner.nextLine();
        System.out.println("Informe a posição de empilhamento (A-J):");
        String posicao = scanner.nextLine().toUpperCase();

        Container container = new Container(proprietario, tipoCarga, pesoCarga, tipoOperacao);
        quadra.empilhar(container, posicao);
    }

    private static void desempilharConteiner(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o ID do contêiner a ser desempilhado:");
        String idContainer = scanner.nextLine().toUpperCase();
        Container containerRemovido = quadra.desempilhar(idContainer);
        if (containerRemovido != null) {
            // Obter a posição do contêiner removido do ID original
            String[] parts = idContainer.split("\\.");
            String posicao = parts[0];
    
            // Obtém o tamanho atual da pilha após a remoção
            int tamanhoAtual = quadra.getTamanhoPilha(posicao);
            
            // Atualiza o ID do contêiner removido com a nova posição
            String novoIDContainer = posicao + "." + (tamanhoAtual - 1); // Decrementa o tamanho da pilha
    
            System.out.println("Contêiner removido: " + containerRemovido);
            System.out.println("Novo ID do contêiner: " + novoIDContainer);
        } else {
            System.out.println("Contêiner não encontrado ou posição vazia.");
        }
    }
    
    

    private static void consultarTopo(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe a posição para consultar o topo:");
        String posConsulta = scanner.nextLine().toUpperCase();
        System.out.println("Informações do topo da posição " + posConsulta + ":");
    
        // Obtém o tamanho atual da pilha após a remoção
        int tamanhoPilha = quadra.getTamanhoPilha(posConsulta);
    
        // Atualiza o ID do topo da pilha
        String idTopo = posConsulta + "." + tamanhoPilha;
    
        // Consulta o topo usando o novo ID
        System.out.println(quadra.consultarTopo(idTopo));
    }
    
    private static void consultarQuantidadePorTipoCarga(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o tipo de carga para consultar a quantidade:");
        String tipoConsulta = scanner.nextLine();
        int quantidade = quadra.quantidadeConteineresPorTipoCarga(tipoConsulta);
        System.out.println("Quantidade de contêineres do tipo " + tipoConsulta + ": " + quantidade);
    }

    private static void consultarPesoPorTipoCarga(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o tipo de carga para consultar o peso total:");
        String tipoConsulta = scanner.nextLine();
        double pesoTotal = quadra.pesoTotalCargasPorTipo(tipoConsulta);
        System.out.println("Peso total das cargas do tipo " + tipoConsulta + ": " + pesoTotal);
    }

    private static void consultarQuantidadePorTipoOperacao(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o tipo de operação para consultar a quantidade:");
        String tipoConsulta = scanner.nextLine();
        int quantidade = quadra.quantidadeConteineresPorTipoOperacao(tipoConsulta);
        System.out.println("Quantidade de contêineres com operação " + tipoConsulta + ": " + quantidade);
    }

    private static void consultarPosicoesVazias(QuadraEmpilhamento quadra) {
        List<String> posicoesVazias = quadra.posicoesVazias();
        if (posicoesVazias.isEmpty()) {
            System.out.println("Todas as posições estão ocupadas.");
        } else {
            System.out.println("Posições de empilhamento vazias:");
            for (String posicao : posicoesVazias) {
                System.out.println(posicao);
            }
        }
    }

    private static void salvarQuadra(QuadraEmpilhamento quadra) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("quadras.txt"))) {
            outputStream.writeObject(quadra);
            System.out.println("Dados da quadra salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados da quadra: " + e.getMessage());
        }
    }

    private static QuadraEmpilhamento carregarQuadra() {
        QuadraEmpilhamento quadra = new QuadraEmpilhamento();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("quadras.txt"))) {
            quadra = (QuadraEmpilhamento) inputStream.readObject();
            System.out.println("Dados da quadra carregados com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Não foi possível carregar os dados da quadra: " + e.getMessage());
        }
        return quadra;
    }
}