public class map {
    public char[25][23] map;


    public map {
        this.addrow("xxxxxxx xxxxxxxx x");
        this.addrow("");
       
    }
    public void addrow(String row, int rowIndex) {
        int counter = 0;
        for (char c: row.toCharArray()) {
            map[rowIndex][counter] = c;
            counter++;
        }
        if (counter < 22) {
            while (counter < 22) {
                counter ++;
                map[rowIndex][counter] = 'x';
            }
        }
        return;
    }
    public String toString() {
    }

}
