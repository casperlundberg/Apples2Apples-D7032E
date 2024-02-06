package client;

import game.GameState;
import game.modules.PlayerPlayedRedAppleModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 8080);
        System.out.println("Connected to the server");

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        GameState gameState = (GameState) inputStream.readObject();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();

        int playerID = gameState.addPlayer(playerName);
        System.out.println("You are player " + playerID + " with name " + playerName);

//        System.out.println("Choose a red apple to play: ");
//        PlayerPlayedRedAppleModel playRedApple = new PlayerPlayedRedAppleModel(player, redApple);

//        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//        outputStream.writeObject(playRedApple);
//        outputStream.flush();
    }
}