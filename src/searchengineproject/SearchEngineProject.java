package searchengineproject;

import javax.swing.*;
/*
 * Search engine project
 */
public class SearchEngineProject
{
    public void init ()
    {
        //GUI components initialized here
        JLabel searchEng = new JLabel ("Search Engine");
        JLabel searchInd = new JLabel ("Search Engine Index");
    }
    
    public static void main( String[] args )
    {
        //Create the frame
        JFrame searchFrame = new JFrame( "Search engine" );
        searchFrame.setSize( 600, 500 );
        searchFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        searchFrame.setVisible( true );
    }
    
}