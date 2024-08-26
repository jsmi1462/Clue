import java.util.*;
import java.io.*;

public class map {
    public char[][] map = new char[25][23];
    public ArrayList<Player> players = new ArrayList<Player>();
    public HashMap<int[], Room> doors = new HashMap<int[], Room>();


    public map (int nplayers) throws IOException {
        //add map
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

        //add NPC players
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
                map[rowIndex][counter] = ' ';
            }
        }
        return;
    }
    public String toString() {
    }

}
