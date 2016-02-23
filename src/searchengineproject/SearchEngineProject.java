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
        searchFrame.setSize( 800, 500 );
        searchFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //GUI components initialized here
        
        JLabel titleLabel, termsLabel, indexedLabel;
        JRadioButton allRadio, anyRadio, exactRadio;
        JTextArea resultsArea;
        JTextField searchField;
        
        titleLabel = new JLabel( "Search Engine" );
        termsLabel = new JLabel( "File Terms" );
        indexedLabel = new JLabel();
        searchField = new JTextField();
        allRadio = new JRadioButton();
        anyRadio = new JRadioButton();
        exactRadio = new JRadioButton();
        resultsArea = new JTextArea();
        JButton searchButton = new JButton();
        JButton maintenanceButton = new JButton();
        JButton aboutButton = new JButton();

        titleLabel.setFont(new java.awt.Font("Tahoma", 0, 36));
        allRadio.setText("Search All File Terms");
        anyRadio.setText("Search Any File Terms");
        exactRadio.setText("Search Exact Phrase");

        resultsArea.setColumns(20);
        resultsArea.setRows(5);

        searchButton.setText("Search");
        maintenanceButton.setText("Maintenance");
        aboutButton.setText("About");

        searchField.setSize( 800, 500 );
        
        JPanel top = new JPanel();
        top.add( titleLabel );
        searchFrame.add( top, BorderLayout.NORTH );
        
        JPanel mid = new JPanel();
        mid.add( termsLabel );
        mid.add( searchField );
        mid.add( searchButton );
        searchFrame.add( mid, BorderLayout.CENTER  );
        searchFrame.setVisible( true );
    }
}