import java.util.*;
import java.io.*;

public class map {
    public char[][] map = new char[25][24]; // hardcoded map
    public ArrayList<Player> players = new ArrayList<Player>(); // list of players, will need to add some sort of method in map that implements the cll............
    public HashMap<int[], Room> doors = new HashMap<int[], Room>(); // doors linked to rooms, the reverse will be annoying unless we store player position inside door....
    public int nplay; // there has to be a better way this is stupid
    public boolean gameover;
    public ArrayList<String> answer;
    
    
    public map (int nplayers) { // constructor builds the map and adds the players to the map after input
        //add map
        createRooms();
        gameover = false;
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
        StartingPos.add(new int[]{1, 1}); // Jack - get starting position coordinates
        for (int i = 0; i < StartingPos.size(); i ++) { // NOT WORKING UGH put in order on starting coordinates
            int[] pos = StartingPos.get(i);
            map[pos[0]][pos[1]] = (char) i;
        }
        System.out.println(this); // Jack - map string is wrong 

        
    }
    
    public void createRooms () {

    }
    public void addplayers (int nplayers) {
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
        linkplayers();
    }

    public void linkplayers() {
        for (int i = 0; i < players.size() - 1; i ++) {
            players.get(i).nextplayer = players.get(i+ 1);
        }
        players.get(players.size() - 1).nextplayer = players.get(0);
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

    public boolean movenpc (int player) {
        NPC npc = (NPC) players.get(player);
        npc.findPath();
        char currmove = npc.currPath.get(0);
        switch (currmove) {
            case ('w'):
                npc.xPos--;
            case ('a'):
                npc.yPos--;
            case ('s'):
                npc.xPos++;
            case ('d'):
                npc.yPos++;
        }
        if (map[npc.xPos][npc.yPos] == 'd') {
            enterRoomNPC(npc, npc.xPos, npc.yPos);
            return false;
        }
        else return true; // filler b/c npc never makes wrong moves
    }

    public boolean moveplayer(int player) {
        if (players.get(player).isNPC) {
            return movenpc(player);
        }
        Player play = players.get(player);
        
        boolean valid = false;
        String move = new String();
        while (valid == false) {
            boolean amove = false;
            while (amove == false) {
            System.out.println("Enter move: w for up, a for left, d for right, s for down:");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
            
            move = input.readLine();
            } catch (IOException e) {
            continue;
            }
            String[] validmoves = new String[]{"w", "a", "s", "d"};
            for (String validmove: validmoves) {
                if (move == validmove) amove = true;
            }
            }
        switch (move) {
            case ("w"):
                if (play.xPos > 0) {
                    if (!checkCollision(play, play.xPos - 1, play.yPos)) {
                        play.xPos--;
                        valid = true;
                    }
                    else System.out.println("Move invalid.");
                }
                else System.out.println("Move invalid. You are already at the top of the board.");
            case ("a"):
                if (play.yPos > 0) {
                    if (!checkCollision(play, play.xPos - 1, play.yPos)) {
                        play.yPos--;
                        valid = true;
                    }
                    else System.out.println("Move invalid.");
                }
                else System.out.println("Move invalid. You are already at the left edge of the board.");
            case ("s"):
                if (play.xPos < 24) {
                    if (!checkCollision(play, play.xPos + 1, play.yPos)) {
                        play.xPos++;
                        valid = true;
                    }
                    else System.out.println("Move invalid.");
                }
                else System.out.println("Move invalid. You are already at the bottom of the board.");
            case ("d"):
                if (play.yPos < 23) {
                    if (!checkCollision(play, play.xPos + 1, play.yPos)) {
                        play.yPos++;
                        valid = true;
                    }
                    else System.out.println("Move invalid.");
                }
                else System.out.println("Move invalid. You are already at the right edge of the board.");
        }
        }
        if (map[play.xPos][play.yPos] == 'd') return false;
        else return true;
    } 

    public boolean checkCollision(Player p, int x, int y) {
        if (map[x][y] == ' ') return false;
        else if (map[x][y] == 'x') {
            System.out.println("You cannot move into walls.");
            return true;
        }
        else if (map[x][y] == 'd') {
            enterRoom(p, x, y);
            return false;
        }
        return true;
    }
    public boolean checkCollisionNPC(int x, int y) {
        if (map[x][y] == ' ') return false;
        else if (map[x][y] == 'x') {
            return true;
        }
        else if (map[x][y] == 'd') {
            return false;
        }
        return true;
    }

    public void enterRoomNPC(NPC p, int x, int y) {
        int[] currcoords = new int[]{x, y};
        Room roomtoenter = doors.get(currcoords);
        p.currentRoom = roomtoenter;
        p.guess();
        return;
    }

    public void enterRoom(Player p, int x, int y) {
        int[] currcoords = new int[]{x, y};
        Room roomtoenter = doors.get(currcoords);
        System.out.println("Entering room " + roomtoenter.name + ".");
        p.currentRoom = roomtoenter;
        p.guess();
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
        char[][] printmap = map.clone();
        for (int i = 0; i < players.size(); i ++) {
            printmap[players.get(i).xPos][players.get(i).yPos] = (char) i;
        }
        String output = "Current Map: \n------------------------\n";
        for (char[] row: printmap) {
            for (char c : row) {
                output += c;
            }
            output += "\n";
        }
        output += "------------------------\n";
        return output;
    }

}
