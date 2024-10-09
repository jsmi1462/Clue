import java.util.*;

public class Player {
    public Scanner input = new Scanner(System.in);
    public int xPos;
    public int yPos;
    public Room currentRoom;
    public Player nextPlayer;
    public String name;
    public ArrayList<String> hand;
    public ArrayList<String> guesses;
    public Scorecard card;
    public boolean isNPC;
    public map map;

    public Player() {

    }

    public Player(String n, map m) {
        currentRoom = null;
        name = n;
        map = m;
        isNPC = false;
        hand = new ArrayList<String>();
        guesses = new ArrayList<String>();
    }

    public void update() {
        card = new Scorecard();
    }

    public void guess() {
        ArrayList<String> tempGuesses = new ArrayList<String>();
        System.out.println(card); //Displays Scorecard
        do {
            try {
                System.out.print("You are in the " + currentRoom.name + " right now.\r\n"
                    + "Please type the name of the character you would like to accuse (Case Sensitive): ");
                tempGuesses.add(input.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid input: Please try again.");
                continue;
            }
        } while (true);
        do {
            try {
                System.out.print("Please type the name of the weapon you would like to guess (Case Sensetive): ");
                tempGuesses.add(input.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid input: Please try again.");
                continue;
            }
        } while (true);
        tempGuesses.add(currentRoom.name);

        boolean hasCard = false;
        ArrayList<String> cardsHad = new ArrayList<>();

        for (int p = 1; p < 6; p++) {
            for (int tG = 0; tG < 3; tG++) {
                for (int h = 0; h < 3; h++) {
                    // System.out.println("Guess: " + tempGuesses.get(tG) + " Hand: " + card.getPlayers(p).hand.get(h));
                    if (tempGuesses.get(tG).equalsIgnoreCase(card.getPlayers(p).hand.get(h))) {
                        hasCard = true;
                        cardsHad.add(tempGuesses.get(tG));
                    }
                }
            }
            if (hasCard) {
                System.out.println("\r\n" + name + ", please pass the screen to " + card.getPlayers(p).name + ".\r\n"
                    + card.getPlayers(p).name + ", please press enter to confirm that only you are looking at the screen.");
                input.nextLine();

                System.out.print("\r\nPlease enter the card you would like to show to " + name + " out of the following:\r\n");
                for (int s = 0; s < cardsHad.size(); s++) {
                    System.out.print(cardsHad.get(s) + "\r\n");
                }

                String cardRevealed;
                do {
                    try {
                        System.out.print("\r\nCard to be revealed (Case Sensitive): ");
                        cardRevealed = input.nextLine();
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid input: Please try again.");
                        continue;
                    }
                } while (true);

                System.out.println("\r\n" + card.getPlayers(p).name + ", please pass the screen back to " + name + ".\r\n"
                    + name + ", please press enter to confirm that only you are looking at the screen.");
                input.nextLine();

                System.out.println("\r\n" + card.getPlayers(p) + " has revealed the card \"" + cardRevealed + "\" to you. This information has been recorded!\r\n");
                if (tempGuesses.indexOf(cardRevealed) == 0) {
                    card.getPlayers(p).card.setPeople(cardRevealed, "X");
                } else if (tempGuesses.indexOf(cardRevealed) == 1) {
                    card.getPlayers(p).card.setWeapons(cardRevealed, "X");
                } else {
                    card.getPlayers(p).card.setRooms(cardRevealed, "X");
                }
                break;
            } else {
                System.out.println("\r\n" + card.getPlayers(p).name + " did not have any of the cards you guessed. This information has been recorded!\r\n"
                    + "Moving onto checking the cards of " + card.getPlayers(p + 1) + "...\r\n");
                card.getPlayers(p).card.setPeople(tempGuesses.get(0), "O");
                card.getPlayers(p).card.setWeapons(tempGuesses.get(1), "O");
                card.getPlayers(p).card.setRooms(tempGuesses.get(2), "O");
            }
        }
    }

    public boolean finalGuess() {
        boolean correct = false;
        int x = 0;
        ArrayList<String> finalGuesses = new ArrayList<>();
        do {
            try {
                if (x == 0) {
                    System.out.print("Character: ");
                    finalGuesses.add(input.nextLine());
                    x++;
                } else if (x == 1) {
                    System.out.print("Weapon: ");
                    finalGuesses.add(input.nextLine());
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        } while (true);
        finalGuesses.add(currentRoom.name);
        if (finalGuesses.get(0).equalsIgnoreCase(map.answer[0]) &&
            finalGuesses.get(1).equalsIgnoreCase(map.answer[1]) &&
            finalGuesses.get(2).equalsIgnoreCase(map.answer[2])) {
                correct = true;
        }
        return correct;
    }

    public Player cloneName() {
        Player temp = new Player(name, map);
        return temp;
    }

    @Override
    public Player clone() { //clones player object
        Player temp = new Player(name, map);
        for (int i = 0; i < 3; i++) {
            temp.hand.add(hand.get(i));
        }
        temp.nextPlayer = this.nextPlayer;
        temp.card = new Scorecard();
        return temp;
    }

    public String toString() {
        return this.name;
    }
    
    public class Scorecard {
        private HashMap<String, String> people = new HashMap<>();
        private HashMap<String, String> weapons = new HashMap<>();
        private HashMap<String, String> rooms = new HashMap<>();
        private ArrayList<Player> players = new ArrayList<>();
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

        public boolean checkCard(int playerNum, String hash, String s) {
            if (hash.equalsIgnoreCase("People")) {
                if (players.get(playerNum).card.getPeople(s).equals("X")) {
                    return true;
                } else {
                    return false;
                }
            } else if (hash.equalsIgnoreCase("Weapon")) {
                if (players.get(playerNum).card.getWeapons(s).equals("X")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (players.get(playerNum).card.getRooms(s).equals("X")) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        public void update() { //Called only one time once all players are created in Map
            Player tempNext = nextPlayer;
            Player playerClone = tempNext.clone();
            for (int p = 0; p < 5; p++) {
                players.add(playerClone);
                //System.out.println("adding" + playerClone);
                //System.out.println(players);
                tempNext = tempNext.nextPlayer;
                playerClone = tempNext.clone();
            }
            players.add(0, playerClone);
            for (int p = 0; p < 6; p++) {
                for (int h = 0; h < 6; h++) {
                    //System.out.println(players);
                    players.get(p).card.setPeople(players.get(h).name, " ");
                }
            }
            boolean room;
            for (int c = 0; c < 3; c++) {
                room = true;
                for (int x = 0; x < 6; x++) {
                    System.out.println(players.get(0).hand);
                    if (players.get(0).hand.get(c).equals(weaponCards[x])) {
                        players.get(0).card.setWeapons(weaponCards[x], "X");
                        room = false;
                    } else if (players.get(0).hand.get(c).equals(players.get(x).name)) {
                        players.get(0).card.setPeople(players.get(x).name, "X");
                        room = false;
                    }
                }
                if (room) {
                    players.get(0).card.setRooms(players.get(0).hand.get(c), "X");
                }
            }
        }

                //Getters and Setters Below
        public String getPeople(String key) {
            return people.get(key);
        }

        public void setPeople(String key, String value) {
            people.put(key, value);
        }

        public String getWeapons(String key) {
            return weapons.get(key);
        }

        public void setWeapons(String key, String value) {
            weapons.put(key, value);
        }

        public String getRooms(String key) {
            return rooms.get(key);
        }

        public void setRooms(String key, String value) {
            rooms.put(key, value);
        }
        
        public Player getPlayers(int i) {
            return players.get(i);
        }

        public void setPlayers(int index, Player p) {
            players.add(index, p);
        }

        public String fillSpace(int charNum, String name) {
            String output = "";
            if ((charNum - name.length()) % 2 == 0) {
                for (int s = 0; s < (charNum - name.length()) / 2; s++) {
                    output += " ";
                }
                output += name;
                for (int s = 0; s < (charNum - name.length()) / 2; s++) {
                    output += " ";
                }
            } else {
                for (int s = 0; s < (charNum - name.length()) / 2; s++) {
                    output += " ";
                }
                output += name;
                for (int s = 0; s < ((charNum - name.length()) / 2) + 1; s++) {
                    output += " ";
                }
            }
            return output;
        }

        public String toString() {
            String display = " ";

            //Divider
            for (int i = 0; i < 111; i++) {
                display += "-";
            }

            //Character Headers
            display += "\r\n|";
            for (int s = 0; s < 15; s++) {
                display += " ";
            }
            display += "|";
            for (int i = 0; i < 6; i++) {
                display += this.fillSpace(15, players.get(i).name) + "|";
            }

            //Characters
            display += "\r\n";
            for (int i = 0; i < 6; i++) {
                display += "| ";
                display += players.get(i).name;
                for (int j = 0; j < 14 - players.get(i).name.length(); j++) {
                    display += " ";
                }
                display += "|";
                for (int p = 0; p < 6; p++) {
                    display += this.fillSpace(15, players.get(p).card.getPeople(players.get(i).name)) + "|";
                }
                display += "\r\n";
            }

            //Divider
            display += "|";
            for (int i = 0; i < 111; i++) {
                display += "-";
            }

            //Weapons
            display += "\r\n";
            for (int i = 0; i < 6; i++) {
                display += "| ";
                display += weaponCards[i];
                for (int j = 0; j < 14 - weaponCards[i].length(); j++) {
                    display += " ";
                }
                display += "|";
                for (int w = 0; w < 6; w++) {
                    display += this.fillSpace(15, players.get(w).card.getWeapons(weaponCards[i])) + "|";
                }
                display += "\r\n";
            }

            //Divider
            display += "|";
            for (int i = 0; i < 111; i++) {
                display += "-";
            }

            //Rooms
            display += "\r\n";
            for (int i = 0; i < 9; i++) {
                display += "| ";
                display += roomCards[i];
                for (int j = 0; j < 14 - roomCards[i].length(); j++) {
                    display += " ";
                }
                display += "|";
                for (int w = 0; w < 6; w++) {
                    display += this.fillSpace(15, players.get(w).card.getRooms(roomCards[i])) + "|";
                }
                display += "\r\n";
            }

            //Divider
            display += " ";
            for (int i = 0; i < 111; i++) {
                display += "-";
            }
            return display;
        }
    }

}
