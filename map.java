import java.util.*;
import java.io.*;

public class map {
    public char[][] map = new char[25][24]; // hardcoded map
    public ArrayList<Player> players = new ArrayList<Player>(); // list of players, will need to add some sort of method in map that implements the cll............
    public HashMap<int[], Room> doors = new HashMap<int[], Room>(); // doors linked to rooms, the reverse will be annoying unless we store player position inside door....
    public int nplay; // there has to be a better way this is stupid
    public String[] answer = new String[3];
    public ArrayList<String> cards = new ArrayList<String>();

    
    public map (int nplayers) { // constructor builds the map and adds the players to the map after input
        //add map
        this.nplay = nplayers;
        this.addrow("xxxxxxx xxxxxxxx xxxxxxx", 0);
        this.addrow("xxxxxxx  xxxxxx  xxxxxxx", 1);
        this.addrow("xxxxxxx  xxxxxx  xxxxxxx", 2);
        this.addrow("xxxxxxd  xxxxxx  xxxxxxx", 3);
        this.addrow("x        dxxxxx  xxxxxxx", 4);
        this.addrow("         xxxxxx  dxxxxxx", 5);
        this.addrow("xxxxxx   xxddxx        x", 6);
        this.addrow("xxxxxxx", 7);
        this.addrow("xxxxxxd  xxxxx         x", 8);
        this.addrow("xxxxxxx  xxxxx  xdxxxxxx", 9);
        this.addrow("xxxdxx   xxxxx  xxxxxxxx", 10);
        this.addrow("x        xxxxx  xxxxxxxx", 11);
        this.addrow("xdxxxx   xxxxx  dxxxxxxx", 12);
        this.addrow("xxxxxx   xxxxx  xxxxxxxx", 13);
        this.addrow("xxxxxx   xxxxx  xxxxxxxx", 14);
        this.addrow("xxxxxd             xxxxx", 15);
        this.addrow("xxxxxx                 x", 16);
        this.addrow("x        xdxxxxdx", 17);
        this.addrow("        xxxxxxxx  xdxxxx", 18);
        this.addrow("xxxxx   dxxxxxxd  xxxxxx", 19);
        this.addrow("xxxxxx  xxxxxxxx  xxxxxx", 20);
        this.addrow("xxxxxx  xxxxxxxx  xxxxxx", 21);
        this.addrow("xxxxxx  xxxxxxxx  xxxxxx", 22);
        this.addrow("xxxxxxx   xxxx   xxxxxxx", 23);
        this.addrow("xxxxxxxxx xxxx xxxxxxxxx", 24);
        
        this.addplayers(nplayers);
        System.out.println(this.playerstring());
        ArrayList<int[]> StartingPos = new ArrayList<int[]>();
        int[] coords = new int[]{1, 1}; // example coordinates
        StartingPos.add(coords); // Jack - get starting position coordinates
        for (int i = 0; i < StartingPos.size(); i ++) { // NOT WORKING UGH put in order on starting coordinates
            int[] pos = StartingPos.get(i);
            map[pos[0]][pos[1]] = (char) i;

        }
        System.out.println(this); // Jack - map string is wrong 

        
    }
    
    public void addplayers(int nplayers) {
        //add NPC players
        
        
        // add player controlled players with name input
        for (int i = 0; i < nplayers; i ++) {
             String playername = "default_player_" + i;
             System.out.println("Enter name for Player " + i + ":");
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             try {
             playername = input.readLine();
             } catch (IOException e) {
                continue;
             }
             System.out.println("Player " + i + " is " + playername + "!"); 
             Player temp = new Player(playername);
             players.add(temp.clone());
            }
        for (int i = 0; i < 6 - nplayers; i ++) {
            Player temp = new NPC();
            players.add(temp.clone());
        }

        for (int i = 0; i < 6; i++) {
            players.get(i).update();
        }
    }



    public void addrow(String row, int rowIndex) { // add rows to the map
        int counter = 0;
        for (char c: row.toCharArray()) {
            map[rowIndex][counter] = c;
            counter++;
        }
        if (counter < 23) {
            while (counter < 23) {
                counter ++;
                map[rowIndex][counter] = ' ';
            }
        }
        return;
    }

    public void moveplayer(int player) {
        Player play = players.get(player);
        boolean valid = false;
        String move = new String();
        while (valid == false) {
            System.out.println("Enter move: w for up, a for left, d for right, s for down:");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
            
            move = input.readLine();
            } catch (IOException e) {
            continue;
            }
            String[] validmoves = new String[]{"w", "a", "s", "d"};
            for (String validmove: validmoves) {
                if (move == validmove) valid = true;
            }
        switch (move) {
            case ("w"):
                if (player.xPos > 0) {
                    if (checkCollision(player.xPos - 1, player.yPos)) {
                        player.xPos--;
                        valid = true;
                    }
                    else System.out.println();
                }
                else System.out.println("Move invalid. You are already at the top of the board.");
            case ("a"):
                if (player.ypox > 0) {
                    player.yPos--;
                    valid = true;
                }
                else System.out.println("Move invalid. You are already at the top of the board.");
            case ("s"):
            case ("d"):


        }
        }
        player.xPos
        player.yPos
    } 

    public boolean checkCollision(int x, int y) {
        if (map[x][y])
        return true; // placeholder
    }

    public String playerstring() {
        String output = "Number of NPCs: " + (6 - nplay);
        output += "\n" + "Real Players:\n";
        for (int i =0; i < nplay; i ++) {
            output += players.get(i) + "\n";
        }
        return output;
    }

//Gant Section Start

    public void dealCards() {
        String[] weaponCards = {"Candlestick", "Knife", "Lead Pipe", "Pistol", "Rope", "Wrench"};
        String[] roomCards = {"Ball Room", "Billiard Room", "Conservatory", "Dining Room", "Hall", "Kitchen", "Library", "Lounge", "Study"};
        for (int i = 0; i < nplay; i++) {
            cards.add(players.get(i).name);
        }
        for (int i = 0; i < 15; i++) { 
            if (i < 6) {
                cards.add(weaponCards[i]);
            } else {
                cards.add(roomCards[i]);
            }
        }
        ArrayList<String> temp = new ArrayList<String>();
        for (int c = 0; c < cards.size(); c++) {
            temp.add(cards.get(c));
        }
        int x = (int) (Math.random() * 6);
        answer[0] = temp.get(x);
        temp.remove(x);
        x = (int) (Math.random() * 11) + 5;
        answer[1] = temp.get(x);
        temp.remove(x);
        x = (int) (Math.random() * 19) + 11;
        answer[2] = temp.get(x);
        temp.remove(x);
        for (int i = 0; i < temp.size(); i++) {
            x = (int) (Math.random() * temp.size());
            players.get(i / 3).hand.add(temp.get(x));
            temp.remove(x);
        }
        for (int i = 0; i < 6; i++) {
            players.get(i).card.update();
        }
    }

//Gant Section End

    public String toString() {
        String output = "Current Map: \n------------------------\n";
        for (char[] row: map) {
            for (char c : row) {
                output += c;
            }
            output += "\n";
        }
        output += "------------------------\n";
        return output;
    }

}
