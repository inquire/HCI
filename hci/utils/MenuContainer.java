package hci.utils;

import hci.ImagePanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
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
	private JButton saveButton;
	//private BoxLayout layout;
	//private Container container;
	
	
	public MenuContainer(){
		super("MenuContainer Mark1");
		
		layout = new FlowLayout();
		//layout = new BoxLayout(container,BoxLayout.X_AXIS);
		//container = getContentPane();
		setLayout(layout);
		
		/* Button New Polygon */
		
		newPolyButton = new JButton("New object");
		newPolyButton.setMnemonic(KeyEvent.VK_N);
		newPolyButton.setSize(100,20);
		newPolyButton.setEnabled(true);
		newPolyButton.setToolTipText("Click to add new object");
		
		/* Test Button */
		
		testButton = new JButton("Load");
		testButton.setMnemonic(KeyEvent.VK_N);
		testButton.setSize(100,20);
		testButton.setEnabled(true);
		testButton.setToolTipText("Click to load new picture");
		
		/* Sa ve Button*/
		
		saveButton = new JButton("Save");
		saveButton.setMnemonic(KeyEvent.VK_N);
		saveButton.setSize(100, 20);
		saveButton.setEnabled(true);
		saveButton.setToolTipText("Click to save current configuration");
		
	}

	public JButton getNewPolyButton(){
		return newPolyButton;
	}

	public JButton getTestButton(){
		return testButton;
	}
	
	public JButton getSaveButton(){
		return saveButton;
	}
}	

	

