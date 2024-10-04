import java.util.*;

public class Clue {
    public ArrayList<String> answer;


    public static void main(String[] args) {
        map gameMap = new map(6);
        int winner = 0;
        while (gameMap.gameOver == false) {
            for (int i = 0; i < gameMap.players.size(); i ++) {
                int roll = 1 + (int) (Math.random() * 6);
                int roll2 = 1 + (int) (Math.random() * 6);
                int roll3 = roll + roll2;
                //System.out.println("Player " + i + "is currently in space " + gamemap.players.get(i).xPos + " , " + gamemap.players.get(i).yPos);
                gameMap.moveplayer(i, roll3);
                if (gameMap.gameOver == true) {
                    winner = i;
                    break;
                }
            }
        }
        gameMap.win(gameMap.players.get(winner));
    }
}
