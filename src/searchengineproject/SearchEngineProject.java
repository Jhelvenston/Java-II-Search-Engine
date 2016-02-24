package searchengineproject;

import javax.swing.*;
import java.awt.*;
/*
 * Search engine project
 */
public class SearchEngineProject
{
    public static void main( String[] args )
    {
        //Create the frame
        JFrame searchFrame = new JFrame( "Search engine" );
        JFrame maintenanceFrame = new JFrame( "Maintenance" );
        searchFrame.setSize( 800, 500 );
        maintenanceFrame.setSize( 800,500 );
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
        allRadio = new JRadioButton( "Search All File Terms" );
        anyRadio = new JRadioButton( "Search Any File Terms" );
        exactRadio = new JRadioButton( "Search Exact Phrase" );
        resultsArea = new JTextArea();
        JButton searchButton = new JButton( "Search" );
        JButton maintenanceButton = new JButton( "Maintenance" );
        JButton aboutButton = new JButton( "About" );
        
        titleLabel.setFont(new Font("Tahoma", 0, 36));
        resultsArea.setColumns(20);
        resultsArea.setRows(5);
        
        //Objects for maintenance frame instantiated, initialized, and added here
        String[] columnNames = {"File Name","Status"};
        Object[][] data = {{"Dummy data name", "Dummy data status"}};
        
        JTable indexTable = new JTable( data, columnNames );
        
        JScrollPane scrollPane = new JScrollPane( indexTable );
        indexTable.setFillsViewportHeight( true );
        
        JPanel contentPane2 = new JPanel();
        contentPane2.setLayout( new BoxLayout( contentPane2, BoxLayout.Y_AXIS ) );
        contentPane2.add( scrollPane );

        maintenanceFrame.add( contentPane2 );
        
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
        
        JPanel panel4 = new JPanel();
        panel4.add( resultsArea );
        contentPane.add(panel4 );
        
        JPanel panel5 = new JPanel();
        panel5.add( maintenanceButton );
        panel5.add( aboutButton );
        contentPane.add(panel5 );
        
        maintenanceFrame.setVisible( true );
        searchFrame.setVisible( true );
    }
}