import java.util.*;

public class Clue {
    public ArrayList<String> answer;


    public static void main(String[] args) {
        map gamemap = new map(0);
        while (gamemap.gameover == false) {
            for (int i = 0; i < gamemap.players.size(); i ++) {
                int roll = 1 + (int) (Math.random() * 6);
                int roll2 = 1 + (int) (Math.random() * 6);
                int roll3 = roll + roll2;
                //System.out.println("Player " + i + "is currently in space " + gamemap.players.get(i).xPos + " , " + gamemap.players.get(i).yPos);
                gamemap.moveplayer(i, roll3);
            }
        }
    }
}