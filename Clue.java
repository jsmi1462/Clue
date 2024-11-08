import java.util.*;

public class Clue {
    public ArrayList<String> answer;
    public static void main(String[] args) {
        map gameMap = new map(0);
        int winner = 0;
        System.out.println()
        while (gameMap.gameOver == false) {
            int limit = gameMap.playersingame.size();
            for (int i = 0; i < limit; i ++) {
                int roll = 1 + (int) (Math.random() * 6);
                int roll2 = 1 + (int) (Math.random() * 6);
                int roll3 = roll + roll2;
                //System.out.println("Player " + i + "is currently in space " + gamemap.players.get(i).xPos + " , " + gamemap.players.get(i).yPos);
                if (gameMap.moveplayer(i, roll3) == false) {
                    System.out.println("Oh no! Player " + gameMap.playersingame.get(i).toString() + " lost the game!");
                    i--;
                    limit--;
                }
                if (gameMap.gameOver == true) {
                    winner = i;
                    break;
                }
            }
        }
        gameMap.win(gameMap.players.get(winner));
    }

}
