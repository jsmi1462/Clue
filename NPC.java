import java.util.*;


public class NPC extends Player {
    public int roll;
    public Room currTarget;
    public ArrayList<Character> currPath;
    public ArrayList<ArrayList<String>> revealedcards; 
    public String answerroom;
    public String answerweapon;
    public String answerperson;

    public NPC (String n, map m, int absindex) {
        absoluteIndex = absindex;
        currentRoom = null;
        name = n;
        map = m;
        isNPC = true;
        hand = new ArrayList<String>();
        guesses = new ArrayList<String>();
        revealedcards = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < 6; i++) {
            revealedcards.add(new ArrayList<>());
        }

    }

    @Override
    public void update() {
        card = new Scorecard(this);
    }
    public ArrayList<String> determineguess() {
        String weapon = findbestweapon();
        String person = findbestperson();
        ArrayList<String> guess = new ArrayList<>();
        guess.add(weapon);
        guess.add(person);
        return guess;
    }

    public String revealCard(int player, String[] strings) 
    {
        boolean found = false;
        String cardtoreveal = "";
        for (String s : strings) {
            if (revealedcards.get(player).indexOf(cardtoreveal) != -1) {
                cardtoreveal = s;
                found = true;
                break;
            }
        }
        for (String s : strings) {
            ArrayList<String> roomCards = new ArrayList<String>(Arrays.asList("Ball Room", "Billiard Room", "Conservatory", "Dining Room", "Hall", "Kitchen", "Library", "Lounge", "Study"));
            if (roomCards.indexOf(s) == -1) {
                cardtoreveal = s;
                found = true;
                break;
            }
        }
        if (!found) {
            cardtoreveal = strings[0];
        }

        revealedcards.get(player).add(cardtoreveal);
        return cardtoreveal;
    }

    public int findPath(int moves, Room room) {
        //System.out.println("Finding path to room " + room);
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
            //System.out.println("Trying path to " + targetx + "," + targety);
            String path = BFS(0, "", targetx, targety, xPos, yPos, forbfs);
            //System.out.println("Found path " + path + " to " + targetx + "," + targety);
            if (path.length() < minmoves) {
                minmoves = path.length();
                mincoords = new int[] {targetCoord.x(), targetCoord.y()};
            }
        }
        ArrayList<coordinate> forbfs = new ArrayList<>(); // argh
        String bestpath = BFS(0, "", mincoords[0], mincoords[1], xPos, yPos, forbfs);
        //System.out.println("The best path has been determined to be " + bestpath);
        for (int i = 0; i < bestpath.length(); i++) {
            currPath.add(bestpath.charAt(i));
        }
        return currPath.size();
    }

    public void pathfindMain(int moves) {
        int bestvalue = calcRoomValue(moves, map.rooms.get(0));
        Room bestroom = map.rooms.get(0);
        for (Room room: map.rooms) {
            int thisvalue = calcRoomValue(moves, room);
            if (thisvalue > bestvalue) {
                bestvalue = thisvalue;
                bestroom = room;
            }
        }
        currTarget = bestroom;
        //System.out.println("The target room is " + bestroom);
        findPath(moves, bestroom);
        input.nextLine();
    }
    public int calcRoomValue(int moves, Room room) {
        int thisvalue = 0;
        int distance = findPath(moves, room) - moves;
        distance = (int) (distance + 6) / 7;
        thisvalue -= (distance * 3);
        
        // if room unknown, done
        // if room = yours, 100 if ans, -inf/2 if no ans
        // if room = next in line, -infty, etc.

        int negativeInf= -1000000;
        int c = 1;
        int b = 4;
        //System.out.println(room);
        switch (card.checkCard("rooms", room.toString())) {
            case (-1):
                return thisvalue;
            case(0):
                if (room.toString().equals(answerroom)) return thisvalue + 100;
                return thisvalue + (negativeInf/2);
            case (1):
                return thisvalue + negativeInf;
        }
        //System.out.println("Room " + room + " has distance value " + thisvalue);
        thisvalue += (card.checkCard("rooms", room.toString()) -2) * c - b;
        //System.out.println("Room " + room + " has value "  + thisvalue);
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
            case(-1):
                return 0;
            case(0):
                //if (card.checkCard("rooms", currentRoom.toString()) == -1) return (-negativeInf);
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
            switch (card.checkCard("People", person)) {
                case(-1):
                    return 0;
                case(0):
                    //if (card.checkCard("rooms", currentRoom.toString()) == -1) return (-negativeInf);
                    return (-0.1);
                case (1):
                    return negativeInf;
            }
            return (card.checkCard("People", person) -2) * c - b;
        } 

    public boolean finalGuess() {
        return true;
    }

    @Override
    public void guess() {
          System.out.println(card);
          ArrayList<String> tempGuesses = new ArrayList<>();
          tempGuesses.add(this.findbestperson());
          tempGuesses.add(this.findbestweapon());
          tempGuesses.add(currentRoom.name);
          boolean hasCard = false;
          ArrayList<String> cardsHad = new ArrayList<>();
          System.out.println("Hi! I'm guessing" + tempGuesses.toString());
  
          for (int p = 1; p < 6; p++) {
              for (int tG = 0; tG < 3; tG++) { //tG = tempGuesses
                  for (int h = 0; h < 3; h++) {
                      // System.out.println("Guess: " + tempGuesses.get(tG) + " Hand: " + card.getPlayers(p).hand.get(h));
                      if (tempGuesses.get(tG).equalsIgnoreCase(card.getPlayers(p).hand.get(h))) {
                          hasCard = true;
                          System.out.println("Someone has a card that I guessed!");
                          cardsHad.add(tempGuesses.get(tG));
                      }
                  }
              }
              
              if (hasCard) {
                  String[] cardsHadArr = new String[cardsHad.size()];
                  for (int cH = 0; cH < cardsHad.size(); cH++) {
                      cardsHadArr[cH] = cardsHad.get(cH);
                  }
  
                  if (card.getPlayers(p).isNPC == false) {
                      System.out.println("\r\n" + name + ", please pass the screen to " + card.getPlayers(p).name + ".\r\n"
                          + card.getPlayers(p).name + ", please press enter to confirm that only you are looking at the screen.");
                      input.nextLine();
  
                      System.out.print("\r\nPlease enter the card you would like to show to " + name + " out of the following:\r\n");
                      for (int s = 0; s < cardsHad.size(); s++) {
                          System.out.print(cardsHad.get(s) + "\r\n");
                      }
  
                      String cardRevealed = this.inputCheck(input, "\r\nCard to be revealed: ", cardsHadArr);
                      if (tempGuesses.indexOf(cardRevealed) == 0) {
                          card.getPlayers(p).card.setPeople(cardRevealed, "X");
                      } else if (tempGuesses.indexOf(cardRevealed) == 1) {
                          card.getPlayers(p).card.setWeapons(cardRevealed, "X");
                      } else {
                          card.getPlayers(p).card.setRooms(cardRevealed, "X");
                      }
                      break;
                  } else {
                      NPC n = (NPC) card.getPlayers(p);
                      String cardNPCRevealed = n.revealCard(absoluteIndex, cardsHadArr);
                      System.out.println(cardNPCRevealed + " was revealed to me");
                      if (cardNPCRevealed.equalsIgnoreCase(tempGuesses.get(0))) {
                          card.getPlayers(p).card.setPeople(cardNPCRevealed, "X");
                      } else if (cardNPCRevealed.equalsIgnoreCase(tempGuesses.get(1))) {
                          card.getPlayers(p).card.setWeapons(cardNPCRevealed, "X");
                      } else {
                          card.getPlayers(p).card.setRooms(cardNPCRevealed, "X");
                      }
                      break;
                  }
              } else {
                  card.getPlayers(p).card.setPeople(tempGuesses.get(0), "O");
                  card.getPlayers(p).card.setWeapons(tempGuesses.get(1), "O");
                  card.getPlayers(p).card.setRooms(tempGuesses.get(2), "O");
              }
          }

    }

    public boolean checkifinarray(ArrayList<coordinate> visited, coordinate c) {
        for (coordinate thisc : visited) {
            if (thisc.equals(c)) {
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
            //System.out.println("NPC testing moving to " + curr.c);
            
            if (!checkifinarray(visited, curr.c)) {
                visited.add(curr.c);
            }
            else {
                continue;
            }
            
            if (curr.c.equals(new coordinate(targetx, targety))) {
                return curr.b;
            }

            

            char[] directions = {'w', 'a', 's', 'd'};
            coordinate newloc;
            for (char direction: directions) {
                switch (direction) {
                    case ('w'):
                        newloc = new coordinate(curr.c.x() - 1, curr.c.y());
                        if (newloc.equals(new coordinate(targetx, targety))) {
                            return curr.b + Character.toString(direction);
                        }
                        break;
                    case ('a'):
                        newloc = new coordinate(curr.c.x(), curr.c.y() - 1);
                        if (newloc.equals(new coordinate(targetx, targety))) {
                            return curr.b + Character.toString(direction);
                        }
                        break;
                    case ('s'):
                        newloc = new coordinate(curr.c.x() + 1, curr.c.y());
                        if (newloc.equals(new coordinate(targetx, targety))) {
                            return curr.b + Character.toString(direction);
                        }
                        break;
                    case ('d'):
                        newloc = new coordinate(curr.c.x(), curr.c.y() + 1);
                        if (newloc.equals(new coordinate(targetx, targety))) {
                            return curr.b + Character.toString(direction);
                        }
                        break;
                }
                if (checkMoveValidity(curr.c.x(), curr.c.y(), direction))
                {
                    switch (direction) {
                        case ('w'):
                            newloc = new coordinate(curr.c.x() - 1, curr.c.y());
                            if (!checkifinarray(visited, newloc)) {
                                q.add(new Triplet<Integer, String, coordinate>(curr.a + 1, curr.b + Character.toString(direction), newloc));
                            }
                            break;
                        case ('a'):
                            newloc = new coordinate(curr.c.x(), curr.c.y() - 1);
                            if (!checkifinarray(visited, newloc)) {
                                q.add(new Triplet<Integer, String, coordinate>(curr.a + 1, curr.b + Character.toString(direction), newloc));
                            }
                            break;
                        case ('s'):
                            newloc = new coordinate(curr.c.x() + 1, curr.c.y());
                            if (!checkifinarray(visited, newloc)) {
                                q.add(new Triplet<Integer, String, coordinate>(curr.a + 1, curr.b + Character.toString(direction), newloc));
                            }
                            break;
                        case ('d'):
                            newloc = new coordinate(curr.c.x(), curr.c.y() + 1);
                            if (!checkifinarray(visited, newloc)) {
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
                return !map.checkCollisionNPCFindPath(this, currx - 1, curry);
            case ('a'):
                if (curry == 0) {
                    return false;
                }
                return !map.checkCollisionNPCFindPath(this, currx, curry - 1);
            case ('s'):
                if (currx == 24) {
                    return false;
                }
                return !map.checkCollisionNPCFindPath(this, currx + 1, curry);

            case ('d'):
                if (curry == 23) {
                    return false;
                }
                return !map.checkCollisionNPCFindPath(this, currx, curry + 1);      
        }
        return false;
    }
    
    @Override
    public NPC clone() {
        //System.out.println("Cloning NPC with hand " + this.hand);
        NPC n = new NPC(this.name, map, absoluteIndex);
        for (String h : this.hand) {
            n.hand.add(h);
        }
        for (String g : guesses) {
            n.guesses.add(g);
        }
        n.card = new Scorecard(n);
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