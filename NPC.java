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
    public boolean isNPC = true; 

    public NPC (String n) {
        currentRoom = null;
        name = n;
    }

    public void findPath() {
        ArrayList<int[]> targetCoords = new ArrayList<int[]>();
        for (Map.Entry<int[], Room> entry : currMap.doors.entrySet()) {
            if (entry.getValue() == currTarget) {
                targetCoords.add(entry.getKey());
            }
        }
        int minmoves = 500;
        int[] mincoords = new int[2];
        for (int[] targetCoord: targetCoords) {
            int targetx = targetCoord[0];
            int targety = targetCoord[1];
            ArrayList<int[]> forbfs = new ArrayList<int[]>(); // argh
            String path = BFS(0, "", targetx, targety, xPos, yPos, forbfs);
            if (path.length() < minmoves) {
                minmoves = path.length();
                mincoords = targetCoord;
            }
        }
        ArrayList<int[]> forbfs = new ArrayList<int[]>(); // argh
        String bestpath = BFS(0, "", mincoords[0], mincoords[1], xPos, yPos, forbfs);
        for (int i = 0; i < bestpath.length(); i++) {
            currPath.add(bestpath.charAt(i));
        }
        return;
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
                        case ('a'):
                            q.add(new Triplet<Integer, String, int[]>(curr.a + 1, curr.b + direction, new int[]{curr.c[0], curr.c[1] - 1}));
                        case ('s'):
                            q.add(new Triplet<Integer, String, int[]>(curr.a + 1, curr.b + direction, new int[]{curr.c[0] + 1, curr.c[1]}));
                        case ('d'):
                            q.add(new Triplet<Integer, String, int[]>(curr.a + 1, curr.b + direction, new int[]{curr.c[0], curr.c[1] + 1}));
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
    public void findTarget() {
        String[] rooms = {"study", "hall", "lounge", "library", "dining room", "billard room", "kitchen", "ball room"};
        for (String room: rooms) {
            if (!roomsGuessed.contains(room)) {
            }
        }
    }

    private record Triplet<A, B, C>(A a, B b, C c) {}
}