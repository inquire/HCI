package hci.utils;

import hci.ImagePanel;

import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JButton;

/**
 * MenuContainer class contains all the buttons that make up the menu with
 * their properties
 * 
 * @author Jack
 *
 */


public class MenuContainer extends JFrame{
	
	private static final long serialVersionUID = 1L;
	ImagePanel imagePanel = null;
	
	private JButton newPolyButton;
	private JButton testButton;
	private FlowLayout layout;
	//private Container container;
	
	
	public MenuContainer(){
		super("MenuContainer Mark1");
		
		layout = new FlowLayout();
		//container = getContentPane();
		setLayout(layout);
		
		/* Button New Polygon */
		
		newPolyButton = new JButton("New object");
		newPolyButton.setMnemonic(KeyEvent.VK_N);
		newPolyButton.setSize(50, 20);
		newPolyButton.setEnabled(true);
		newPolyButton.setToolTipText("Click to add new object");
		
		/* Test Button */
		
		testButton = new JButton("Load");
		testButton.setMnemonic(KeyEvent.VK_N);
		testButton.setSize(50, 20);
		testButton.setEnabled(true);
		testButton.setToolTipText("Click to load new picture");
		
	}

	public JButton getNewPolyButton(){
		return newPolyButton;
	}

	public JButton getTestButton(){
		return testButton;
	}
	
	
}	

	

