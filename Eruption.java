
/**
 * @author Matthew Pische 
 */

import java.util.Scanner;

public class Eruption
{
    // instance variables
    private int minute;
    private int hour;
    private int month;
    private int date;
    private int year;
    private String name;
    
    public Eruption(String info) {
        Scanner scnr = new Scanner(info);
        scnr.useDelimiter("/|,|:");
        month = scnr.nextInt();
        date = scnr.nextInt();
        year = scnr.nextInt();
        name = scnr.next();
        hour = scnr.nextInt();
        minute = scnr.nextInt();
    }
    public int min() {
        return minute;
    }
    public int hr() {
        return hour;
    }
    public int mon() {
        return month;
    }
    public int day() {
        return date;
    }
    public int yr() {
        return year;
    }
    public String geyser() {
        return name;
    }
    public String toString() {
        return String.format("%1$s on %2$d/%3$d/%4$d at %5$d:%6$02d",
                                geyser(), mon(), day(), yr(), hr(), min()
                                );
    }
    public static void main(String[] args) {
        try {
            Eruption testErupt = new Eruption("7/18/2010,Pink Cone,10:7");
            if (testErupt.toString().equals("Pink Cone on 7/18/2010 at 10:07"))
                System.out.println("Eruption string correct");
            else
                System.out.println("Eruption string wrong");
        } catch (Exception e) {
            System.out.println("Eruption class failed");
        }
    }
    
}
