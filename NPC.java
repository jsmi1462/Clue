import java.util.*;


public class NPC extends Player {
    public Scanner input = new Scanner(System.in);
    public int roll;
    public int xPos;
    public int yPos;
    public Room currentRoom;
    public String name;
    public ArrayList<String> hand;
    public Player nextplayer;
    public map currMap;
    public Room currTarget;
    public ArrayList<Character> currPath;
    public boolean isNPC; 
    public Scorecard card;
    public String answerroom;
    public String answerplayer;
    public String answerweapon;

    public NPC (String n) {
        currentRoom = null;
        name = n;
        isNPC = true;
        hand = new ArrayList<String>();
        guesses = new ArrayList<String>();
    }

    public int findPath(int moves, Room room) {
        ArrayList<map.coordinate> targetCoords = new ArrayList<>();
        for (Map.Entry<map.coordinate, Room> entry : currMap.doors.entrySet()) {
            if (entry.getValue() == room) {
                targetCoords.add(entry.getKey());
            }
        }
        int minmoves = 500;
        int[] mincoords = new int[2];
        for (map.coordinate targetCoord: targetCoords) {
            int targetx = targetCoord.x();
            int targety = targetCoord.y();
            ArrayList<int[]> forbfs = new ArrayList<int[]>(); // argh
            String path = BFS(0, "", targetx, targety, xPos, yPos, forbfs);
            if (path.length() < minmoves) {
                minmoves = path.length();
                mincoords = new int[] {targetCoord.x(), targetCoord.y()};
            }
        }
        ArrayList<int[]> forbfs = new ArrayList<int[]>(); // argh
        String bestpath = BFS(0, "", mincoords[0], mincoords[1], xPos, yPos, forbfs);
        for (int i = 0; i < bestpath.length(); i++) {
            currPath.add(bestpath.charAt(i));
        }
        return currPath.size();
    }
    public void pathfindMain(int moves) {
        int bestvalue = calcRoomValue(moves, currMap.rooms.get(0));
        
        Room bestroom = currMap.rooms.get(0);
        for (Room room: currMap.rooms) {
            int thisvalue = calcRoomValue(moves, room);
            if (thisvalue > bestvalue) {
                bestvalue = thisvalue;
                bestroom = room;
            }
        }
        currTarget = bestroom;
        findPath(moves, bestroom);
    }
    public int calcRoomValue(int moves, Room room) {
        int thisvalue = 0;
        int distance = findPath(moves, room) - moves;
        int infinity = 1000000;
        distance = (int) (distance + 6) / 7;
        thisvalue -= (distance * 3);

        if (answerroom.equalsIgnoreCase(room.toString())) return thisvalue;

        switch(card.checkCard("Room", room.toString())) {
            case (0 - 1): 
                return thisvalue;
            case 0:
                if (answerroom != null) return thisvalue + 100;
                return thisvalue - (infinity) / 2;
            case 1: 
                return thisvalue - infinity;
        }
        return (0 - 4 + card.checkCard("Room", room.toString()));
    }

    public String findbestweapon() {
        double bestvalue = calcweaponvalue("Candlestick");
        String bestweapon = "Candlestick";
        for (String weapon: new String[]{"Candlestick", "Knife", "Lead Pipe", "Pistol", "Rope", "Wrench"}) {
            double thisvalue = calcweaponvalue(weapon);
            if (thisvalue > bestvalue) {
                bestvalue = thisvalue;
                bestweapon = weapon;
            }

        }
        return bestweapon;

    }

    public double calcweaponvalue (String weapon) 
        {
            int negativeInf= -1000000;
            int b = 4;
            int c = 1;
            switch (card.checkCard("Weapon", weapon)) {
                case(0):
                    if (card.checkCard("rooms", currentRoom.toString()) == -1) return (-negativeInf);
                    return (-0.1);
                case (1):
                    return negativeInf;
            }
            return (card.checkCard("Weapon", weapon) -2) * c - b;
        } 

    public String findbestperson() {
        ArrayList<String> names = new ArrayList<>();

        for (Player p: currMap.players) {
            names.add(p.name);
        }

        double bestvalue = calcpersonvalue(names.get(0));
        String bestname = names.get(0);
        for (String name: names) {
            double thisvalue = calcpersonvalue(name);
            if (thisvalue > bestvalue) {
                bestvalue = thisvalue;
                bestname = name;
            }

        }
        return bestname;

    }

    public double calcpersonvalue (String person) 
        {
            int negativeInf= -1000000;
            int b = 4;
            int c = 1;
            switch (card.checkCard("Weapon", person)) {
                case(0):
                    if (card.checkCard("rooms", currentRoom.toString()) == -1) return (-negativeInf);
                    return (-0.1);
                case (1):
                    return negativeInf;
            }
            return (card.checkCard("Weapon", person) -2) * c - b;
        } 

    @Override
    public void guess() {
          Room guessedRoom = currTarget; // note to self - wtf is this
           
    }
    public String BFS(Integer length, String pathsofar, int targetx, int targety, int currx, int curry, ArrayList<int[]> visited) {
        Queue<Triplet<Integer, String, int[]>> q = new LinkedList<Triplet<Integer, String, int[]>>(); // hate me for this if you want, i can't find another way without changing too many map methods........
        q.add(new Triplet<Integer, String, int[]>(length, pathsofar, new int[]{currx, curry})); // BFS queue keeping track of path and integer
        while (!q.isEmpty()) {
            Triplet<Integer, String, int[]> curr = q.poll();
            visited.add(curr.c);
            if (curr.c.equals(new int[]{targetx, targety})) {
                return curr.b;
            }
            char[] directions = {'w', 'a', 's', 'd'};
            for (char direction: directions) {
                if (checkMoveValidity(currx, curry, direction))
                {
                    switch (direction) {
                        case ('w'):
                            q.add(new Triplet<Integer, String, int[]>(curr.a + 1, curr.b + direction, new int[]{curr.c[0] - 1, curr.c[1]}));
                            break;
                        case ('a'):
                            q.add(new Triplet<Integer, String, int[]>(curr.a + 1, curr.b + direction, new int[]{curr.c[0], curr.c[1] - 1}));
                            break;
                        case ('s'):
                            q.add(new Triplet<Integer, String, int[]>(curr.a + 1, curr.b + direction, new int[]{curr.c[0] + 1, curr.c[1]}));
                            break;
                        case ('d'):
                            q.add(new Triplet<Integer, String, int[]>(curr.a + 1, curr.b + direction, new int[]{curr.c[0], curr.c[1] + 1}));
                            break;
                    }
                }
            } 
        }
        return pathsofar; // placeholder impossible case

        
    }

    public boolean checkMoveValidity(int currx, int curry, char direction) {
        switch (direction) {
            case ('w'):
                if (currx == 0) {
                    return false;
                }
                return !currMap.checkCollisionNPC(this, currx - 1, curry);
            case ('a'):
                if (curry == 0) {
                    return false;
                }
                return !currMap.checkCollisionNPC(this, currx, curry - 1);
            case ('s'):
                if (currx == 24) {
                    return false;
                }
                return !currMap.checkCollisionNPC(this, currx + 1, curry);

            case ('d'):
                if (curry == 23) {
                    return false;
                }
                return !currMap.checkCollisionNPC(this, currx, curry + 1);      
        }
        return false;
    }
    
    @Override
    public NPC clone() {
        NPC n = new NPC(this.name);
        return n;
    }
    public String toString() {
        return this.name;
    }

    private record Triplet<A, B, C>(A a, B b, C c) {}
}