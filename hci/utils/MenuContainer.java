package hci.utils;

import hci.ImagePanel;

import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

//import javax.swing.AbstractAction;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * MenuContainer class contains all the buttons that make up the menu with
 * their properties
 * 
 * @author Daniel & Diana
 *
 */


public class MenuContainer extends JFrame{
	
	private static final long serialVersionUID = 1L;
	ImagePanel imagePanel = null;
	
	/* Defining the layout and the buttons of the application*/
	
	private FlowLayout layout;
	private JButton newPolyButton;
	private JButton loadButton;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton renameButton;
	private JButton clearButton;
	
	public MenuContainer(){
		super("MenuContainer Mark1");
		
		layout = new FlowLayout();
		setLayout(layout);
		
		/* Button New Polygon */
		
		newPolyButton = new JButton("New object");
		newPolyButton.setMnemonic(KeyEvent.VK_N);
		newPolyButton.setSize(100,20);
		newPolyButton.setEnabled(true);
		newPolyButton.setToolTipText("Click to add new object");
		
		/* Load Button */
		
		loadButton = new JButton("Load");
		loadButton.setMnemonic(KeyEvent.VK_N);
		loadButton.setSize(100,20);
		loadButton.setEnabled(true);
		loadButton.setToolTipText("Click to load new picture");
		
		/* Save Button*/
		
		saveButton = new JButton("Save");
		saveButton.setMnemonic(KeyEvent.VK_N);
		saveButton.setSize(100, 20);
		saveButton.setEnabled(true);
		saveButton.setToolTipText("Click to save current configuration");
		
		/* Delete Button */

		deleteButton = new JButton("Delete");
		deleteButton.setMnemonic(KeyEvent.VK_N);
		deleteButton.setSize(100,20);
		deleteButton.setEnabled(true);
		deleteButton.setToolTipText("Click to delete the selected polygon");
		
		/* Rename Button */
		
		renameButton = new JButton("Rename");
		renameButton.setMnemonic(KeyEvent.VK_N);
		renameButton.setSize(100,20);
		renameButton.setEnabled(true);
		renameButton.setToolTipText("Click to rename the tag");
		
		/* Clear Button */
		
		clearButton = new JButton("Clear");
		clearButton.setMnemonic(KeyEvent.VK_N);
		clearButton.setSize(100,20);
		clearButton.setEnabled(true);
		clearButton.setToolTipText("Click to clear the unfinished polygon");
			
	}

	
	/* Retrieves button preferences & specifications */
	
	public JButton getNewPolyButton(){
		return newPolyButton;
	}

	public JButton getTestButton(){
		return loadButton;
	}
	
	public JButton getSaveButton(){
		return saveButton;
	}
	
	public JButton getDeleteButton(){
		return deleteButton;
	}
	
	public JButton getRenameButton(){
		return renameButton;
	}
	
	public JButton getClearButton(){
		return clearButton;
	}
}	

	

