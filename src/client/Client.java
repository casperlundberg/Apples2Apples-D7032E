package client;

import game.GameState;
import game.apples.GreenApple;
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
        Socket socket = new Socket("localhost", 8080);
        System.out.println("Connected to the server");

        addPlayerToGame(socket);

        //check which phase that is currently
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        Phase currentPhase = (Phase) inputStream.readObject();

        if (currentPhase instanceof DrawGreenApplePhase) {
            System.out.println("It's the draw green apple phase");
            GreenApple greenApple = (GreenApple) inputStream.readObject();
            System.out.println("Green apple in play is: " + greenApple.getContent());
        } else if (currentPhase instanceof SubmitRedApplePhase) {
            System.out.println("It's the submit red apple phase");
        }


//        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
//        GameState gameState = (GameState) inputStream.readObject();




//        int playerID = gameState.addPlayer(playerName);
//        System.out.println("You are player " + playerID + " with name " + playerName);

//        System.out.println("Choose a red apple to play: ");
//        PlayerPlayedRedAppleModel playRedApple = new PlayerPlayedRedAppleModel(player, redApple);

//        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//        outputStream.writeObject(playRedApple);
//        outputStream.flush();
    }

    static void addPlayerToGame(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(player);
    }
}