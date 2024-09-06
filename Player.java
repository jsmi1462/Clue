import java.util.*;

public class Player {
    public Scanner input = new Scanner(System.in);
    public int roll;
    public int xPos;
    public int yPos;
    public Room currentRoom;
    public Player nextPlayer;
    public String name;
    public String namesGuessed;
    public String weaponsGuessed;
    public String roomsGuessed;
    public ArrayList<String> hand;
    public ArrayList<String> guesses;
    public Scorecard card;

    public Player(int xP, int yP, String n, Player next) {
        currentRoom = null;
        name = n;
        nextPlayer = next;
        roll();
        hand = new ArrayList<String>();
        guesses = new ArrayList<String>();
        card = new Scorecard();
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
        ArrayList<String> tempGuesses = new ArrayList<String>();
        Player tempNext = nextPlayer;
        int firstParse = (int) (Math.random() * 3);
        boolean answer = false;
        System.out.println(card);
        System.out.print("You are in the " + currentRoom.name + " right now.\r\n"
            + "Please type the name of the character you would like to accuse: ");
        tempGuesses.add(input.nextLine());
        System.out.print("Please type the name of the weapon you would like to guess: ");
        tempGuesses.add(input.nextLine());
        tempGuesses.add(currentRoom.name);
        for (int p = 0; p < 6; p++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tempGuesses.get(firstParse).equalsIgnoreCase(tempNext.hand.get(j))) {
                        guesses.add(tempGuesses.get(firstParse));
                        answer = false;
                        System.out.println(tempNext.name + " has revealed this card to you: " + tempGuesses.get(firstParse));
                        if (firstParse == 0) {
                            card.setPeople(tempGuesses.get(firstParse), "X");
                        } else if (firstParse == 1) {
                            card.setWeapons(tempGuesses.get(firstParse), "X");
                        } else {
                            card.setRooms(tempGuesses.get(firstParse), "X");
                        }
                        break;
                    } else {
                        answer = true;
                    }
                }
                if (firstParse / 2 == 0) {
                    firstParse++;
                } else if (!answer) {
                    break;
                } else if (i == 2) {
                    break;
                } else {
                    firstParse = 0;
                }
            }
            if (!answer) {
                break;
            } else {
                tempNext = tempNext.nextPlayer;
            }
        }
        if (!answer) {
            System.out.println("Nobody had the cards you guessed!");
        }


/*
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
*/
    }




    public class Scorecard {
        private HashMap<String, String> people;
        private HashMap<String, String> weapons;
        private HashMap<String, String> rooms;
        private ArrayList<Player> players;


        public Scorecard() {
            String[] weaponCards = {"Candlestick", "Knife", "Lead Pipe", "Pistol", "Rope", "Wrench"};
            String[] roomCards = {"Ball Room", "Billiard Room", "Conservatory", "Dining Room", "Hall", "Kitchen", "Library", "Lounge", "Study"};
            for (int w = 0; w < 6; w++) {
                weapons.put(weaponCards[w], "O");
            }
            for (int r = 0; r < 9; r++) {
                rooms.put(roomCards[r], "O");
            }
        }

        public void update() {
            players = new ArrayList<Player>();
            Player tempPlayer = nextPlayer;
            people.put(name, "O");
            for (int p = 0; p < 5; p++) {
                people.put(tempPlayer.name, "O");
                players.add(tempPlayer);
                tempPlayer = tempPlayer.nextPlayer;
            }
            players.add(0, tempPlayer.nextPlayer);
        }

        public HashMap<String, String> getPeople() {
            return people;
        }

        public void setPeople(String key, String value) {
            people.put(key, value);
        }

        public HashMap<String, String> getWeapons() {
            return weapons;
        }

        public void setWeapons(String key, String value) {
            weapons.put(key, value);
        }

        public HashMap<String, String> getRooms() {
            return rooms;
        }

        public void setRooms(String key, String value) {
            rooms.put(key, value);
        }

        public String toString() {
            String display = "";
            display = "_________________________\r\n"
                + "|     | " + name + " | " + players.get(1).name + " | " + players.get(2).name + " | " + players.get(3).name + " | " + players.get(4).name + " | " + players.get(5).name + " |\r\n"
                + "| Candlestick | " + people.get("Candlestick");
        }
    }

}
