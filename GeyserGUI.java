import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.text.*;

/***********************************************************************
 * GUI front end for a Yellowstone Geyser database 
 * 
 * @author Scott Grissom
 * @version August 1, 2016
 **********************************************************************/
public class GeyserGUI extends JFrame implements ActionListener{

    /** results box */
    private JTextArea resultsArea;

    private GeyserDatabase db;

    // FIX ME: define labels, text fields and button
    private JTextField dayField;
    private JTextField monthField;
    private JTextField yearField;
    private JTextField geyserField;
    

    private JButton lateButton;
    private JButton onDateButton;
    private JButton maxButton;
    private JButton byNameButton;

    private JButton mostActiveButton;
    private JButton leastActiveButton;
    private JButton geyserListButton;

    /** menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenu reportsMenu;
    private JMenuItem quitItem;
    private JMenuItem openItem; 
    private JMenuItem countItem;
    private JMenuItem geyserItem;
    
    /*********************************************************************
    Main Method
     *********************************************************************/
    public static void main(String arg[]){
       GeyserGUI gui = new GeyserGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Yellowstone Geysers");
        gui.pack();
        gui.setVisible(true);

    }

    /*********************************************************************
    Constructor - instantiates and displays all of the GUI commponents
     *********************************************************************/
    public GeyserGUI(){
        db = new GeyserDatabase();
        
        // FIX ME: the following line should be removed
        //db.readGeyserData("GeyserData.txt");

        // create the Gridbag layout
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        // create the Results Text Area (5 x 10 cells)
        resultsArea = new JTextArea(20,40);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        position.gridx = 0;
        position.gridy = 0;
        position.gridheight = 10;
        position.gridwidth = 5;   
        position.insets =  new Insets(20,20,10,0);       
        add(scrollPane, position);  

        // Create label and textfield for Month
        position = new GridBagConstraints();
        position.insets =  new Insets(0,20,5,0);   
        position.gridx = 0;
        position.gridy = 10;  
        position.gridwidth = 1;
        position.gridheight = 1;  
        position.anchor = GridBagConstraints.LINE_START;
        add(new JLabel("Month"), position);

        position = new GridBagConstraints();
        position.gridx = 0;
        position.gridy = 11; 
        position.gridwidth = 1;
        position.gridheight = 1;
        position.anchor = GridBagConstraints.LINE_START;
        position.insets =  new Insets(0,20,20,0);  
        monthField = new JTextField(2);  
        add(monthField, position);
        
        position = makeConstraints(1,10,1,1,GridBagConstraints.LINE_START);
        position.insets = new Insets(0,20,5,0);
        add(new JLabel("Day"), position);
        
        position = makeConstraints(1,11,1,1,GridBagConstraints.LINE_START);
        position.insets = new Insets(0,20,20,0);
        dayField = new JTextField(2);
        add(dayField, position);
        
        position = makeConstraints(2,10,1,1,GridBagConstraints.LINE_START);
        position.insets = new Insets(0,20,5,0);
        add(new JLabel("Year"), position);
        
        position = makeConstraints(2,11,1,1,GridBagConstraints.LINE_START);
        position.insets = new Insets(0,20,20,0);
        yearField = new JTextField(4);
        add(yearField, position);
        
        position = makeConstraints(3,10,1,1,GridBagConstraints.LINE_START);
        position.insets = new Insets(0,20,5,0);
        add(new JLabel("Geyser"), position);
        
        position = makeConstraints(3,11,1,2,GridBagConstraints.LINE_START);
        position.insets = new Insets(0,20,20,0);
        geyserField = new JTextField(24);
        add(geyserField, position);
        
        position = makeConstraints(5,0,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(20,20,5,20);
        add(new JLabel("Eruptions"), position);
        
        position = makeConstraints(5,1,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(0,20,5,20);
        lateButton = new JButton("Late Night Eruption");
        lateButton.addActionListener(this);
        add(lateButton, position);
        
        position = makeConstraints(5,2,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(0,20,5,20);
        onDateButton = new JButton("# on Date");
        onDateButton.addActionListener(this);
        add(onDateButton, position);
        
        position = makeConstraints(5,3,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(0,20,5,20);
        maxButton = new JButton("Max Eruptions in Year");
        maxButton.addActionListener(this);
        add(maxButton, position);
        
        position = makeConstraints(5,4,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(0,20,20,20);
        byNameButton = new JButton("By Name");
        byNameButton.addActionListener(this);
        add(byNameButton, position);
        
        position = makeConstraints(5,6,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(0,20,5,20);
        add(new JLabel("Geysers"), position);
        
        position = makeConstraints(5,7,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(0,20,5,20);
        mostActiveButton = new JButton("Most Active");
        mostActiveButton.addActionListener(this);
        add(mostActiveButton, position);
        
        position = makeConstraints(5,8,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(0,20,5,20);
        leastActiveButton = new JButton("Least Active");
        leastActiveButton.addActionListener(this);
        add(leastActiveButton, position);
        
        position = makeConstraints(5,9,1,1,GridBagConstraints.CENTER);
        position.insets = new Insets(0,20,5,20);
        geyserListButton = new JButton("Geyser List");
        geyserListButton.addActionListener(this);
        add(geyserListButton, position);
          

        // set up File menus
        setupMenus();
        pack();

    }

    /*********************************************************************
    List all entries given an ArrayList of eruptions.  Include a final
    line with the number of eruptions listed

    @param m list of eruptions
     *********************************************************************/
    private void displayEruptions(ArrayList <Eruption> m){
        resultsArea.setText("");
        for (Eruption e : m) {
            resultsArea.append(e.toString() + "\n");
        }
        resultsArea.append("Total Eruptions: " + m.size());
    }
        private void displayEruptions(String startMessage, ArrayList <Eruption> m){
        resultsArea.setText(startMessage + "\n");
        for (Eruption e : m) {
            resultsArea.append(e.toString() + "\n");
        }
        resultsArea.append("Total Eruptions: " + m.size());
    }

    /*********************************************************************
    Respond to menu selections and button clicks

    @param e the button or menu item that was selected
     *********************************************************************/
    public void actionPerformed(ActionEvent e){
        /*
         *      
            private JButton mostActiveButton;
            private JButton leastActiveButton;
            private JTextField dayField;
            private JTextField monthField;
            private JTextField yearField;
            private JTextField geyserField;
         */
        Eruption item = null;
        
        // either open a file or warn the user
        if (e.getSource() == openItem){
            openFile();     
        }else if(db.getNumEruptions() == 0){
            String errorMessage = "Did you forget to open a file?";
            resultsArea.setText(errorMessage);
            return;    
        } 
        // menu item - quit
        if (e.getSource() == quitItem){
            System.exit(1);
        }    
        // Count menu item - display number of eruptions and geysers   
        if (e.getSource() == countItem){
            resultsArea.setText("Number of all Eruptions:\n  " + db.getNumEruptions());
            resultsArea.append("\nNumber of all Geysers:\n  " + db.getNumGeysers());
        }
        
        // display late night eruption    
        if (e.getSource() == lateButton) {
            item = db.getLateNightEruption();
            resultsArea.setText("Latest Eruption:\n" + item.toString());
        }
        // display all geyser names  
        if (e.getSource() == geyserListButton) {
            resultsArea.setText("Geysers: \n");
            for (Geyser g : db.getGeyserList()) {
                resultsArea.append(g.getName() + "\n");
            }
        }
        // max eruptions day in a year (check for year) 
        if (e.getSource() == maxButton) {
            if (yearField.getText().length() == 0) 
                JOptionPane.showMessageDialog(this, "Provide a year");
            else {
                int year = Integer.parseInt(yearField.getText());
                String max = db.findDayWithMostEruptions(year);
                resultsArea.setText("Day with the most eruptions in " + year + ":\n" + max);
            }
        }
        // list all eruptions for a geyser (check  for name)   
        if (e.getSource() == byNameButton) {
            String name = geyserField.getText();
            if (name.length() == 0)
                JOptionPane.showMessageDialog(this, "Provide a geyser name");
            else
                displayEruptions("All Eruptions for: " + name, db.getEruptions(name));
        }
        // display eruptions for a particular date
        // check for month, day and year
        if (e.getSource() == onDateButton) {
            if (monthField.getText().length() != 0 && 
                dayField.getText().length() != 0 &&
                yearField.getText().length() != 0) {
                int m = Integer.parseInt(monthField.getText());
                int d = Integer.parseInt(dayField.getText());
                int y = Integer.parseInt(yearField.getText());
                int total = db.getNumEruptions(m, d, y);
                resultsArea.setText(String.format("Total Eruptions on %1$d/%2$d/%3$d: %4$d", m, d, y, total));
            } else {
                JOptionPane.showMessageDialog(this, "Provide Month, Date, and Year");
            }
        }
        if (e.getSource() == mostActiveButton) {
            resultsArea.setText("Most Active Geyser: \n");
            for (Geyser g : db.findMostActiveGeyser()) {
                resultsArea.append(g.getName() + ", " + g.getNumEruptions() + " Eruptions \n");
            }
        }
        if (e.getSource() == leastActiveButton) {
            resultsArea.setText("Least Active Geyser: \n");
            for (Geyser g : db.findLeastActiveGeyser()) {
                resultsArea.append(g.getName() + ", " + g.getNumEruptions() + " Eruption \n");
            }
        }
    }

    /*********************************************************************
    In response to the menu selection - open a data file
     *********************************************************************/
    private void openFile(){
        JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
        int returnVal = fc.showOpenDialog(this);

        // did the user select a file?
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = fc.getSelectedFile().getName();
            
             db.readGeyserData(filename);          
        }
    }

    /*********************************************************************
    Create a custom gridbag constraint
     *********************************************************************/    
       private GridBagConstraints makeConstraints(int x, int y, int h, int w, int align){ 
        GridBagConstraints rtn = new GridBagConstraints(); 
        rtn.gridx = x; 
        rtn.gridy = y; 
        rtn.gridheight = h; 
        rtn.gridwidth = w; 
        
        // set alignment: LINE_START, CENTER, LINE_END
        rtn.anchor = align; 
        return rtn; 
    }   
    
    /*********************************************************************
    Set up the menu items
     *********************************************************************/
    private void setupMenus(){

        // create menu components
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        openItem = new JMenuItem("Open...");
        reportsMenu = new JMenu("Reports");
        countItem = new JMenuItem("Counts");

        // assign action listeners
        quitItem.addActionListener(this);
        openItem.addActionListener(this);
        countItem.addActionListener(this);

        // display menu components
        fileMenu.add(openItem);
        fileMenu.add(quitItem);
        reportsMenu.add(countItem);    
        menus = new JMenuBar();

        menus.add(fileMenu);
        menus.add(reportsMenu);
        setJMenuBar(menus);
    }         
}