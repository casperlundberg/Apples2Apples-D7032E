package client;
import java.io.*;
import java.net.*;

/**
 * This is where we will send the red apple to the server.
 * We need to add a loop that waits for the server to send a message (Green apple) to the client.
 * We need to split this code into different functions instead of having all in the constructor.
 * */
public class client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    // constructor to put ip address and port
    public client(String address, int port)
    {

        // establish a connection
        try {

            socket = new Socket(address, port);

            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(
                    socket.getOutputStream());
        }

        catch (UnknownHostException u) {

            System.out.println(u);
        }

        catch (IOException i) {

            System.out.println(i);
        }

        // string to read message from input
        String line = "";

        // keep reading until "End" is input
        while (!line.equals("End")) {

            try {

                line = input.readLine();

                out.writeUTF(line);
            }

            catch (IOException i) {

                System.out.println(i);
            }
        }

        // close the connection
        try {

            input.close();

            out.close();

            socket.close();
        }

        catch (IOException i) {

            System.out.println(i);
        }
    }

    public static void main(String[] args)
    {

        client client = new client("127.0.0.1", 5000);
    }
}
