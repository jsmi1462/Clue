import java.util.*;

public class Player {
    public Scanner input = new Scanner(System.in);
    public int roll;
    public int xPos;
    public int yPos;
    public Room currentRoom;
    public Player nextPlayer;
    public String name;
    public ArrayList<String> hand;
    public ArrayList<String> guesses;
    public ArrayList<Player> players;
    public Scorecard card;

    public Player(int xP, int yP, String n, Player next) {
        currentRoom = null;
        name = n;
        nextPlayer = next;
        roll();
        hand = new ArrayList<String>();
        guesses = new ArrayList<String>();
        players = new ArrayList<Player>();
        card = new Scorecard();
    }

    public void roll() {
        int rollNum;
        rollNum = ((int) Math.random() * 5 + 1) + ((int) Math.random() * 5 + 1);
        roll = rollNum;
    }

    public void update() {
        Player tempNext = nextPlayer;
        for (int i = 0; i < 5; i++) {
            players.add(tempNext);
            tempNext = tempNext.nextPlayer;
        }
        players.add(0, tempNext.nextPlayer);
    }

    public void guess() {
        ArrayList<String> tempGuesses = new ArrayList<String>();
        Player tempNext = nextPlayer;
        int firstParse = (int) (Math.random() * 3);
        boolean answer = false;
        System.out.println(card); //Displays Scorecard
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
                            card.getPlayers().get(p).card.setPeople(tempGuesses.get(firstParse), "X");
                        } else if (firstParse == 1) {
                            card.getPlayers().get(p).card.setWeapons(tempGuesses.get(firstParse), "X");
                        } else {
                            card.getPlayers().get(p).card.setRooms(tempGuesses.get(firstParse), "X");
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
            for (int i = 0; i < 3; i++) {
                if (firstParse == 0) {
                    card.getPlayers().get(p).card.setPeople(tempGuesses.get(i), "O");
                } else if (firstParse == 1) {
                    card.getPlayers().get(p).card.setWeapons(tempGuesses.get(i), "O");
                } else {
                    card.getPlayers().get(p).card.setRooms(tempGuesses.get(i), "O");
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
    }

    public Player clone() {
        Player temp = new Player(xPos, yPos, name, nextPlayer);
        temp.currentRoom = currentRoom;
        for (int i = 0; i < 3; i++) {
            temp.hand.add(hand.get(i));
        }
        for (int i = 0; i < guesses.size(); i++) {
            temp.guesses.add(guesses.get(i));
        }
        for (int i = 0; i < 6; i++) {
            temp.players.add(players.get(i));
        }
        temp.card = card.clone();
        return temp;
    }


    public class Scorecard {
        private HashMap<String, String> people;
        private HashMap<String, String> weapons;
        private HashMap<String, String> rooms;
        private ArrayList<Player> players;
        private String[] weaponCards = {"Candlestick", "Knife", "Lead Pipe", "Pistol", "Rope", "Wrench"};
        private String[] roomCards = {"Ball Room", "Billiard Room", "Conservatory", "Dining Room", "Hall", "Kitchen", "Library", "Lounge", "Study"};


        public Scorecard() { //Constructor that fills hashmaps
            for (int w = 0; w < 6; w++) {
                weapons.put(weaponCards[w], " ");
            }
            for (int r = 0; r < 9; r++) {
                rooms.put(roomCards[r], " ");
            }
        }

        public void update() { //Called only one time once all players are created in Map
            players = new ArrayList<Player>();
            Player tempPlayer = nextPlayer.clone();
            people.put(name, " ");
            for (int p = 0; p < 5; p++) {
                people.put(tempPlayer.name, " ");
                players.add(tempPlayer);
                tempPlayer = tempPlayer.nextPlayer.clone();
            }
            players.add(0, tempPlayer.nextPlayer.clone());
            boolean room;
            for (int i = 0; i < 3; i++) {
                room = true;
                for (int j = 0; j < 6; j++) {
                    if (hand.get(i).equals(players.get(j).name)) {
                        people.put(players.get(j).name, "X");
                        room = false;
                    } else if (hand.get(i).equals(weaponCards[j])) {
                        weapons.put(weaponCards[j], "X");
                        room = false;
                    }
                }
                if (room) {
                    rooms.put(hand.get(i), "X");
                }
            }
        }

        public Scorecard clone() {
            Scorecard temp = new Scorecard();
            for (int i = 0; i < 6; i++) {
                temp.people.put(players.get(i).name, people.get(players.get(i).name));
                temp.weapons.put(weaponCards[i], weapons.get(weaponCards[i]));
                temp.setPlayers(players.get(i));
            }
            for (int i = 0; i < 9; i++) {
                temp.rooms.put(roomCards[i], rooms.get(roomCards[i]));
            }
            return temp;
        }

                //Getters and Setters Below
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
        
        public ArrayList<Player> getPlayers() {
            return players;
        }

        public void setPlayers(Player p) {
            players.add(p);
        }

        public String toString() {
            String display = ""; //1st Column: Subject | 2nd Column: Own Cards | 3rd Column: 1st Player in Queue | 4th Column: 2nd Player in Queue | 5th Column: 3rd Player Queue | 6th Column: 4th Player in Queue | 7th Column: 5th Players in Queue
            display = "________________________________________________________________________________\r\n"
                + "|                                   | " + players.get(0).name + " | " + players.get(1).name + " | " + players.get(2).name + " | " + players.get(3).name + " | " + players.get(4).name + " | " + players.get(5).name + " |\r\n"
                + "| " + players.get(0).name + " | " + people.get(players.get(0).name) + " | " + players.get(1).card.getPeople().get(players.get(0).name) + " | " + players.get(2).card.getPeople().get(players.get(0).name) + " | " + players.get(3).card.getPeople().get(players.get(0).name) + " | " + players.get(4).card.getPeople().get(players.get(0).name) + " | " + players.get(5).card.getPeople().get(players.get(0).name) + " |\r\n"
                + "| " + players.get(1).name + " | " + people.get(players.get(1).name) + " | " + players.get(1).card.getPeople().get(players.get(1).name) + " | " + players.get(2).card.getPeople().get(players.get(1).name) + " | " + players.get(3).card.getPeople().get(players.get(1).name) + " | " + players.get(4).card.getPeople().get(players.get(1).name) + " | " + players.get(5).card.getPeople().get(players.get(1).name) + " |\r\n"
                + "| " + players.get(2).name + " | " + people.get(players.get(2).name) + " | " + players.get(1).card.getPeople().get(players.get(2).name) + " | " + players.get(2).card.getPeople().get(players.get(2).name) + " | " + players.get(3).card.getPeople().get(players.get(2).name) + " | " + players.get(4).card.getPeople().get(players.get(2).name) + " | " + players.get(5).card.getPeople().get(players.get(2).name) + " |\r\n"
                + "| " + players.get(3).name + " | " + people.get(players.get(3).name) + " | " + players.get(1).card.getPeople().get(players.get(3).name) + " | " + players.get(2).card.getPeople().get(players.get(3).name) + " | " + players.get(3).card.getPeople().get(players.get(3).name) + " | " + players.get(4).card.getPeople().get(players.get(3).name) + " | " + players.get(5).card.getPeople().get(players.get(3).name) + " |\r\n"
                + "| " + players.get(4).name + " | " + people.get(players.get(4).name) + " | " + players.get(1).card.getPeople().get(players.get(4).name) + " | " + players.get(2).card.getPeople().get(players.get(4).name) + " | " + players.get(3).card.getPeople().get(players.get(4).name) + " | " + players.get(4).card.getPeople().get(players.get(4).name) + " | " + players.get(5).card.getPeople().get(players.get(4).name) + " |\r\n"
                + "| " + players.get(5).name + " | " + people.get(players.get(5).name) + " | " + players.get(1).card.getPeople().get(players.get(5).name) + " | " + players.get(2).card.getPeople().get(players.get(5).name) + " | " + players.get(3).card.getPeople().get(players.get(5).name) + " | " + players.get(4).card.getPeople().get(players.get(5).name) + " | " + players.get(5).card.getPeople().get(players.get(5).name) + " |\r\n"
                + "________________________________________________________________________________\r\n"
                + "| Candlestick   | " + weapons.get("Candlestick") + " | " + players.get(1).card.getWeapons().get("Candlestick") + " | " + players.get(2).card.getWeapons().get("Candlestick") + " | " + players.get(3).card.getWeapons().get("Candlestick") + " | " + players.get(4).card.getWeapons().get("Candlestick") + " | " + players.get(5).card.getWeapons().get("Candlestick") + " |\r\n"
                + "| Knife         | " + weapons.get("Knife") + " | " + players.get(1).card.getWeapons().get("Knife") + " | " + players.get(2).card.getWeapons().get("Knife") + " | " + players.get(3).card.getWeapons().get("Knife") + " | " + players.get(4).card.getWeapons().get("Knife") + " | " + players.get(5).card.getWeapons().get("Knife") + " |\r\n"
                + "| Lead Pipe     | " + weapons.get("Lead Pipe") + " | " + players.get(1).card.getWeapons().get("Lead Pipe") + " | " + players.get(2).card.getWeapons().get("Lead Pipe") + " | " + players.get(3).card.getWeapons().get("Lead Pipe") + " | " + players.get(4).card.getWeapons().get("Lead Pipe") + " | " + players.get(5).card.getWeapons().get("Lead Pipe") + " |\r\n"
                + "| Pistol        | " + weapons.get("Pistol") + " | " + players.get(1).card.getWeapons().get("Pistol") + " | " + players.get(2).card.getWeapons().get("Pistol") + " | " + players.get(3).card.getWeapons().get("Pistol") + " | " + players.get(4).card.getWeapons().get("Pistol") + " | " + players.get(5).card.getWeapons().get("Pistol") + " |\r\n"
                + "| Rope          | " + weapons.get("Rope") + " | " + players.get(1).card.getWeapons().get("Rope") + " | " + players.get(2).card.getWeapons().get("Rope") + " | " + players.get(3).card.getWeapons().get("Rope") + " | " + players.get(4).card.getWeapons().get("Rope") + " | " + players.get(5).card.getWeapons().get("Rope") + " |\r\n"
                + "| Wrench        | " + weapons.get("Wrench") + " | " + players.get(1).card.getWeapons().get("Wrench") + " | " + players.get(2).card.getWeapons().get("Wrench") + " | " + players.get(3).card.getWeapons().get("Wrench") + " | " + players.get(4).card.getWeapons().get("Wrench") + " | " + players.get(5).card.getWeapons().get("Wrench") + " |\r\n"
                + "________________________________________________________________________________\r\n"
                + "| Ball Room     | " + rooms.get("Ball Room") + " | " + players.get(1).card.getRooms().get("Ball Room") + " | " + players.get(2).card.getRooms().get("Ball Room") + " | " + players.get(3).card.getRooms().get("Ball Room") + " | " + players.get(4).card.getRooms().get("Ball Room") + " | " + players.get(5).card.getRooms().get("Ball Room") + " |\r\n"
                + "| Billiard Room | " + rooms.get("Billiard Room") + " | " + players.get(1).card.getRooms().get("Billiard Room") + " | " + players.get(2).card.getRooms().get("Billiard Room") + " | " + players.get(3).card.getRooms().get("Billiard Room") + " | " + players.get(4).card.getRooms().get("Billiard Room") + " | " + players.get(5).card.getRooms().get("Billiard Room") + " |\r\n"
                + "| Conservatory  | " + rooms.get("Conservatory") + " | " + players.get(1).card.getRooms().get("Conservatory") + " | " + players.get(2).card.getRooms().get("Conservatory") + " | " + players.get(3).card.getRooms().get("Conservatory") + " | " + players.get(4).card.getRooms().get("Conservatory") + " | " + players.get(5).card.getRooms().get("Conservatory") + " |\r\n"
                + "| Dining Room   | " + rooms.get("Dining Room") + " | " + players.get(1).card.getRooms().get("Dining Room") + " | " + players.get(2).card.getRooms().get("Dining Room") + " | " + players.get(3).card.getRooms().get("Dining Room") + " | " + players.get(4).card.getRooms().get("Dining Room") + " | " + players.get(5).card.getRooms().get("Dining Room") + " |\r\n"
                + "| Hall          | " + rooms.get("Hall") + " | " + players.get(1).card.getRooms().get("Hall") + " | " + players.get(2).card.getRooms().get("Hall") + " | " + players.get(3).card.getRooms().get("Hall") + " | " + players.get(4).card.getRooms().get("Hall") + " | " + players.get(5).card.getRooms().get("Hall") + " |\r\n"
                + "| Kitchen       | " + rooms.get("Kitchen") + " | " + players.get(1).card.getRooms().get("Kitchen") + " | " + players.get(2).card.getRooms().get("Kitchen") + " | " + players.get(3).card.getRooms().get("Kitchen") + " | " + players.get(4).card.getRooms().get("Kitchen") + " | " + players.get(5).card.getRooms().get("Kitchen") + " |\r\n"
                + "| Library       | " + rooms.get("Library") + " | " + players.get(1).card.getRooms().get("Library") + " | " + players.get(2).card.getRooms().get("Library") + " | " + players.get(3).card.getRooms().get("Library") + " | " + players.get(4).card.getRooms().get("Library") + " | " + players.get(5).card.getRooms().get("Library") + " |\r\n"
                + "| Lounge        | " + rooms.get("Lounge") + " | " + players.get(1).card.getRooms().get("Lounge") + " | " + players.get(2).card.getRooms().get("Lounge") + " | " + players.get(3).card.getRooms().get("Lounge") + " | " + players.get(4).card.getRooms().get("Lounge") + " | " + players.get(5).card.getRooms().get("Lounge") + " |\r\n"
                + "| Study         | " + rooms.get("Study") + " | " + players.get(1).card.getRooms().get("Study") + " | " + players.get(2).card.getRooms().get("Study") + " | " + players.get(3).card.getRooms().get("Study") + " | " + players.get(4).card.getRooms().get("Study") + " | " + players.get(5).card.getRooms().get("Study") + " |\r\n"
                + "________________________________________________________________________________\r\n";
            return display;
        }
    }

}
