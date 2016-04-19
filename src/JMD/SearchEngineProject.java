package JMD;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/*
 * Search engine project
 *
 * James Helvenston, Mark Helvenston, Daniel Matheny
 */
public class SearchEngineProject
{
    private static final int ASIZE = 100;
    
    //Boolean used to track if maintenance window is open or closed
    private static boolean windowOpen;
    private static int indexNum = 0;
    
    //These arrays should be converted to lists with an unfixed size
    private static String[] indexData = new String[ASIZE];
    private static long[] modData = new long[ASIZE];
    private static String[] fileContent = new String[ASIZE];
    
    //Map used to contain the search string the user inputs
    private static Map<String, ArrayList> invertIndex = new HashMap<>();
    private static ArrayList<Integer> mapInts = new ArrayList<>();
    
    //private static ArrayList<String> indexData = new ArrayList<String>();
    //private static ArrayList<Long> modData = new ArrayList<Long>();
    
    private static JFrame searchFrame = new JFrame( "Search engine" );
    private static JFrame maintenanceFrame = new JFrame( "Maintenance" );
    
    public static void searchFrame()
    {
        //Create the frame
        searchFrame.setSize( 800, 500 );
        searchFrame.setLocation( 50, 250 );
        searchFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        //GUI components instantiated and initialized here
        JLabel titleLabel, termsLabel, indexedLabel;
        JRadioButton allRadio, anyRadio, exactRadio;
        JTextArea resultsArea;
        JTextField searchField;
        titleLabel = new JLabel( "Search Engine" );
        termsLabel = new JLabel( "File Terms" );
        indexedLabel = new JLabel();
        searchField = new JTextField();
        searchField.setPreferredSize( new Dimension( 350, 24 ) );
        allRadio = new JRadioButton( "Search All File Terms" );
        anyRadio = new JRadioButton( "Search Any File Terms" );
        exactRadio = new JRadioButton( "Search Exact Phrase" );
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JButton searchButton = new JButton( "Search" );
        JButton maintenanceButton = new JButton( "Maintenance" );
        JButton aboutButton = new JButton( "About" );
        
        titleLabel.setFont(new Font("Tahoma", 0, 36));
        resultsArea.setColumns(20);
        resultsArea.setRows(5);
        
        //Check to see if window is closed before opening a new one
        maintenanceButton.addActionListener((ActionEvent ae) -> 
        {
            if ( windowOpen == false )
                maintenanceFrame();
        } );
        
        //Open a dialog box explaining the program
        aboutButton.addActionListener((ActionEvent ae) -> 
        {
            JOptionPane.showMessageDialog( searchFrame , "Search Engine Project for Java II" );
        } );
        
        searchButton.addActionListener((ActionEvent ae) -> 
        {
            String searchResults = search( searchField.getText() );
            resultsArea.setText( searchResults );
        } );
        
        //Top level boxlayout panel is added to stack all other panels
        JPanel contentPane = new JPanel();
        contentPane.setLayout( new BoxLayout( contentPane, BoxLayout.Y_AXIS ) );
        searchFrame.add( contentPane );
        
        //five panels are made to store the components
        JPanel panel1 = new JPanel();
        panel1.add( titleLabel );
        contentPane.add(panel1 );
        
        JPanel panel2 = new JPanel();
        panel2.add( termsLabel );
        panel2.add( searchField );
        panel2.add( searchButton );
        contentPane.add(panel2 );
        
        JPanel panel3 = new JPanel();
        panel3.add( allRadio );
        panel3.add( anyRadio );
        panel3.add( exactRadio );
        ButtonGroup radios = new ButtonGroup(); //buttongroup is needed to keep only one radio pushed at a time
        radios.add( allRadio );
        radios.add( anyRadio );
        radios.add( exactRadio );
        contentPane.add( panel3 );
        
        JScrollPane scrollPane = new JScrollPane( resultsArea );
        contentPane.add( scrollPane );
        
        JPanel panel4 = new JPanel();
        panel4.add( maintenanceButton );
        panel4.add( aboutButton );
        contentPane.add(panel4 );

        searchFrame.setVisible( true );
    }
    
    public static void maintenanceFrame()
    {
        //Creating the frame
        
        maintenanceFrame.setSize( 800,500 );
        maintenanceFrame.setLocation( 1040, 250 );
        windowOpen = true;
        
        //Set boolean to false when window closes
        maintenanceFrame.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing( WindowEvent we )
            {
                windowOpen = false;
            }
        } );
        
        //GUI components instantiated and initialized here
        JLabel titleLabel, indexedLabel, versionLabel;
        titleLabel = new JLabel("Index Maintenance");
        indexedLabel = new JLabel();
        versionLabel = new JLabel();
        JButton addButton = new JButton("Add File");
        JButton removeButton = new JButton("Remove File");
        JButton rebuildButton = new JButton("Rebuild Out-of-date");
        JButton repositionButton = new JButton("Reset Window Position");
        JPanel contentPane = new JPanel();
        contentPane.setLayout( new BoxLayout( contentPane, BoxLayout.Y_AXIS ) );
        
        titleLabel.setFont(new Font("Tahoma", 0, 36));
        
        //Reset window positions when repositionButton is pressed
        repositionButton.addActionListener((ActionEvent ae) -> 
        {
                resetWindows();
        } );
        
        JPanel panel1 = new JPanel();
        panel1.add( titleLabel );
        contentPane.add( panel1 );
        
        DefaultTableModel tableMod = new DefaultTableModel();
        JTable indexTable = new JTable( tableMod )
        {
            @Override
            public boolean isCellEditable( int row, int column )
            {
                return false;
            }
        };
        
        tableMod.addColumn( "File Name" );
        tableMod.addColumn( "Status" );
        
        
        JScrollPane scrollPane = new JScrollPane( indexTable );
        indexTable.setFillsViewportHeight( true );
        contentPane.add( scrollPane );
        
        addButton.addActionListener((ActionEvent ae) ->
        {
            //call method to get file name and add it to table
            String newItem = addToIndex( maintenanceFrame );
            if ( !newItem.isEmpty() )
            {
                //Program should check for status updates later
                tableMod.addRow( new Object[] { newItem, "Dummy data status" } );
                writeFile();
                indexNum++;
                updateLabel( indexedLabel );
            }
        });
        
        removeButton.addActionListener((ActionEvent ae) ->
        {
            //Remove a row from the index
            if ( indexNum > 0 )
            {
                removeFromIndex( tableMod );
                updateLabel( indexedLabel );
                writeFile();
            }
        });
        
        rebuildButton.addActionListener((ActionEvent ae) ->
        {
            rebuild();
        });
        
        JPanel panel2 = new JPanel();
        panel2.add( addButton );
        panel2.add( removeButton );
        panel2.add( rebuildButton );
        contentPane.add( panel2 );
        
        JPanel panel3 = new JPanel();
        panel3.add( repositionButton );
        panel3.add( indexedLabel );
        panel3.add( versionLabel );
        contentPane.add( panel3 );
        
        maintenanceFrame.add( contentPane );
        maintenanceFrame.setVisible( true );
        
        readFile( tableMod );
        
        updateLabel( indexedLabel );
    }
    
    public static void resetWindows()
    {
        //Set windows back to where they originally spawned
        searchFrame.setLocation( 50, 250 );
        maintenanceFrame.setLocation( 1040, 250 );
    }
    
    public static String search( String searchText )
    {
        //Returns dummy data, searching will replace this later
        //String results = "Dummy search results";
        StringBuilder sb = new StringBuilder();
        
        //strip out all non-alphanumeric characters from the text entered and set it to lowercase
        searchText = searchText.replaceAll("[^a-zA-Z0-9 ]","");
        searchText = searchText.toLowerCase();
        //split it individual words, separated by spaces, and put each word into an array
        String[] keys = searchText.split( " " );
        
        //Selection statement used to determine which radio was pushed
        
        //Once the radio button is determined, one of the three search types
        //is performed
        
        //Some kind of loop needs to be used to cycle through the array
        //containing each file pulled from the index, and if found, shown
        //in the text area on the seach frame
        for( int i = 0; i < keys.length; ++i )
        {
            /*            String[] test = fileContent[i].split( " " );
            for(int j = 0; j < test.length; ++j)
            {
            if ( keys[i].equals(test[j]) )
            {
            //searchTerms.put( keys[i], test[j] );
            sb.append( indexData[i] + " " + test[j] );
            //sb.append( System.getProperty( "line.separator" ) );
            }
            }*/
            
            if ( invertIndex.containsKey( keys[i] ) )
            {
                for( int j = 0; j < mapInts.size(); ++j )
                {
                    //this should pull the appropriate index number pointing to a certain filename
                    //but it is currently broken
                    ArrayList<Integer> intList = invertIndex.get( keys );
                    int find = intList.get( j );
                    sb.append( indexData[ find ] );
                }
            }
        }
        
        return sb.toString();
    }
    
    public static String addToIndex( JFrame parent )
    {
        //Open a file chooser to return a file's path name
        JFileChooser indexChooser = new JFileChooser();
        int choice = indexChooser.showOpenDialog( parent );
        String filename;
        long lastMod;

        if ( choice == JFileChooser.APPROVE_OPTION )
        {
            File file = indexChooser.getSelectedFile();
            filename = file.getAbsolutePath();
            lastMod = file.lastModified();
            String indexItem = filename;
            
            //Only return filename if a file is selected and valid
            if ( file.isFile() )
            {
                //Changed to adjust for arraylist
                indexData[indexNum] = filename + " ; " + lastMod;
                return indexItem;
                
            }
            else
            {
                JOptionPane.showMessageDialog( parent , "Invalid file", "Error", JOptionPane.WARNING_MESSAGE );
            }
        }
        
        //Return an empty string if nothing is selected, or the file is invalid
        return "";
    }
    
    public static void removeFromIndex( DefaultTableModel table )
    {
        //Removes a row. Only removes the last row added, this should be changed to remove selected rows
        table.removeRow( indexNum - 1 );
        indexNum--;
        indexData[indexNum] = "";
        modData[indexNum] = 0;
        manageFileContent();
        //Changed to adjust for arraylist
        //indexData.remove(indexNum);
        //modData.remove(indexNum);
    }
    
    public static void rebuild()
    {
    }
    
    public static void writeFile()
    {
        //Attempt to write the contents of the index to a file to save between runs
        try ( FileWriter fw = new FileWriter( "Index.txt" ); BufferedWriter bw = new BufferedWriter( fw ) )
        {
            for( int i = 0; i <= indexNum; ++i )
            {
                //Changed to adjust for arraylist
                //boolean check = indexData.isEmpty();
                if( !indexData[i].isEmpty() )
                {
                    bw.append( indexData[i] + " ; " + modData[i] );
                    bw.newLine();
                }
            }
        }
        catch( IOException ex )
        {
            System.err.println( ex );
        }
        manageFileContent();
    }
    
    public static void readFile( DefaultTableModel table )
    {
        //Reads in the data from the saved file
        try ( BufferedReader br = new BufferedReader( new FileReader( "Index.txt" ) ) )
        {
            String line;
            while( ( line = br.readLine() ) != null )
            {
                String[] load = line.split( " ; " );
                indexData[indexNum] = load[0];
                modData[indexNum] = Long.valueOf( load[1] );
                
                indexNum++;
            }
            
            for( int i = 0; i < indexNum; ++i )
            {
                table.addRow( new Object[] { indexData[i], "Lookin' good" } );
            }
        }
        catch( IOException ex )
        {
            System.err.println( ex );
        }
        manageFileContent();
    }
    
    public static void updateLabel( JLabel label )
    {
        //used to keep track of the number of indexed items
        label.setText( "Number of indexed files: " + indexNum );
    }
    
    public static void manageFileContent()
    {
        for( int i = 0; i < indexNum; ++i )
        {
            try ( BufferedReader br = new BufferedReader( new FileReader( indexData[i] ) ) )
            {
                String line;
                while( ( line = br.readLine() ) != null )
                {
                    //remove non-alphanumeric characters, set to lowercase, remove duplicates
                    line = line.replaceAll("[^a-zA-Z0-9 ]","");
                    line = line.toLowerCase();
                    String[] stringArray = line.split( " " );
                    
                    for( int j = 0; j < stringArray.length; ++j )
                    {
                        mapInts.add(j);
                        invertIndex.put( stringArray[j], mapInts );
                    }
                }
            }
            catch( IOException ex )
            {
                System.err.println( ex );
            }
        }
    }
    
    public static void main( String[] args )
    {
        maintenanceFrame();
        searchFrame();
    }
}