import java.util.*;
import java.io.*;
import javax.print.attribute.standard.PrinterMessageFromOperator;

public class map {
    public char[][] map = new char[25][24]; // hardcoded map
    public ArrayList<Player> players = new ArrayList<Player>(); // list of players, will need to add some sort of method in map that implements the cll............
    public HashMap<int[], Room> doors = new HashMap<int[], Room>(); // doors linked to rooms, the reverse will be annoying unless we store player position inside door....
    public int nplay; // there has to be a better way this is stupid
    public String[] answer = new String[3];
    public ArrayList<String> cards = new ArrayList<String>();
    public boolean gameover;
    
    public map (int nplayers) { // constructor builds the map and adds the players to the map after input
        //add map
        assignDoorCoors();
        gameover = false;
        this.nplay = nplayers;
        this.addrow("xxxxxxx xxxxxxxx xxxxxxx", 0);
        this.addrow("x     x  x    x  x     x", 1);
        this.addrow("x     x  x    x  x     x", 2);
        this.addrow("xxxxxxd  x    x  x     x", 3);
        this.addrow("x        d    x  x     x", 4);
        this.addrow("         x    x  dxxxxxx", 5);
        this.addrow("xxxxxx   xxddxx        x", 6);
        this.addrow("x     x", 7);
        this.addrow("x     d  xxxxx         x", 8);
        this.addrow("x     x  xxxxx  xdxxxxxx", 9);
        this.addrow("xxxdxx   xxxxx  x      x", 10);
        this.addrow("x        xxxxx  x      x", 11);
        this.addrow("xdxxxx   xxxxx  d      x", 12);
        this.addrow("x    x   xxxxx  x      x", 13);
        this.addrow("x    x   xxxxx  xxx     ", 14);
        this.addrow("x    d             xxxxx", 15);
        this.addrow("xxxxxx                 x", 16);
        this.addrow("x       xdxxxxdx", 17);
        this.addrow("        x      x  xdxxxx", 18);
        this.addrow("xxxxx   d      d  x    x", 19);
        this.addrow("x    x  x      x  x    x", 20);
        this.addrow("x    x  x      x  x    x", 21);
        this.addrow("x    x  xx    xx  x    x", 22);
        this.addrow("xxxxxxx   xxxx   xxxxxxx", 23);
        this.addrow("xxxxxxxxx xxxx xxxxxxxxx", 24);
        this.addplayers(nplayers);
        System.out.println(this.playerstring());
        int[][] StartingPos = {{0, 16}, 
            {5, 0},
            {7, 23},
            {18, 0},
            {24, 9},
            {24, 14}};
        for (int i = 0; i < StartingPos.length; i ++) { 
            int[] pos = StartingPos[i];
            players.get(i).xPos = pos[0];
            players.get(i).yPos = pos[1];
        }
        System.out.println(this); // Jack - map string is wrong 

        
    }
    
    public void assignDoorCoors() {
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

    public void addplayers(int nplayers) {
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
            players.add(temp.cloneName());
        }

        //add NPC
        ArrayList<String> npcnames = new ArrayList<String>();
        for (String n : new String[]{"Billy Bob Joe", "Jackson Grant", "Mr. Smith", "Mr. Gannon"}) {
            npcnames.add(n);
        }

        for (int i = 0; i < 6 - nplayers; i ++) {
            String npcname = npcnames.remove((int) Math.random()* (npcnames.size()));
            NPC temp = new NPC(npcname);
            System.out.println("NPC " + i + " is " + temp + "!");
            players.add(temp.cloneName());
        }
        linkplayers();
    }

    public void linkplayers() {
        for (int i = 0; i < players.size() - 1; i ++) {
            players.get(i).nextPlayer = players.get(i+ 1);
        }
        players.get(players.size() - 1).nextPlayer = players.get(0);
    }



    public void addrow(String row, int rowIndex) { // add rows to the map
        int counter = 0;
        for (char c: row.toCharArray()) {
            map[rowIndex][counter] = c;
            counter++;
        }
        if (counter < 24) {
            while (counter < 24) {
                map[rowIndex][counter] = ' ';
                counter ++;
            }
        }
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
            System.out.println("NPC " + players.get(player) + " is moving!");
            return movenpc(player);
        }

        Player play = players.get(player);
        
        boolean valid = false;
        String move = new String();
        while (valid == false) {
            //boolean amove = false;
            //while (amove == false) {
            System.out.println("Enter move: w for up, a for left, d for right, s for down:");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
            move = input.readLine();
            } catch (IOException e) {
            continue;
            }
            //String[] validmoves = new String[]{"w", "a", "s", "d"};
            //for (String validmove: validmoves) {
            //    amove = (move.equals(validmove));
            //}
            //}
            switch (move) {
                case "w" -> {
                    if (play.xPos > 0) {
                        if (!checkCollision(players.get(player), play.xPos - 1, play.yPos)) {
                            System.out.println("You moved up.");
                            players.get(player).xPos--;
                            valid = true;
                        }
                        else System.out.println("Move invalid.");
                    }
                    else System.out.println("Move invalid. You are already at the top of the board.");
                }
                case "a" -> {
                    if (play.yPos > 0) {
                        if (!checkCollision(players.get(player), play.xPos, play.yPos - 1)) {
                            System.out.println("You moved left.");
                            players.get(player).yPos--;
                            valid = true;
                        }
                        else System.out.println("Move invalid.");
                    }
                    else System.out.println("Move invalid. You are already at the left edge of the board.");
                }
                case "s" -> {
                    if (play.xPos < 24) {
                        if (!checkCollision(players.get(player), play.xPos + 1, play.yPos)) {
                            System.out.println("You moved down.");
                            players.get(player).xPos++;
                            valid = true;
                        }
                        else System.out.println("Move invalid.");
                    }
                    else System.out.println("Move invalid. You are already at the bottom of the board.");
                }
                case "d" -> {
                    if (play.yPos < 23) {
                        if (!checkCollision(players.get(player), play.xPos, play.yPos + 1)) {
                            System.out.println("You moved right.");
                            players.get(player).yPos++;
                            valid = true;
                        }
                        else System.out.println("Move invalid.");
                    }
                    else System.out.println("Move invalid. You are already at the right edge of the board.");
                }
            }
        }
        return map[play.xPos][play.yPos] != 'd';
    } 

    public boolean checkCollision(Player p, int x, int y) {
        System.out.println("Index  " + x +  " " + y + " is a " + map[x][y]);
        if (map[x][y] == ' ') {
            return false;
        } else if (map[x][y] == 'x') {
            System.out.println("You cannot move into walls.");
            return true;
        } else if (map[x][y] == 'd') {
            enterRoom(p, x, y);
            return false;
        }
        return false;
    }

    public boolean checkCollisionNPC(NPC npc, int x, int y) {
        return switch (map[x][y]) {
            case 'x' -> true;
            case 'd' -> {
                enterRoomNPC(npc, x, y);
                yield false;
            }
            default -> false;
        };
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

    @Override
    public String toString() {
        String output = "Current Map: \n------------------------\n";

        char[][] printmap = new char[25][24];
        for (int i =0; i < map.length; i ++) {
            for (int j = 0; j < map[i].length; j++) {
                printmap[i][j] = map[i][j];
            }
        }
        ArrayList<int[]> changesquares = new ArrayList<int[]>();
        for (int i = 0; i < players.size(); i ++) {
            changesquares.add(new int[]{players.get(i).xPos, players.get(i).yPos});
        }
        for (int i = 0; i < changesquares.size(); i ++) {
            printmap[changesquares.get(i)[0]][changesquares.get(i)[1]] = (char)('0' + i);
        }
        
        for (int i = 0; i < printmap.length; i ++) {
            for (int j = 0; j < printmap[i].length; j++) {
                output += printmap[i][j];
            }
            output += "\n";
        }
        output += "------------------------\n";
        return output;
    }

}
