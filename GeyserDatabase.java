
/**
 * @author Matthew Pische 
 */
import java.util.*;
import java.io.*;

public class GeyserDatabase
{
    private ArrayList<Eruption> eruptions;
    private ArrayList<Geyser> geysers;
    
    public GeyserDatabase() {
        eruptions = new ArrayList<Eruption>();
        geysers = new ArrayList<Geyser>();
    }
    public void readGeyserData(String filename) {
        //project spec called for a new ArrayList here, but unclear why 
        try {
            if (eruptions.size() != 0) {
                eruptions = new ArrayList<Eruption>();
                geysers = new ArrayList<Geyser>();
            }
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            String text; 
            while (sc.hasNext()) {
                text = sc.nextLine();
                Eruption e = new Eruption(text);
                addEruption(e);
            }
            sc.close();
            // 
            createGeyserList();
        } catch (IOException e) {
            System.out.println("Failed to read datafile: " + filename);
        }
    }
    public void addEruption (Eruption e) {
        eruptions.add(e);
        boolean inList = false;
        for (Geyser g : geysers) {
            if (g.getName().equals(e.geyser())){
                g.increment();
                inList = true;
            }
        }
        if (!inList){
            Geyser g = new Geyser(e.geyser());
            g.increment();
            geysers.add(g);
        }
    }
    public ArrayList<Eruption> getEruptionList() {
        return eruptions;
    }
    public ArrayList<Geyser> getGeyserList() {
        return geysers;
    }
    public int getNumEruptions() {
        return eruptions.size();
    }
    public int getNumEruptions(int m, int d, int y) {
        int total = 0;
        for (Eruption e : eruptions) {
            if (e.yr() == y && e.mon() == m && e.day() == d)
                total++;
        }
        return total;
    }
    public Eruption getLateNightEruption() {
        /* NOTE: 
         * the project spec is ambiguous in that the method signature it gives does not take a year, 
         * however the description of it's function could arguably be read to imply it is only checking for the 
         * latest eruption during the most recent (?) year, or that this was meaningless, because the target data set only 
         * contains one year's data. I chose to instead look for the first
         * instance of the lastest eruption out of the entire set, inclusive of all years here
         */
        Eruption lastE = eruptions.get(0);
        Iterator<Eruption> iter = eruptions.iterator();
        while(iter.hasNext() && lastE.hr() * 60 + lastE.min() < 1439) {
            Eruption e = iter.next();
            if (lastE.hr() * 60 + lastE.min() < e.hr() * 60 + e.min())
                lastE = e;
        }
        /* alternate implimentation 
        for (Eruption e : eruptions) {
            if (lastE.hr() * 60 + lastE.min() < e.hr() * 60 + e.min())
                lastE = e;
        }
        */
        return lastE;
    }

    public ArrayList<Eruption> getEruptions(String geyser) {
        if (geyser.equals(""))
            return null;
        ArrayList<Eruption> output = new ArrayList<Eruption>();
        geyser = geyser.toLowerCase();
        for (Eruption e : eruptions) {
            // NOTE, this will pull in all geysers beginning with the entered string
            if (e.geyser().toLowerCase().startsWith(geyser)) 
                output.add(e);
        }
        return output;
    }
    public String findDayWithMostEruptions(int y) {
        /* NOTE:
         * the project spec references the method "countEruptions" but I think this was an error, 
         * as it is never mentioned elsehwere in the document, and use of getNumEruptions() was actually intended, 
         * which is what I have done here. 
         */
        int max = 0;
        int month = 0;
        int day = 0;
        String output = "";
        for (int m = 1; m <= 12; m++) {
            for (int d = 1; d <= 31; d++) {
                int dayCount = getNumEruptions(m, d, y);
                if (max < dayCount) {
                    month = m;
                    day = d;
                    max = dayCount;
                }
            }
        }
        if (max > 0)
            output = String.format("%1$d eruptions on %2$d/%3$d/%4$d", max, month, day, y);
        else 
            output = String.format("No eruptions in %1$d", y);
        return output;
    }
    private void createGeyserList() {
        ArrayList<String> nameList = new ArrayList<String>();
        // temp list of geyser names
        for (Eruption e : eruptions) {
            if (!nameList.contains(e.geyser())) 
                nameList.add(e.geyser());
        }
        geysers = new ArrayList<Geyser>();
        for (String s : nameList) {
            Geyser g = new Geyser(s);
            for (Eruption e : eruptions) {
                if (e.geyser().equals(g.getName()))
                    g.increment();
            }
            geysers.add(g);
        }
    }

    public int getNumGeysers() {
        return geysers.size();
    }
    public ArrayList<Geyser> findMostActiveGeyser() {
        Geyser maxG = geysers.get(0);
        ArrayList<Geyser> maxGeysers = new ArrayList<Geyser>();
        for (Geyser g : geysers) {
            if (g.getNumEruptions() > maxG.getNumEruptions()) {
                maxG = g;
                maxGeysers.clear();
                maxGeysers.add(maxG);
            } else if (g.getNumEruptions() == maxG.getNumEruptions()) {
                maxGeysers.add(g);
            }
        }
        return maxGeysers;
    }
    public ArrayList<Geyser> findLeastActiveGeyser() {
        Geyser minG = geysers.get(0);
        ArrayList<Geyser> minGeysers = new ArrayList<Geyser>();
        for (Geyser g : geysers) {
            if (g.getNumEruptions() < minG.getNumEruptions()) {
                minG = g;
                minGeysers.clear();
                minGeysers.add(minG);
            } else if (g.getNumEruptions() == minG.getNumEruptions()) {
                minGeysers.add(g);
            }
        }
        return minGeysers;
    }
}
