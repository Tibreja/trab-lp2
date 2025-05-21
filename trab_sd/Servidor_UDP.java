import java.io.*;
import java.net.*;

public class Servidor_UDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876);
            System.out.println("Servidor iniciado...");

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData()).trim();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                if (clientMessage.equals("LOGIN")) {
                    System.out.println("Cliente conectado. Atualizando réplica...");

                    // Lê o arquivo atual
                    String fileContent = readFile("precos.txt");

                    // Envia confirmação de login
                    String loginResponse = "LOGIN_OK";
                    byte[] sendData = loginResponse.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    socket.send(sendPacket);

                    atualizarReplica(fileContent);
                } else if (clientMessage.equals("REQUEST_FILE")) {
                    // Envia o arquivo de preços
                    String fileContent = readFile("precos.txt");
                    byte[] sendData = fileContent.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    socket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }
    private static void atualizarReplica(String fileContent) {
        try {
            DatagramSocket replicaSocket = new DatagramSocket();
            InetAddress replicaAddress = InetAddress.getByName("localhost"); // IP da réplica
            byte[] sendData = fileContent.getBytes();
            
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,replicaAddress,9871 );
            
            replicaSocket.send(sendPacket);
            replicaSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}