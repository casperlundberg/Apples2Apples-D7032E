package server;

import game.GameState;
import game.apples.RedApple;
import game.models.PlayerPlayedRedAppleModel;
import game.phases.*;
import game.players.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server is listening on port 8080");

        GameState gameState = new GameState();

        while (true) {
            // Accept new client and add the player to the game along with their socket
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Player player = (Player) inputStream.readObject();
            player.setSocket(socket);
            gameState.addPlayer(player);

            // If all players have joined, start the game
            Phase currentPhase = gameState.getCurrentPhase();
            if (gameState.allPlayersJoined()) {
                gameState = currentPhase.execute(socket, gameState);
                gameState.nextPhase();
            }
        }
    }
}