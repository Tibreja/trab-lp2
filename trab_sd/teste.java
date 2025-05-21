import java.io.FileWriter;
import java.net.*;
import java.util.Scanner;

public class teste {
    // Configurações de rede
    private static final String SERVER_IP = "localhost";      // IP do servidor principal
    private static final String REPLICA_IP = "192.168.1.7";     // IP da réplica
    private static final int SERVER_PORT = 9876;
    private static final int REPLICA_PORT = 9877;
    private static final int TIMEOUT_MS = 5000;               // 5 segundos de timeout

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try (Scanner scanner = new Scanner(System.in)) {
            socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT_MS);

            // Conecta ao servidor principal para login
            InetSocketAddress mainServer = new InetSocketAddress(InetAddress.getByName(SERVER_IP), SERVER_PORT);
            System.out.println("Tentando conectar ao servidor principal para login...");
            
            String loginMessage = "LOGIN";
            byte[] sendData = loginMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, mainServer.getAddress(), mainServer.getPort());
            socket.send(sendPacket);

            // Recebe resposta do login
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();

            // Verifica resposta válida
            if (!serverResponse.equals("LOGIN_OK")) {
                System.out.println("Falha ao conectar ao servidor principal.");
                return;
            }

            System.out.println("Login bem-sucedido no servidor principal!");

            // Solicita ao usuário a escolha do servidor para download
            System.out.println("Escolha o servidor para download:");
            System.out.println("1 - Servidor Principal");
            System.out.println("2 - Réplica");
            int choice = scanner.nextInt();

            InetSocketAddress selectedServer;
            if (choice == 1) {
                selectedServer = mainServer;
            } else if (choice == 2) {
                selectedServer = new InetSocketAddress(InetAddress.getByName(REPLICA_IP), REPLICA_PORT);
            } else {
                System.out.println("Opção inválida. Encerrando o programa.");
                return;
            }

            System.out.println("Tentando conectar em: " + selectedServer.getAddress().getHostAddress() + ":" + selectedServer.getPort());

            // Solicita o arquivo de preços
            System.out.println("Solicitando arquivo...");
            String requestFileMessage = "REQUEST_FILE";
            sendData = requestFileMessage.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, selectedServer.getAddress(), selectedServer.getPort());
            socket.send(sendPacket);

            // Recebe o arquivo (aumentando o buffer para grandes arquivos)
            receiveData = new byte[65507];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String conteudo = new String(receivePacket.getData(), 0, receivePacket.getLength());
            try (FileWriter writer = new FileWriter("cliente_precos.txt")) {
                writer.write(conteudo);
                System.out.println("Arquivo atualizado na cliente!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}