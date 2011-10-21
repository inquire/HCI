package hci.utils;

import hci.ImagePanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

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
	
	//ImagePanel imagePanel = new ImagePanel();
	private JButton newPolyButton;
	private JButton loadButton;
	private FlowLayout layout;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton renameButton;
	private JButton clearButton;
	
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
		
		loadButton = new JButton("Load");
		loadButton.setMnemonic(KeyEvent.VK_N);
		loadButton.setSize(100,20);
		loadButton.setEnabled(true);
		loadButton.setToolTipText("Click to load new picture");
		
		/* Sa ve Button*/
		
		saveButton = new JButton("Save");
		saveButton.setMnemonic(KeyEvent.VK_N);
		saveButton.setSize(100, 20);
		saveButton.setEnabled(true);
		saveButton.setToolTipText("Click to save current configuration");
		
		deleteButton = new JButton("Delete");
		deleteButton.setMnemonic(KeyEvent.VK_N);
		deleteButton.setSize(100,20);
		deleteButton.setEnabled(true);
		deleteButton.setToolTipText("Click to delete the selected polygon");
		
		renameButton = new JButton("Rename");
		renameButton.setMnemonic(KeyEvent.VK_N);
		renameButton.setSize(100,20);
		renameButton.setEnabled(true);
		renameButton.setToolTipText("Click to rename the tag");
		
		clearButton = new JButton("Clear");
		clearButton.setMnemonic(KeyEvent.VK_N);
		clearButton.setSize(100,20);
		clearButton.setEnabled(true);
		clearButton.setToolTipText("Click to clear the unfinished polygon");
		
		
		
		
	}

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

	

