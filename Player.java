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
    public int absoluteindex;

    public Player() {
        
    }

    public Player(String n, map m, int absindex) {
        absoluteindex = absindex;
        currentRoom = null;
        name = n;
        map = m;
        isNPC = false;
        hand = new ArrayList<String>();
        guesses = new ArrayList<String>();
    }

    public String inputCheck(Scanner input, String phrase, String ... options) {
        String validInput = "";
        boolean notValid = true;
        do {
            try {
                System.out.print(phrase);
                validInput = input.nextLine();
                for (int i = 0; i < options.length; i++) {
                    if (validInput.equalsIgnoreCase(options[i])) {
                        validInput = options[i];
                        notValid = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid Input: Please try again.");
                continue;
            }
            if (notValid) {
                System.out.println("Invalid Input: Please try again.");
            }
        } while (notValid);
        return validInput;
    }

    public void update() {
        card = new Scorecard(this);
    }
    public void printHand() {
        System.out.println(this.hand);
    }
    public void guess() {
        ArrayList<String> tempGuesses = new ArrayList<>();
        System.out.println(card + "\r\nYou are in the " + currentRoom.name + " right now."); //Displays Scorecard
        tempGuesses.add(this.inputCheck(input, "Please type the name of the character you would like to accuse: ", card.getPeople()));
        tempGuesses.add(this.inputCheck(input, "Please type the name of the weapon you would like to guess: ", card.getWeapons()));
        tempGuesses.add(currentRoom.name);

        boolean hasCard = false;
        ArrayList<String> cardsHad = new ArrayList<>();

        for (int p = 1; p < 6; p++) {
            for (int tG = 0; tG < 3; tG++) { //tG = tempGuesses
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

                String[] cardsHadArr = new String[cardsHad.size()];
                for (int cH = 0; cH < cardsHad.size(); cH++) {
                    cardsHadArr[cH] = cardsHad.get(cH);
                }

                String cardRevealed = this.inputCheck(input, "\r\nCard to be revealed: ", cardsHadArr);

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
        ArrayList<String> finalGuesses = new ArrayList<>();
        finalGuesses.add(this.inputCheck(input, "Character: ", card.getPeople()));
        finalGuesses.add(this.inputCheck(input, "Weapon: ", card.getWeapons()));
        finalGuesses.add(this.inputCheck(input, "Room: ", card.getRooms()));
        if (finalGuesses.get(0).equalsIgnoreCase(map.answer[0]) &&
            finalGuesses.get(1).equalsIgnoreCase(map.answer[1]) &&
            finalGuesses.get(2).equalsIgnoreCase(map.answer[2])) {
                correct = true;
        }
        return correct;
    }

    public Player cloneName() {
        Player temp = new Player(name, map, absoluteindex);
        return temp;
    }

    @Override
    public Player clone() { //clones player object
        Player temp = new Player(name, map, absoluteindex);
        for (int i = 0; i < 3; i++) {
            temp.hand.add(hand.get(i));
        }
        temp.nextPlayer = this.nextPlayer;
        temp.card = new Scorecard(temp);
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
        private String[] peopleCards = new String[6];
        private String[] weaponCards = {"Candlestick", "Knife", "Lead Pipe", "Pistol", "Rope", "Wrench"};
        private String[] roomCards = {"Ball Room", "Billiard Room", "Conservatory", "Dining Room", "Hall", "Kitchen", "Library", "Lounge", "Study"};
        private Player currentPlayer; 

        public Scorecard(Player p) { //Constructor that fills hashmaps
            currentPlayer = p;
            for (int w = 0; w < 6; w++) {
                weapons.put(weaponCards[w], " ");
            }
            for (int r = 0; r < 9; r++) {
                rooms.put(roomCards[r], " ");
            }
        }
        public int checkCard(String hash, String s) {
            for (int i = 0; i < 6; i ++) {
                if (checkCardSpecific(i, hash, s)) {
                    return i;
                }
            }
            return -1;
        }

        public boolean checkCardSpecific(int playerNum, String hash, String s) {
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
            Player tempNext = currentPlayer;
            Player playerClone = tempNext.clone();
            for (int p = 0; p < 6; p++) {
                players.add(playerClone);
                tempNext = tempNext.nextPlayer;
                playerClone = tempNext.clone();
            }
            for (int p = 0; p < 6; p++) {
                for (int h = 0; h < 6; h++) {
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

            //fills peopleCards
            for (int p = 0; p < 6; p++) {
                peopleCards[p] = players.get(p).name;
            }

            //set O's for cards you know you don't have
            for (int p = 0; p < 6; p++) {
                if (players.get(0).card.getPeople(players.get(p).name).equals(" ")) {
                    players.get(0).card.setPeople(players.get(p).name, "O");
                } else {
                    for (int oP = 1; oP < 6; oP++) { //oP = other Players
                        players.get(oP).card.setPeople(peopleCards[p], "O");
                    }
                }
            }
            for (int w = 0; w < 6; w++) {
                if (players.get(0).card.getWeapons(weaponCards[w]).equals(" ")) {
                    players.get(0).card.setWeapons(weaponCards[w], "O");
                } else {
                    for (int oW = 1; oW < 6; oW++) { //oW = other Weapons
                        players.get(oW).card.setPeople(weaponCards[w], "O");
                    }
                }
            }
            for (int r = 0; r < 9; r++) {
                if (players.get(0).card.getRooms(roomCards[r]).equals(" ")) {
                    players.get(0).card.setRooms(roomCards[r], "O");
                } else {
                    for (int oR = 1; oR < 6; oR++) { //oR = other Rooms
                        players.get(oR).card.setPeople(roomCards[r], "O");
                    }
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

        public String[] getWeapons() {
            return weaponCards;
        }

        public void setWeapons(String key, String value) {
            weapons.put(key, value);
        }

        public String getRooms(String key) {
            return rooms.get(key);
        }

        public String[] getRooms() {
            return roomCards;
        }

        public void setRooms(String key, String value) {
            rooms.put(key, value);
        }
        
        public Player getPlayers(int i) {
            return players.get(i);
        }

        public String[] getPeople() {
            return peopleCards;
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
            display += " ";
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
            display += " ";
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
