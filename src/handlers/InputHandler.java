package handlers;

import game.apples.RedApple;
import game.players.Player;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }
    public RedApple chooseRedApple(Player player, ArrayList<RedApple> redApples) {
        // If the player is a bot, choose a random red apple
        if (player.isBot()) {
            return redApples.get(new SecureRandom().nextInt(redApples.size()));
        }

        boolean valid = false;
        int choice = 0;
        while (!valid) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 0 && choice < redApples.size()) {
                    valid = true;
                    System.out.println("You chose: " + redApples.get(choice).getContent());
                } else {
                    System.out.println("Invalid index, please enter a valid index: ");
                    scanner.nextLine(); // clear scanner buffer so the next input can be read
                }
            } else {
                System.out.println("No input available. Please enter an integer.");
                scanner.nextLine(); // clear scanner buffer so the next input can be read
            }
        }

        // Do not close the scanner here if it's System.in as it will close System.in as well
        return redApples.get(choice);
    }

    public String getPlayerNameInput() {
        String playerName;
        do {
            System.out.println("Enter your name: ");
            playerName = scanner.nextLine();

            if (playerName.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        } while (playerName.isEmpty());
        return playerName;
    }
}