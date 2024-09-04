import java.util.Arrays;
import java.util.HashMap;

public class MapTester
{
    public static void main(String[] args)
    {
        HashMap<int[], Room> doors = new HashMap<int[], Room>(); // doors linked to rooms, the reverse will be annoying unless we store player position inside door....
        int[][] doorCoors = {{3,6},{4,9},{5,17},{6,10},{6,11},{8,6},{9,17},{10,3},{12,1},{15,6},{17,10},{17,15},{18,19},{19,8},{19,15}};
        String[] roomNames = {"study", "hall", "lounge", "hall", "hall", "library", "dining room", "library", "billard room", "billard room",
                        "ball room", "ball room", "kitchen", "ball room", "ball room"};
        for (int i = 0; i < roomNames.length; i++)
        {
            Room curr = new Room(roomNames[i]);
            int[] coor = doorCoors[i];
            doors.put(coor, curr);
        }
        System.out.print(doors);
    }
    
    
        // HashMap<Integer, String> letters = new HashMap<Integer, String>();
        // letters.put(0,"A");
        // letters.put(1,"B");
        // System.out.print(letters);
   
   
                        
    
                        
    // //need to make room obejcts so they can be added to the hashmap
    // for (int i = 0; i < roomsNames.length; i++)
    // {
    //     Room curr = new Room(roomNames[i]);
    //     int[] coor = doorCoors[i];
    //     doors.put(coor, curr);
    // }

    // System.out.println(doors);
    // int[] example = {2,2};
    // Room r = new Room("ROOM");
    // doors.put(example, r);
    // System.out.print(doors);
}