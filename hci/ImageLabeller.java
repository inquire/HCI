package hci;


import hci.utils.MenuContainer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import hci.utils.CustomImage;
import hci.utils.Polygon;
import hci.utils.Metadata;

/**
 * Main class of the program - handles display of the main window
 * @author Michal
 *
 */
public class ImageLabeller extends JFrame {
	/**
	 * some java stuff to get rid of warnings
	 */
	private static final long serialVersionUID = 1L;
	final JFileChooser fc = new JFileChooser();
	File file;
	/**
	 * main window panel
	 */
	JPanel appPanel = null;
	
	/**
	 * toolbox - put all buttons and stuff here!
	 */
	JPanel toolboxPanel = null;
	
	/**
	 * image panel - displays image and editing area
	 */
	ImagePanel imagePanel = null;
	
	/**
	 * handles New Object button action
	 */
	
	public void addNewPolygon() {
		imagePanel.addNewPolygon();
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		imagePanel.paint(g); //update image panel
	}
	
	/**
	 * sets up application window
	 * @param imageFilename image to be loaded for editing
	 * @throws Exception
	 */
	public void setupGUI(String imageFilename) throws Exception {
		this.addWindowListener(new WindowAdapter() {
		  	public void windowClosing(WindowEvent event) {
		  		//here we exit the program (maybe we should ask if the user really wants to do it?) 		- done
		  		//maybe we also want to store the polygons somewhere? and read them next time 				- pending
		  		
		  		
		  // ========================= Prompting the user for exit =========================
		  		
		  		Object [] options = {"Yes", "No"};
		  		int result = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Really quit?", JOptionPane.YES_NO_CANCEL_OPTION, 
		  				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		  			if (result == JOptionPane.YES_OPTION){
		  				
		  				Metadata saveFile = new Metadata();
		  				saveFile.saveMetadata(imagePanel.getCustomImage(),imagePanel.getCustomImage().getFileName());
		  				CustomImage something = saveFile.loadMetadata(imagePanel.getCustomImage().getFileName());
		  				System.out.println(something.size());
		  				
		  				
		  				System.out.println("Bye bye!");
		  				System.exit(0);
		  			}else{
		  				;
		  			}
		  // ================================================================================
		  			
		  	}
		});

		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);
		
        //Create and set up the image panel.
		imagePanel = new ImagePanel(imageFilename);
		imagePanel.setOpaque(true); //content panes must be opaque
		
        appPanel.add(imagePanel);

        //create toolbox panel
        toolboxPanel = new JPanel();
        GridLayout glayout = new GridLayout(0,1,0,10);
        //toolboxPanel.setLayout(new BoxLayout(toolboxPanel, BoxLayout.Y_AXIS));
        toolboxPanel.setLayout(glayout);
        toolboxPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        
        /**
         * Menu Containing Buttons
         * 
         * @author Daniel
         */
        MenuContainer menu = new MenuContainer();
        
        JButton newPolyButton =  menu.getNewPolyButton();
        newPolyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    	addNewPolygon();
			}
		});
        toolboxPanel.add(newPolyButton);
        
        JButton testButton = menu.getTestButton();
        testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int returnVal =fc.showOpenDialog(fc);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					try{
						imagePanel.setImage(file.getPath());
					} catch(IOException e1){
						e1.printStackTrace();
					}
					//clear the previous polygons
					imagePanel.polygonsList.clear();
					repaint();
				}
			    	
			}
		});
        toolboxPanel.add(testButton);
        
        JButton saveButton = menu.getSaveButton();
        saveButton.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		Metadata saveFile = new Metadata();
  				saveFile.saveMetadata(imagePanel.getCustomImage(),imagePanel.getCustomImage().getFileName());
  				CustomImage something = saveFile.loadMetadata(imagePanel.getCustomImage().getFileName());
  				System.out.println(something.size());
        	}
        });
        toolboxPanel.add(saveButton);
        
      
		//add toolbox to window
		appPanel.add(toolboxPanel);
		
		//display all the stuff
		this.pack();
        this.setVisible(true);
	}
	

	/**
	 * Runs the program
	 * @param argv path to an image
	 */
	public static void main(String argv[]) {
		try {
			//create a window and display the image
			ImageLabeller window = new ImageLabeller();
			window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			window.setupGUI(argv[0]);
		} catch (Exception e) {
			System.err.println("Image: " + argv[0]);
			e.printStackTrace();
		}
	}
}
