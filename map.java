import java.util.*;
import java.io.*;

public class map {
    public char[25][23] map;
    public ArrayList<Player> players;
    public HashMap<int[2], Room> doors;


    public map (int nplayers) throws IOException {
        players = new ArrayList<Player>();
        doors = new HashMap<int[2], Room>();
        
        this.addrow("xxxxxxx xxxxxxxx x");
        this.addrow("");
        for (int i = 0; i < 6 - nplayers; i ++) {
            Player temp = new NPC();
            players.add(temp);
        }
        // add player controlled players with name input
        for (int i = 0; i < nplayers; i ++) {
             boolean stop = false;
             while (stop == false) {
             System.out.println("Enter name for player" + i + ":");
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             String playername = input.readLine();
             System.out.println("Confirm player name is " + playername + ": (Y/N)"); //make sure the player name is correct
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             String response = input.readLine();
             if (response == "Y") {
             Player temp = new Player(playername);
             players.add(temp);
             stop == True
             }
             }
        }
    }
    
    
    public void addrow(String row, int rowIndex) {
        int counter = 0;
        for (char c: row.toCharArray()) {
            map[rowIndex][counter] = c;
            counter++;
        }
        if (counter < 22) {
            while (counter < 22) {
                counter ++;
                map[rowIndex][counter] = 'x';
            }
        }
        return;
    }
    public String toString() {
    }

}
