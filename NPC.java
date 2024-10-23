import java.util.*;


public class NPC extends Player {
    public int roll;
    public Room currTarget;
    public ArrayList<Character> currPath;
    public ArrayList<ArrayList<String>> revealedcards; 

    public NPC (String n, map m, int absindex) {
        absoluteindex = absindex;
        currentRoom = null;
        name = n;
        map = m;
        isNPC = true;
        hand = new ArrayList<String>();
        guesses = new ArrayList<String>();
        revealedcards = new ArrayList<ArrayList<String>>();

    }

    @Override
    public void update() {
        card = new Scorecard(this);
    }


    public String revealcard(int player, String... strings) 
    {
        String cardtoreveal = "";
        revealedcards.get(player).add(cardtoreveal);
        System.out.println("NPC " + name + " revealed name " + cardtoreveal + " to you.");
        return cardtoreveal;
    }

    public int findPath(int moves, Room room) {
        System.out.println("Finding path to room " + room);
        currPath = new ArrayList<Character>();
        ArrayList<map.coordinate> targetCoords = new ArrayList<>();
        for (Map.Entry<map.coordinate, Room> entry : map.doors.entrySet()) {
            if (entry.getValue() == room) {
                targetCoords.add(entry.getKey());
            }
        }
        int minmoves = 500;
        int[] mincoords = new int[2];
        for (map.coordinate targetCoord: targetCoords) {
            int targetx = targetCoord.x();
            int targety = targetCoord.y();
            ArrayList<coordinate> forbfs = new ArrayList<>(); // argh
            System.out.println("Trying path to " + targetx + "," + targety);
            String path = BFS(0, "", targetx, targety, xPos, yPos, forbfs);
            System.out.println("Found path " + path + " to " + targetx + "," + targety);
            if (path.length() < minmoves) {
                minmoves = path.length();
                mincoords = new int[] {targetCoord.x(), targetCoord.y()};
            }
        }
        ArrayList<coordinate> forbfs = new ArrayList<>(); // argh
        String bestpath = BFS(0, "", mincoords[0], mincoords[1], xPos, yPos, forbfs);
        System.out.println("The best path has been determined to be " + bestpath);
        for (int i = 0; i < bestpath.length(); i++) {
            currPath.add(bestpath.charAt(i));
        }
        return currPath.size();
    }

    public void pathfindMain(int moves) {
        int bestvalue = calcRoomValue(moves, map.rooms.get(0));
        int infinity = 1000000;
        Room bestroom = map.rooms.get(0);
        for (Room room: map.rooms) {
            int thisvalue = calcRoomValue(moves, room);
            if (thisvalue > bestvalue) {
                bestvalue = thisvalue;
                bestroom = room;
            }
        }
        currTarget = bestroom;
        System.out.println("The target room is " + bestroom);
        findPath(moves, bestroom);
    }
    public int calcRoomValue(int moves, Room room) {
        int thisvalue = 0;
        int distance = findPath(moves, room) - moves;
        distance = (int) (distance + 6) / 7;
        thisvalue -= (distance * 3);
        System.out.println("Room " + room + " has value "  + moves);
        // if room unknown, done
        // if room = yours, 100 if ans, -inf/2 if no ans
        // if room = next in line, -infty, etc.
        return thisvalue;
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

        for (Player p: map.players) {
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

    public boolean checkifinarray(ArrayList<coordinate> visited, coordinate c) {
        for (int i =0; i < visited.size(); i ++) {
            if (visited.get(i).equals(c)) {
                return true;
            }
        }
        return false;
    }
    public String BFS(Integer length, String pathsofar, int targetx, int targety, int currx, int curry, ArrayList<coordinate> visited) {
        Queue<Triplet<Integer, String, coordinate>> q = new LinkedList<Triplet<Integer, String, coordinate>>(); // hate me for this if you want, i can't find another way without changing too many map methods........
        q.add(new Triplet<Integer, String, coordinate>(length, pathsofar, new coordinate(currx, curry))); // BFS queue keeping track of path and integer
        while (!q.isEmpty()) {
            Triplet<Integer, String, coordinate> curr = q.poll();
            System.out.println("NPC testing moving to " + curr.c.x() + "," + curr.c.y());
            visited.add(curr.c);

            if (curr.c.equals(new coordinate(targetx, targety))) {
                return curr.b;
            }

            char[] directions = {'w', 'a', 's', 'd'};
            coordinate newloc;
            for (char direction: directions) {
                
                if (checkMoveValidity(curr.c.x(), curr.c.y(), direction))
                {
                    switch (direction) {
                        case ('w'):
                            newloc = new coordinate(curr.c.x() - 1, curr.c.y());
                            if (checkifinarray(visited, newloc)) {
                                q.add(new Triplet<Integer, String, coordinate>(curr.a + 1, curr.b + Character.toString(direction), newloc));
                            }
                            break;
                        case ('a'):
                            newloc = new coordinate(curr.c.x(), curr.c.y() - 1);
                            if (checkifinarray(visited, newloc)) {
                                q.add(new Triplet<Integer, String, coordinate>(curr.a + 1, curr.b + Character.toString(direction), newloc));
                            }
                            break;
                        case ('s'):
                            newloc = new coordinate(curr.c.x() + 1, curr.c.y());
                            if (checkifinarray(visited, newloc)) {
                                q.add(new Triplet<Integer, String, coordinate>(curr.a + 1, curr.b + Character.toString(direction), newloc));
                            }
                            break;
                        case ('d'):
                            newloc = new coordinate(curr.c.x(), curr.c.y() + 1);
                            if (checkifinarray(visited, newloc)) {
                                q.add(new Triplet<Integer, String, coordinate>(curr.a + 1, curr.b + Character.toString(direction), newloc));
                            }
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
                return !map.checkCollisionNPC(this, currx - 1, curry);
            case ('a'):
                if (curry == 0) {
                    return false;
                }
                return !map.checkCollisionNPC(this, currx, curry - 1);
            case ('s'):
                if (currx == 24) {
                    return false;
                }
                return !map.checkCollisionNPC(this, currx + 1, curry);

            case ('d'):
                if (curry == 23) {
                    return false;
                }
                return !map.checkCollisionNPC(this, currx, curry + 1);      
        }
        return false;
    }
    
    @Override
    public void printHand() {
        System.out.println("current NPC has hand" + hand);
    }
    
    @Override
    public NPC clone() {
        System.out.println("Cloning NPC with hand " + this.hand);
        NPC n = new NPC(this.name, map, absoluteindex);
        n.hand = this.hand;
        n.guesses = guesses;
        n.card = card;
        return n;
    }
    public String toString() {
        return this.name;
    }


    public record coordinate(int x, int y) {

        @Override 
        public boolean equals(Object o) {
            if (this == o) return true;
            else if (this.getClass() != o.getClass()) return false;
            coordinate o2 = (coordinate) o;
            return (this.x == o2.x) && (this.y == o2.y);
        }

        @Override
        public int hashCode() {
            return 25 * x + y;
        }
    }

    private record Triplet<A, B, C>(A a, B b, C c) {}
}