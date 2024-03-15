package client;

import game.GameState;
import game.apples.GreenApple;
import game.models.newPlayerModel;
import game.phases.DrawGreenApplePhase;
import game.phases.Phase;
import game.phases.SubmitRedApplePhase;
import game.players.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        socket = new Socket("localhost", 8080);
        System.out.println("Connected to the server");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();
        newPlayerModel playerModel = new newPlayerModel(playerName);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(playerModel);

        Player player = new Player(playerName);

        while (true) {
            inputStream = new ObjectInputStream(socket.getInputStream());
            Phase currentPhase = (Phase) inputStream.readObject();
            System.out.println("Current phase: " + currentPhase.getClass().getSimpleName());

            player = currentPhase.executeOnClient(socket, player);
        }
    }
}