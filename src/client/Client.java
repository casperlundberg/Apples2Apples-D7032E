package client;

import game.phases.Phase;
import game.players.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;

        socket = new Socket("localhost", 8080);
        System.out.println("Connected to the server");

        Scanner scanner = new Scanner(System.in);

        String playerName;
        do {
            System.out.println("Enter your name: ");
            playerName = scanner.nextLine().trim();
            if (playerName.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        } while (playerName.isEmpty());

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(playerName);

        Player player = new Player(playerName, socket);

        do {
            inputStream = new ObjectInputStream(socket.getInputStream());
            Phase currentPhase = (Phase) inputStream.readObject();
            System.out.println("Current phase: " + currentPhase.getClass().getSimpleName());

            player = currentPhase.executeOnClient(player);

        } while (!player.getDonePlaying());

        scanner.close();
    }
}