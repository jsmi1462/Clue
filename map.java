import java.util.*;
import java.io.*;

public class map {
    public char[][] map = new char[25][24]; // hardcoded map
    public ArrayList<Player> players = new ArrayList<Player>(); // list of players, will need to add some sort of method in map that implements the cll............
    public HashMap<int[], Room> doors = new HashMap<int[], Room>(); // doors linked to rooms, the reverse will be annoying unless we store player position inside door....
    int nplay; // there has to be a better way this is stupid


    public map (int nplayers) { // constructor builds the map and adds the players to the map after input
        //add map
        this.nplay = nplayers;
        this.addrow("xxxxxxx xxxxxxxx xxxxxxx", 0);
        this.addrow("xxxxxxx  xxxxxx  xxxxxxx", 1);
        this.addrow("xxxxxxx  xxxxxx  xxxxxxx", 2);
        this.addrow("xxxxxxx  xxxxxx  xxxxxxx", 3);
        this.addrow("x        dxxxxx  xxxxxxx", 4);
        this.addrow("         xxxxxx  xxxxxxx", 5);
        this.addrow("xxxxxx   xxddxx        x", 6);
        this.addrow("xxxxxxx", 7);
        this.addrow("xxxxxxd  xxxxx         x", 8);
        this.addrow("xxxxxxx  xxxxx  xxxxxxxx", 9);
        this.addrow("xxxxxx   xxxxx  xxxxxxxx", 10);
        this.addrow("x        xxxxx  xxxxxxxx", 11);
        this.addrow("xxxxxx   xxxxx  xxxxxxxx", 12);
        this.addrow("xxxxxx   xxxxx  xxxxxxxx", 13);
        this.addrow("xxxxxx   xxxxx  xxxxxxxx", 14);
        this.addrow("xxxxxx             xxxxx", 15);
        this.addrow("xxxxxx                 x", 16);
        this.addrow("x        xxxxxxxx", 17);
        this.addrow("         xxxxxxxx xxxxxx", 18);
        this.addrow("xxxxx    xxxxxxxx xxxxxx", 19);
        this.addrow("xxxxxx   xxxxxxxx xxxxxx", 20);
        this.addrow("xxxxxx   xxxxxxxx xxxxxx", 21);
        this.addrow("xxxxxx   xxxxxxxx xxxxxx", 22);
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
             players.add(temp);
            }
        for (int i = 0; i < 6 - nplayers; i ++) {
            Player temp = new NPC();
            players.add(temp);
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
    public String playerstring() {
        String output = "Number of NPCs: " + (6 - nplay);
        output += "\n" + "Real Players:\n";
        for (int i =0; i < nplay; i ++) {
            output += players.get(i) + "\n";
        }
        return output;
    }
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
