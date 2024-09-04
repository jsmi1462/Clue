import java.util.*;

public class Clue {
    public static void main(String[] args) {
        map gamemap = new map(2);
        while (gamemap.gameover == false) {
            for (int i = 0; i < gamemap.players.size(); i ++) {
                int roll = 1 + (int) (Math.random() * 6);
                for (int j = 0; j < roll; j++) {
                    if (!gamemap.moveplayer(i)) break;
                }
            }

        }
    }
}