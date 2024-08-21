public class map {
    public char[20][20] map;


    public map {
       addwall(9, 0, 0);
       addwall(4, 0, 10);
       addwall(9, 0, 15);
      
       
       
    }
    public static void addwall(int n, int row, int column, bool horizontal) {
        if (horizontal == False)
        for (int i = 0; i < n; i ++) {
            map[row][column] = "x";
            if (column < 19) {
               column += 1;
            }
            else {
               row += 1;
               column = 0;
            }
        }
      return;
    }
    public String toString() {
    }

}
