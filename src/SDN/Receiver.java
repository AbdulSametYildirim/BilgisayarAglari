package SDN;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;

public class Receiver {

    private static ServerSocket serverSocket;
    private static final int PORT = 1309;

    public static void main(String[] args) {
        System.out.println("Opening port");
        try {
            serverSocket = new ServerSocket(PORT);   
        } catch (IOException ioEx) {
            System.out.println(
                    "Unable to attach to port for receiver!");
            System.exit(1);
        }

        handleRouter();

    }

    private static void handleRouter() {
        Socket link = null;                            
        try {
            
            int numMessages = 0;
            String message;
            do {

                link = serverSocket.accept();
                   
                Scanner input
                        = new Scanner(link.getInputStream());   
                    

                message = input.nextLine();
                if (message.equals("***CLOSE***")) {
                    numMessages=0;
                    break;
                }
                String[] strings = message.split("_");
                String str = strings[1];
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);
                output.println("ACK" + numMessages + "_" + str);
                numMessages++;
                System.out.println(numMessages + ":" + message);
                
            } while (!message.equals("***CLOSE***"));

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                System.out.println(
                        "\n* Closing connections (Receiver side)*");
                link.close();
            } catch (IOException ioEx) {
                System.out.println(
                        "Unable to disconnect!");
                System.exit(1);
            }
        }
    }

}
