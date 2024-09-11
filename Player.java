import java.util.*;

public class Player {
    public Scanner input = new Scanner(System.in);
    public int roll;
    public int xPos;
    public int yPos;
    public Room currentRoom;
    public String name;
    public String namesGuessed;
    public String weaponsGuessed;
    public String roomsGuessed;
    public ArrayList<String> hand;
    public Player nextplayer;
    public boolean isNPC;

    public Player() {

    }
    public Player(String n) {
        currentRoom = null;
        name = n;
        roll();
        isNPC = false;
/*      do {
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
*/
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
    
    public Player clone() {
        Player p = new Player(this.name);
        return p;

    }

    public String toString() {
        return this.name;
    }

    private class Scorecard {
        private HashMap<String, String> people;
        private HashMap<String, String> weapons;
        private HashMap<String, String> rooms;


        private Scorecard() {

        }

        public String toString() {
            System.out.println("_________________________");
            System.out.print("|     ");
        }
    }

}
