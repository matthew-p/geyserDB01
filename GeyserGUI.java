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
    
    private JTextField month;

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
        db.readGeyserData("GeyserData.txt");

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
        position.insets =  new Insets(20,20,0,0);       
        add(scrollPane, position);  

        // Create label and textfield for Month
        position = new GridBagConstraints();
        position.insets =  new Insets(0,20,0,0);   
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
        position.insets =  new Insets(0,20,0,0);  
        month = new JTextField(2);  
        add(month, position);
        
        // FIX ME: add labels and text fields at bottom
   

        // FIX ME: Add buttons and labels on right side
       

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

        //FIX ME: complete this method as specified
    }

    /*********************************************************************
    Respond to menu selections and button clicks

    @param e the button or menu item that was selected
     *********************************************************************/
    public void actionPerformed(ActionEvent e){

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
        
        // FIX ME: Count menu item - display number of eruptions and geysers   
        if (e.getSource() == countItem){

        }
        
        // FIX ME: display late night eruption    

        // FIX ME: display all geyser names  


        // FIX ME: max eruptions day in a year (check for year) 


        // FIX ME: list all eruptions for a geyser (check  for name)   




        // FIX ME: display eruptions for a particular date
        // check for month, day and year


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
            // use the name of your lottery ticket variable 
            // db.readGeyserData(filename);          
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