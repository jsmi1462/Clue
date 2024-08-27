import System.util.Scanner;
import System.util.*;

public class Player {
    public Scanner input = new Scanner(System.in);
    public int roll;
    public int xPos;
    public int yPos;
    public Room currentRoom;
    public String character;
    public String namesGuessed;
    public String weaponsGuessed;
    public String roomsGuessed;
    public String hand;

    public Player(int xP, int yP, Player nextP) {
        currentRoom = null;
        roll();
        do {
            System.out.print("Choose your character: + \r\n"
                + "Colonel Mustard + \r\n"
                + "Mr. Green + \r\n"
                + "Mrs. Peacock + \r\n"
                + "Professor Plum + \r\n" 
                + "Mrs. Scarlet + \r\n"
                + "Mrs. White + \r\n"
                + "Input: ");
            try {
                character = input.next();
                break;
            } catch (Exception e) {
                System.out.println("Please input a valid character.");
                continue;
            }
        } while (true);

    }

    public void roll() {
        int rollNum;
        rollNum = ((int) Math.random() * 5 + 1) + ((int) Math.random() * 5 + 1);
        roll = rollNum;
    }

    public void guess() {
        ArrayList<String> guesses = new ArrayList<>();
        System.out.print("Your hand: " + hand + "\r\n"
            + "Cards revealed:\r\n"
            + "    People: " + namesGuessed + "\r\n"
            + "    Weapons: " + weaponsGuessed + "\r\n"
            + "    Rooms: " + roomsGuessed + "\r\n"
            + "\r\n"
            + "Your guess:\r\n"
            + "    Person: ");
        guesses.add(input.next());
        System.out.print("    Weapon: ");
        guesses.add(input.next());
        System.out.println("    Room: " + currentRoom.name);
        guesses.add(currentRoom.name);
        System.out.println(guesses.get(0) + ", " + guesses.get(1) + ", " + guesses.get(2));
    }


}
