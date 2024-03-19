package server;

import game.GameState;
import game.phases.*;
import game.players.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port 8080");

            GameState gameState = new GameState();

            while (true) {
                // If all players have joined, start the game
                Phase currentPhase = gameState.getCurrentPhase();
                if (gameState.allPlayersJoined()) {

                    gameState = currentPhase.executeOnServer(gameState);

                    if (gameState.isGameEnded()) {
                        System.out.println("The game has ended");
                        break;
                    }

                    gameState.nextPhase();
                } else {
                    // Accept new client and add the player to the game along with their socket
                    Socket socket = serverSocket.accept();
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    String playerName = (String) inputStream.readObject();
                    Player player = new Player(playerName);
                    player.setSocket(socket);

                    System.out.println("New client connected on " + socket.getInetAddress() + ":" + socket.getPort() + " with username: " + player.getName());
                    gameState.addPlayer(player);
                }
            }
        }
    }
}