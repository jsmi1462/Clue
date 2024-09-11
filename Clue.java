import java.util.*;

public class Clue {
    public ArrayList<String> answer;


    public static void main(String[] args) {
        map gamemap = new map(2);
        while (gamemap.gameover == false) {
            for (int i = 0; i < gamemap.players.size(); i ++) {
                int roll = 1 + (int) (Math.random() * 6);
                int roll2 = 1 + (int) (Math.random() * 6);
                int roll3 = roll + roll2;
                    for (int j = roll3; j > 0 ; j--) {
                        System.out.println("Player " + gamemap.players.get(i) + " (" + i + ") is playing with " + j + " moves remaining this turn!");
                        System.out.println(gamemap);
                        //System.out.println("Player " + i + "is currently in space " + gamemap.players.get(i).xPos + " , " + gamemap.players.get(i).yPos);
                        if (!gamemap.moveplayer(i)) break;
                }
            }
        }
    }
}