package SDN;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Router5 {

    private static ServerSocket serverSocket;
    private static InetAddress host;
    private static final int PORT = 1305;
    private static final int PORT2 = 1300;

    private static Socket link2 = null;

    public static void main(String[] args) {
        System.out.println("Opening port");
        {
            try {
                host = InetAddress.getLocalHost();
                System.out.println("Enter TcpReceiver IP Address:");

            } catch (Exception uhEx) {
                System.out.println("Host ID not found!");
                System.exit(1);
            }

        }
        try {

            serverSocket = new ServerSocket(PORT);

        } catch (IOException ioEx) {
            System.out.println(
                    "Unable to attach to port for router!");
            System.exit(1);
        }

        handleClient();

    }

    private static String handleClient() {
        Socket link = null;
        String str2 = null;
        try {
            System.out.println("Router5");
            String message;
            do {

                link = serverSocket.accept();
                Scanner input = new Scanner(link.getInputStream());
                message = input.nextLine();
                int[] paths = {3, 6, 8};

                int[] paths2 = Functions.pastpath(message, paths);

                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(paths2.length);

                int router_ad = paths2[randomInt];

                int NewPort = PORT2 + router_ad;
                link2 = new Socket(host, NewPort);
                if (message.equals("***CLOSE***")) {
                    break;
                }

                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                Scanner input2 = new Scanner(link2.getInputStream());

                PrintWriter output2 = new PrintWriter(link2.getOutputStream(), true);
                System.out.println("message from sender " + message);
                message = message + String.valueOf(router_ad);

                int random = randomGenerator.nextInt(100);
                System.out.println("Generated random number for the packet is: " + random);
                if (random > -1) {

                    output2.println(message);

                    String str = input2.nextLine();
                    System.out.println("message from receiver: " + str);
                    output.println(str);
                    link2.close();
                } else {
                    output.println(str2);

                    link2.close();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Router5.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!message.equals("***CLOSE***"));

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                System.out.println(
                        "\n* Closing connections (Router side)*");
                link.close();
                link2.close();
            } catch (IOException ioEx) {
                System.out.println(
                        "Unable to disconnect!");
                System.exit(1);
            }
        }
        return null;
    }

}
