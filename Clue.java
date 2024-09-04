import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Clue {
    public static void main(String[] args) {
        map gamemap = new map(6);
        while (gamemap.gameover == false) {
            for (int i = 0; i < gamemap.players.size(); i ++) {
                int roll = 1 + (int) (Math.random() * 6);
                    for (int j = roll; j > 0 ; j--) {
                        System.out.println("Player " + i + " is playing with " + j + " moves remaining this turn!");
                        System.out.println(gamemap);
                        if (!gamemap.moveplayer(i)) break;
                }
            }

        }
    }
}