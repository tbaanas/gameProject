package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(54345);


        Service clientFirst = new Service(serverSocket.accept());
        Service clientSecond = new Service(serverSocket.accept());



        clientFirst.clientMatch(clientSecond);
        clientSecond.clientMatch(clientFirst);




        clientFirst.start();
        clientSecond.start();


    }

}

class Service extends Thread {

    BufferedReader bufferedReader;

    PrintWriter printWriter;

    public void sendPing() {

        printWriter.println("ping");
        printWriter.flush();

    }

    public void sendPong() {
        printWriter.println("pong");
        printWriter.flush();

    }

    Service(Socket client) {
        try {

            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            printWriter = new PrintWriter(client.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {


        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equalsIgnoreCase("ping")) {

                    clientSendMsg.sendPing();
                } else if (line.equalsIgnoreCase("pong")) {
                    clientSendMsg.sendPong();

                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    Service clientSendMsg;

    public void clientMatch(Service clientHandler) {
        this.clientSendMsg = clientHandler;

    }
}


