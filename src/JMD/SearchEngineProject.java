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
    
    //Boolean used to track if maintenance window is open or closed
    private static boolean windowOpen;
    private static int indexNum = 0;
  
    
    //Map used to contain the search string the user inputs
    private static final HashMap<String, ArrayList> invertIndex = new HashMap<>();
    
    private static final ArrayList<String> indexData = new ArrayList<String>();
    private static final ArrayList modData = new ArrayList();
    private static final ArrayList<String> fileContent = new ArrayList<String>();
    
    private static final JFrame searchFrame = new JFrame( "Search engine" );
    private static final JFrame maintenanceFrame = new JFrame( "Maintenance" );
    
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
            String searchType = "";
            if ( allRadio.isSelected() )
            {
                searchType = "ALL";
            } else if ( anyRadio.isSelected() )
            {
                searchType = "ANY";
            }
                
            String searchResults = search( searchField.getText(), searchType );
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
        allRadio.setSelected( true );
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
                manageFileContent();
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
        
        JPanel panel2 = new JPanel();
        panel2.add( addButton );
        panel2.add( removeButton );
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
    
    public static String search( String searchText, String searchType )
    {
        StringBuilder sb = new StringBuilder();
        HashSet<String> removeDoubles = new HashSet();
        
        //strip out all non-alphanumeric characters from the text entered and set it to lowercase
        searchText = searchText.replaceAll("[^a-zA-Z0-9 ]","");
        searchText = searchText.toLowerCase();
        //split it individual words, separated by spaces, and put each word into an array
        String[] keys = searchText.split( " " );

        if( searchType.equals( "ALL" ) )
        {
            for( int i = 0; i < keys.length; ++i ) //this loop will run through each word in the array, testing it against the invertIndex keys
            {
                if ( invertIndex.containsKey( keys[i] ) )
                {
                    ArrayList<WordPos> intList = invertIndex.get( keys[i] );

                    for( int j = 0; j < intList.size(); ++j )
                    {
                        //this should pull the appropriate index number pointing to a certain filename
                        WordPos findWord = intList.get( j );
                        int find = findWord.getWord();
                        removeDoubles.add(indexData.get(find));
                    }
                }
            }
        } else if ( searchType.equals( "ANY" ) )
        {
            ArrayList<Integer> fileSet = new ArrayList<>();
            ArrayList<Integer> selectedFiles = new ArrayList<>();
            for( int i = 0; i < indexNum; ++i ) //fill fileSet with numbers corresponding to indexed files
            {
                fileSet.add( i );
            }
            
            for( int i = 0; i < keys.length; ++i ) //runs through search terms entered by user
            {
                if ( invertIndex.containsKey( keys[i] ) )
                {
                    ArrayList<WordPos> intList = new ArrayList<>( invertIndex.get( keys[i] ) );
                    
                    for( int j = 0; j < intList.size(); ++j ) //runs through the arraylist in the inverted index associated with that term
                    {
                        WordPos findWord = intList.get( j );
                        
                        int compare = findWord.getWord();
                        fileSet.removeIf((Integer x) -> {
                            boolean remove = x == compare;
                            if ( remove )
                            {
                                selectedFiles.add( x );
                            }
                            return remove;
                        } );
                    }
                }
                fileSet.clear();
                fileSet.addAll( selectedFiles );
                selectedFiles.clear();
            }
            
            //then run through the remaining numbers
            for( int i = 0; i < fileSet.size(); ++i )
            {
                int find;
                find = fileSet.get( i );
                removeDoubles.add( indexData.get( find ) );
            }
        } else
        {
            //pull the set of { file, pos } pairs relating to the first search term from the inverted index
            ArrayList<WordPos> initialFiles = new ArrayList<>();
            ArrayList<WordPos> newIndex = new ArrayList<>();
            if ( invertIndex.containsKey( keys[0] ) )
            {
                initialFiles = invertIndex.get( keys[0] );
            }
            
            //loop through the remaining search terms
            for( int i = 1; i < keys.length; ++i )
            {
                //loop through the set of pairs
                if ( invertIndex.containsKey( keys[i] ) )
                {
                    for( int j = 0; j < initialFiles.size(); ++j )
                    {
                        ArrayList<WordPos> fileSet = invertIndex.get( keys[i] );
                        //check if the word in the next position is the same as the current search term
                        //maybe do this by pulling the current search term out of the inverted index and checking its array list for matches
                        for( WordPos k : fileSet )
                        {
                            if( ( k.getWord() == initialFiles.get( j ).getWord() ) && ( k.getPos() == initialFiles.get( j ).getPos() + 1 ) )
                            {
                                //if so, add it to the list of pairs
                                newIndex.add(k);
                            }
                        }
                    }

                    initialFiles = newIndex;
                }
            }
            
            for( int i = 0; i < initialFiles.size(); ++i )
            {
                int find;
                find = initialFiles.get( i ).getWord();
                removeDoubles.add( indexData.get( find ) );
            }
        }
        
        String[] reDubArray = new String[removeDoubles.size()];
        removeDoubles.toArray( reDubArray );
        
        for( int i = 0; i < removeDoubles.size(); ++i )
        {
            sb.append( reDubArray[i] + System.getProperty( "line.separator" ) );
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
                indexData.add(indexNum,filename); // + " ; " + lastMod;
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
        indexData.add(indexNum,"");
        modData.add(indexNum, 0);
        manageFileContent();
    }
    
    public static void writeFile()
    {
        //Attempt to write the contents of the index to a file to save between runs
        try ( FileWriter fw = new FileWriter( "Index.txt" ); BufferedWriter bw = new BufferedWriter( fw ) )
        {
            for( int i = 0; i <= indexNum; ++i )
            {
                if( !indexData.get(i).isEmpty() )
                {
                    bw.append( indexData.get(i) + " ; " + modData.get(i) );
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
                indexData.add(indexNum,load[0]);
                modData.add(indexNum,Long.valueOf( load[1] ));
                
                indexNum++;
            }
            
            for( int i = 0; i < indexNum; ++i )
            {
                table.addRow( new Object[] { indexData.get(i), "Lookin' good" } );
            }
            manageFileContent();
        }
        catch( IOException ex )
        {
            System.err.println( ex );
        }
    }
    
    public static void updateLabel( JLabel label )
    {
        //used to keep track of the number of indexed items
        label.setText( "Number of indexed files: " + indexNum );
    }
    
    public static void manageFileContent()
    {
        invertIndex.clear();
        for( int i = 0; i < indexNum; ++i ) //"i" will track which file is currently being read from
        {
            try ( BufferedReader br = new BufferedReader( new FileReader( indexData.get(i) ) ) )
            {
                String line;
                while( ( line = br.readLine() ) != null )
                {
                    //remove non-alphanumeric characters, set to lowercase
                    line = line.replaceAll("[^a-zA-Z0-9 ]","");
                    line = line.toLowerCase();
                    String[] stringArray = line.split( " " );
                    
                    for( int j = 0; j < stringArray.length; ++j ) //"j" will track the position of the word being read
                    {
                        //int[] intArray = new int[ stringArray.length ];
                        ArrayList<WordPos> mapInts = new ArrayList<>();
                        
                        
                        if( invertIndex.containsKey( stringArray[j] ) && !mapInts.equals( invertIndex.get( stringArray[j] ) ) )
                        {
                            mapInts.addAll( invertIndex.get( stringArray[j] ) );
                        }
                        WordPos wordPos = new WordPos( i, j );
                        mapInts.add( wordPos );
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

class WordPos
{
    public final int word;
    public final int pos;
    
    WordPos( int word, int pos )
    {
        this.word = word;
        this.pos = pos;
    }
    
    int getWord()
    {
        return word;
    }
    int getPos()
    {
        return pos;
    }
}