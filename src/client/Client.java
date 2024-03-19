package client;

import game.phases.Phase;
import game.players.Player;
import handlers.InputHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;

        socket = new Socket("localhost", 8080);
        System.out.println("Connected to the server");

        InputHandler inputHandler = new InputHandler();
        String playerName = inputHandler.getPlayerNameInput();

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(playerName);

        Player player = new Player(playerName, socket);

        do {
            inputStream = new ObjectInputStream(socket.getInputStream());
            Phase currentPhase = (Phase) inputStream.readObject();
            System.out.println("Current phase: " + currentPhase.getClass().getSimpleName());

            player = currentPhase.executeOnClient(player);

        } while (!player.getDonePlaying());
    }
}