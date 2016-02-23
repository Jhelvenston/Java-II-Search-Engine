package searchengineproject;

import javax.swing.*;
import java.awt.*;
/*
 * Search engine project
 */
public class SearchEngineProject
{
        //private JButton searchButton, maintenanceButton, aboutButton;
        private JLabel titleLabel, termsLabel, indexedLabel;
        private JRadioButton allRadio, anyRadio, exactRadio;
        private JTextArea resultsArea;
        private JTextField searchField;
        private JPanel top;
    
    public static void main( String[] args )
    {
        //Create the frame
        JFrame searchFrame = new JFrame( "Search engine" );
        searchFrame.setSize( 600, 500 );
        searchFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        searchFrame.setVisible( true );
    }
    
        public void search()
    {
        //GUI components initialized here
        titleLabel = new JLabel( "Search Engine", JLabel.TOP );
        searchField = new JTextField();
        termsLabel = new JLabel();
        indexedLabel = new JLabel();
        allRadio = new JRadioButton();
        anyRadio = new JRadioButton();
        exactRadio = new JRadioButton();
        resultsArea = new JTextArea();
        JButton searchButton = new JButton();
        JButton maintenanceButton = new JButton();
        JButton aboutButton = new JButton();

        titleLabel.setFont(new java.awt.Font("Tahoma", 0, 36));
        termsLabel.setText("File Terms");
        allRadio.setText("Search All File Terms");
        anyRadio.setText("Search Any File Terms");
        exactRadio.setText("Search Exact Phrase");

        resultsArea.setColumns(20);
        resultsArea.setRows(5);

        searchButton.setText("Search");
        maintenanceButton.setText("Maintenance");
        aboutButton.setText("About");
    }
}