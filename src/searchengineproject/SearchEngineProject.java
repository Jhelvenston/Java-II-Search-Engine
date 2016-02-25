package searchengineproject;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
/*
 * Search engine project
 *
 * James Helvenston, Mark Helvenston, Daniel Matheny
 */
public class SearchEngineProject
{
    //Boolean used to track if maintenance window is open or closed
    private static boolean windowOpen;
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
        indexedLabel = new JLabel( "Number of indexed files: " );
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
        
        Object[][] data = {{"Dummy data name", "Dummy data status"}};
        
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
        tableMod.addRow( new Object[] { "Dummy data name", "Dummy data status" } );
        
        
        JScrollPane scrollPane = new JScrollPane( indexTable );
        indexTable.setFillsViewportHeight( true );
        contentPane.add( scrollPane );
        
        addButton.addActionListener((ActionEvent ae) -> 
        {
                String newItem = addToIndex();
                tableMod.addRow( new Object[] { "Dummy data name", "Dummy data status" } );
        } );
        
        removeButton.addActionListener((ActionEvent ae) -> 
        {
                removeFromIndex( tableMod );
        } );
        
        rebuildButton.addActionListener((ActionEvent ae) -> 
        {
                rebuild();
        } );
        
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
        String results = "Dummy search results";
        return results;
    }
    
    public static String addToIndex()
    {
        //Returns dummy data, adding items to index will replace this later
        String indexItem = "Dummy index data";
        return indexItem;
    }
    
    public static void removeFromIndex( DefaultTableModel table )
    {

    }
    
    public static void rebuild()
    {
        
    }
    
    public static void main( String[] args )
    {
        maintenanceFrame();
        searchFrame();
    }
}