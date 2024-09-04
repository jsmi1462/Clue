import java.util.*;
import java.io.*;

public class map {
    public char[][] map = new char[25][24]; // hardcoded map
    public ArrayList<Player> players = new ArrayList<Player>(); // list of players, will need to add some sort of method in map that implements the cll............
    public HashMap<int[], Room> doors = new HashMap<int[], Room>();

    public void assignDoorCoors()
    {
        HashMap<String, Room> rooms = new HashMap<String, Room>();
        String[] uniqueRoomNames = {"study", "hall", "lounge", "library", "dining room", "billard room",
        "ball room", "kitchen", "conservatory"};
        String[] roomNames = {"study", "hall", "lounge", "hall", "hall", "library", "dining room", "library", "billard room", "billard room",
            "ball room", "ball room", "kitchen", "ball room", "ball room"};
        int[][] doorCoors = {{3,6},{4,9},{5,17},{6,10},{6,11},{8,6},{9,17},{10,3},{12,1},{15,6},{17,10},{17,15},{18,19},{19,8},{19,15}};
        for (int i = 0; i < uniqueRoomNames.length; i++)
        {
        rooms.put(uniqueRoomNames[i],new Room(uniqueRoomNames[i])); //names and rooms
        }
        for (int i = 0; i <doorCoors.length; i++)
        {
        doors.put(doorCoors[i], rooms.get(roomNames[i])); //coordinates and rooms
        }
    }
    
    // doors linked to rooms, the reverse will be annoying unless we store player position inside door....
    
    
    
    //doors.put([3,6], study);
    //doors.put([4,9], hall);
    //doors.put([5,17], lounge);
    //doors.put([6,10], hall);
    //doors.put([6,11], hall);
    //doors.put([8,11], study);
    //doors.put([3,6], study);
    //doors.put([3,6], study);
    //doors.put([3,6], study);

    
    
    
    
    int nplay; // there has to be a better way this is stupid

    
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
        while (valid == false) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
        String move = input.readLine();
        } catch (IOException e) {
        continue;
        }
        }
        switch (move) {

        }
        player.xPos
        player.yPos
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
