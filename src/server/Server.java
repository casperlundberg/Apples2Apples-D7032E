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
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server is listening on port 8080");

        GameState gameState = new GameState();

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");
            Phase currentPhase = gameState.getCurrentPhase();

            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                outputStream.writeObject(gameState.getCurrentPhase());
                outputStream.flush();
                if (currentPhase instanceof WaitForPlayersPhase) {
                    System.out.println("It's the wait for players phase");
                    Player player = (Player) inputStream.readObject();
                    gameState.addPlayer(player);
                    if (gameState.allPlayersJoined()) {
                        gameState.executePhase(currentPhase);
                    }
                } else if (currentPhase instanceof DrawGreenApplePhase) {
                    System.out.println("It's the draw green apple phase");
                    gameState.executePhase(currentPhase);
                    outputStream.writeObject(gameState.getCurrentGreenApple());
                    outputStream.flush();

                } else if (currentPhase instanceof SubmitRedApplePhase) {
                    System.out.println("It's the submit red apple phase");
                    PlayerPlayedRedAppleModel playerPlayedRedAppleModel = (PlayerPlayedRedAppleModel) inputStream.readObject();
                    gameState.playerPlayedRedApple(playerPlayedRedAppleModel);

                    if (gameState.allPlayersSubmittedRedApples()) {
                        gameState.executePhase(currentPhase);
                    }
                } else if (currentPhase instanceof JudgePhase) {
                    System.out.println("It's the judge phase");
                    outputStream.writeObject(gameState.getRedApplesToBeJudged());

                    RedApple winningRedApple = (RedApple) inputStream.readObject();
                    Player winningPlayer = gameState.getWinningPlayer(winningRedApple);

                    gameState.executePhase(currentPhase);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}