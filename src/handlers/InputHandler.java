package handlers;

import game.apples.RedApple;
import game.player.Player;

import java.security.PublicKey;
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

    public int getNumberOfPlayers() {
        int numberOfPlayers = 0;
        boolean valid = false;
        while (!valid) {
            System.out.println("If you enter less than 4 players, \nthose spots will be filled by bots even if you enter too few bots in the next input. \nEnter the number of players: ");
            if (scanner.hasNextInt()) {
                numberOfPlayers = scanner.nextInt();
                if (numberOfPlayers >= 1) {
                    valid = true;
                } else {
                    System.out.println("Invalid number of players. Please enter a number between higher than 0.");
                    scanner.nextLine(); // clear scanner buffer so the next input can be read
                }
            } else {
                System.out.println("No input available. Please enter an integer.");
                scanner.nextLine(); // clear scanner buffer so the next input can be read
            }
        }
        return numberOfPlayers;
    }

    public int getNumberOfBots() {
        int numberOfBots = 0;
        boolean valid = false;
        while (!valid) {
            System.out.println("Minimum amount of players and bots are 4, \nif there are less than 4 players the game will be filled with bots. \nEnter the number of bots: ");
            if (scanner.hasNextInt()) {
                numberOfBots = scanner.nextInt();
                if (numberOfBots >= 0) {
                    valid = true;
                } else {
                    System.out.println("Invalid number of bots. Please enter a number between 0 and 10.");
                    scanner.nextLine(); // clear scanner buffer so the next input can be read
                }
            } else {
                System.out.println("No input available. Please enter an integer.");
                scanner.nextLine(); // clear scanner buffer so the next input can be read
            }
        }
        return numberOfBots;
    }
}