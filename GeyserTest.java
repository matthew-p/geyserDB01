
/**
 * @author (your name) 
 */
public class GeyserTest
{
    public static void main(String[] args) {
        GeyserDatabase db = new GeyserDatabase();
        db.readGeyserData("testFile.txt");
        if (db.getNumEruptions() != 11) 
            System.out.println("Error: wrong # eruptions");
        if (db.getNumEruptions(12, 31, 2010) != 3)
            System.out.println("Error: wrong # eruptions on Dec. 31, 2010, should be 3, was: " + db.getNumEruptions(12,31,2010));
        if (db.getNumGeysers() != 8) 
            System.out.println("Error: wrong # geysers, was: " + db.getNumGeysers() + " should be 8");
        
        Eruption e = db.getLateNightEruption();
        if (e.hr() != 20 && e.min() != 35)
            System.out.println("Error: latest eruption wrong, should be 20:35");
        if (!e.toString().equals("Scott on 4/15/2010 at 20:35"))
            System.out.println("Error: last eruption toString should be: Scott on 4/15/2010 at 20:35\nwas: " + e.toString());
        e = new Eruption("7/18/2010,Pink Cone,21:7");
        db.addEruption(e);
        if (db.getNumEruptions() != 12)
            System.out.println("Error: after insert eruptions should be 12, but are: " + db.getNumEruptions());
        if (db.getNumGeysers() != 9)
            System.out.println("Error: after insert geysers should be 9, but are: " + db.getNumGeysers());
        e = db.getLateNightEruption();
        if (!e.toString().equals("Pink Cone on 7/18/2010 at 21:07"))
            System.out.println("Error: insert did not update latest eruption for 2010, should be Pink Cone, was: " + e.toString());
        if (!db.findDayWithMostEruptions(2010).equals("3 eruptions on 1/1/2010"))
            System.out.println("Error: day w/most eruptions should be 1/1/2010, was: " + db.findDayWithMostEruptions(2010));
        if (!db.findDayWithMostEruptions(2011).equals("No eruptions in 2011"))
            System.out.println("Error: does not handle empty year properly");
        if (!db.findMostActiveGeyser().getName().equals("Scott"))
            System.out.println("Error: most active geyser incorrect, should be Scott, was: " + db.findMostActiveGeyser().getName());
        if (db.findLeastActiveGeyser().getNumEruptions() != 1)
            System.out.println("Error: least active geyser count incorrect, should be 1, was: " + db.findLeastActiveGeyser().getNumEruptions());
            
        System.out.println("Tests complete");
    }
}
