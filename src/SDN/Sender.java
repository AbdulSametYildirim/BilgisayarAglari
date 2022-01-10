package SDN;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender extends Thread {

    private static InetAddress host;
    private static final int PORT = 1300;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
            System.out.println("Enter TcpRouter IP Address:");
        } catch (Exception uhEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer() {
        Socket link = null;

        try {

            for (int i = 0; i < 6; i++) {

                System.out.println("How many packets? ");
                Scanner userEntry = new Scanner(System.in);
                String message, str2, response;
                int number;
                response = userEntry.nextLine();
                number = Integer.parseInt(response);
                int counter = 0, attempt = 0;
                long startTime = System.nanoTime();

                do {
                    int[] paths = {1, 2};

                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(2);

                    int router_ad = paths[randomInt];

                    int NewPort = PORT + router_ad;

                    link = new Socket(host, NewPort);
                    link.setSoTimeout(4000);
                    Scanner input = new Scanner(link.getInputStream());
                    PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                    message = "PCK";

                    message = "PCK" + counter + "_" + String.valueOf(router_ad);
                    output.println(message);
                    attempt++;

                    String str = input.nextLine();

                    str2 = str.substring(0, 3);

                    while (!str2.equals("ACK")) {

                        link.close();
                        link = new Socket(host, NewPort);
                        input = new Scanner(link.getInputStream());
                        output = new PrintWriter(link.getOutputStream(), true);

                        System.out.println(message + counter + " Resending...");
                        output.println(message + counter);
                        attempt++;
                        str = input.nextLine();
                        str2 = str.substring(0, 3);
                    }
                    System.out.println(str + " received from receiver successfully");

                    counter++;

                    if (counter == number) {

                        output.println("***CLOSE***");
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } while (counter < number);

                long endTime = System.nanoTime();
                System.out.println("Total number of try: " + attempt);
                System.out.println("Time taken to send all packets: " + (endTime - startTime) + " nano seconds.");

            }

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                System.out.println("\n* Closing connections (Sender side)*");
                link.close();
            } catch (IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}
