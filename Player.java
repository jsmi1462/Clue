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

    public Player() {
    }

    public Player(String n) {
        currentRoom = null;
        name = n;
        isNPC = false;
        hand = new ArrayList<String>();
        guesses = new ArrayList<String>();
    }

    public void update() {
        card = new Scorecard(this);
//      card.update();
    }

    public void guess() {
        ArrayList<String> tempGuesses = new ArrayList<String>();
        Player tempNext = nextPlayer;
        int firstParse = (int) (Math.random() * 3); //randomizes guesses to compare
        boolean answer = false; //helps determine
        System.out.println(card); //Displays Scorecard
        boolean invalid = true;
        do {
            try {
                System.out.print("You are in the " + currentRoom.name + " right now.\r\n"
                    + "Please type the name of the character you would like to accuse: ");
                tempGuesses.add(input.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input: Please try again.");
                continue;
            }
            invalid = false;
        } while (invalid);
        invalid = true;
        do {
            try {
                System.out.print("Please type the name of the weapon you would like to guess: ");
                tempGuesses.add(input.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input: Please try again.");
                continue;
            }
            invalid = false;
        } while (invalid);
        tempGuesses.add(currentRoom.name);




        boolean firstpersonfound = false;
        int i = 1;
        Player tempplayer = this.nextPlayer;
        ArrayList<String> types = new ArrayList<>(Arrays.asList("People", "Weapons", "Rooms"));
        while (!firstpersonfound && (i < 6)) {
            for (int j = 0 ; j < 3; j++) {
                if (tempplayer.hand.indexOf(tempGuesses.get(j)) != -1) firstpersonfound = true;
                else {
                    card.setvalue(types.get(j), i, tempGuesses.get(j), "O");
                }
            }
            i++;
            tempplayer = tempplayer.nextPlayer;
        }
        if (i == 6) return;

        ArrayList<String> options = new ArrayList<>();
        for (String possible : tempGuesses) {
            if (tempplayer.hand.indexOf(possible) != -1) options.add(possible);
        }
        System.out.println("Player " + tempplayer.name + ": pick a card to show Player " + this);
        for (String option: options) {
            System.out.println(option);
        }

        invalid = true;
        String guess = new String();
        while (invalid) {
            guess = input.nextLine();
            if (options.indexOf(guess) != -1) {
                invalid = false;
            }
        }

        card.setvalue(types.get(options.indexOf(guess)), i, guess, "X");


    }

    @Override
    public Player clone() {
        Player temp = new Player(name);
        return temp;
    }

    

    public String toString() {
        return this.name;
    }
    

    public class Scorecard {
        public HashMap<String, ArrayList<HashMap<String, String>>> hashmaps = new HashMap<>();
        
        private Player associatedplayer;
        private String[] weaponcards = {"Candlestick", "Knife", "Lead Pipe", "Pistol", "Rope", "Wrench"};
        private String[] roomcards = {"Ball Room", "Billiard Room", "Conservatory", "Dining Room", "Hall", "Kitchen", "Library", "Lounge", "Study"};
        private ArrayList<String> playercards = new ArrayList<>();

        public Scorecard(Player player) {
            associatedplayer = player;
            fetchnames();
            ArrayList<HashMap<String, String>> peoplehashmaps = new ArrayList<>();
            ArrayList<HashMap<String, String>> weaponhashmaps = new ArrayList<>();
            ArrayList<HashMap<String, String>> roomhashmaps = new ArrayList<>();
            hashmaps.put("People", peoplehashmaps);
            hashmaps.put("Weapons", weaponhashmaps);
            hashmaps.put("Rooms", roomhashmaps);

            for (int i = 0; i < 6; i ++) {
                HashMap<String, String> temphashmap = new HashMap<String, String>();
                for (String playername: playercards) {
                    temphashmap.put(playername, " ");
                }
                peoplehashmaps.add(temphashmap);

                temphashmap = new HashMap<String, String>();
                for (String weaponcard: weaponcards) {
                    temphashmap.put(weaponcard, " ");
                }
                weaponhashmaps.add(temphashmap);

                temphashmap = new HashMap<String, String>();
                for (String roomcard: roomcards) {
                    temphashmap.put(roomcard, " ");
                }
                roomhashmaps.add(temphashmap);
            }
        }

        public void update() {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 6; i ++) {
                    if (hand.get(i)) 
                }
            }

            }
            for (String card: hand) {
                if ()
            }
        }

        public int checkCard(String type, String s) {
            for (int i = 0; i < 6; i ++) {
                HashMap<String, String> tocheck = hashmaps.get(type).get(i);
                if (tocheck.get(s).equals("X")) return i;
            }
            return -1;
        }

        public void fetchnames() {
            Player tempplayer = associatedplayer;
            for (int i = 0; i < 6; i ++) {
                playercards.add(tempplayer.name);
                tempplayer = tempplayer.nextPlayer;
            }
        }

        public void setvalue(String type, int indexfrom, String key, String value) {
            hashmaps.get(type).get(indexfrom).put(key, value);

        }

        public String toString() {
            String display = ""; //1st Column: Subject |a 2nd Column: Own Cards | 3rd Column: 1st Player in Queue | 4th Column: 2nd Player in Queue | 5th Column: 3rd Player Queue | 6th Column: 4th Player in Queue | 7th Column: 5th Players in Queue
            display = "________________________________________________________________________________\r\n|                                   ";
            for (int i = 0; i < 6; i++) {
                display += " | " + playercards.get(i);
            }
            display += "\n";
            
            for (int i = 0; i < 6; i ++) {
                display += "| " + playercards.get(0);
                for (int j = 0; j < 6; j ++) {
                    display += " | " + hashmaps.get("People").get(i).get(playercards.get(j)) + " ";
                }
                display += "\r\n";
            }
            display += "________________________________________________________________________________\r\n";
            for (int i = 0; i < 6; i ++) {
                display += "| " + roomcards[0];
                for (int j = 0; j < 6; j ++) {
                    display += " | " + hashmaps.get("Rooms").get(i).get(roomcards[j]) + " ";
                }
                display += "\r\n";
            }
            display += "________________________________________________________________________________\r\n";
            for (int i = 0; i < 6; i ++) {
                display += "| " + weaponcards[0];
                for (int j = 0; j < 6; j ++) {
                    display += " | " + hashmaps.get("Weapons").get(i).get(weaponcards[j]) + " ";
                }
                display += "\r\n";
            }
            return display;
        }


    }



}

