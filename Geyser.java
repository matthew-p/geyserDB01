
/**
 * @author Matthew Pische 
 */
public class Geyser
{
    private String name;
    private int eruptions;
    
    public Geyser(String name) {
        this.name = name;
        eruptions = 0;
    }
    public void increment() {
        eruptions++;
    }
    public String getName() {
        return name;
    }
    public int getNumEruptions() {
        return eruptions;
    }
    public static void main(String[] args) {
        try {
            Geyser g = new Geyser("My Geyser");
            if (g.getNumEruptions() != 0 || !g.getName().equals("My Geyser"))
                System.out.println("Error in instantiation");
            g.increment();
            if (g.getNumEruptions() != 1)
                System.out.println("Error in increment eruptions");
            System.out.println("Test complete");
        } catch (Exception e) {
            System.out.println("Error in Geyser class");
        }
    }
}
