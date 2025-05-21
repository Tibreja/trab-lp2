import java.io.*;
import java.net.*;

public class ReplicaServidor_UDP {

    public static void main(String[] args) {
        try {
            // Inicia thread para receber atualizações do servidor principal
            new Thread(new ReplicaUpdateHandler()).start();
            
            // Socket para comunicação com clientes
            try (DatagramSocket clientSocket = new DatagramSocket(9877)) {
                System.out.println("Réplica iniciada na porta 9877...");

                while (true) {
                    byte[] receiveData = new byte[65507];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);

                    String mensagem = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                    InetAddress clientAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();

                    if (mensagem.equals("LOGIN")) {
                        // Resposta padrão para login na réplica
                        String resposta = "LOGIN_OK_REPLICA";
                        byte[] sendData = resposta.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        clientSocket.send(sendPacket);
                    } else if (mensagem.equals("REQUEST_FILE")) {
                        // Envia arquivo atualizado
                        String conteudo = lerArquivo("replica_precos.txt");
                        byte[] sendData = conteudo.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        clientSocket.send(sendPacket);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Classe interna para tratar atualizações do servidor principal
    private static class ReplicaUpdateHandler implements Runnable {
        @Override
        public void run() {
            try (DatagramSocket updateSocket = new DatagramSocket(9871)) {
                System.out.println("Réplica pronta para receber atualizações na porta 9871...");

                while (true) {
                    byte[] buffer = new byte[65507];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    updateSocket.receive(packet);
                    
                    String conteudo = new String(packet.getData(), 0, packet.getLength());
                    try (FileWriter writer = new FileWriter("replica_precos.txt")) {
                        writer.write(conteudo);
                        System.out.println("Arquivo atualizado na réplica!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        StringBuilder conteudo = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            conteudo.append(linha).append("\n");
        }
        leitor.close();
        return conteudo.toString();
    }
}