package hci;


// Custom classes imports

import hci.utils.BetaPolygon;
import hci.utils.CustomImage;
import hci.utils.MenuContainer;
import hci.utils.Metadata;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

//import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Main class of the program - handles display of the main window
 * @author Diana
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
	
	// Defines the elements that make the visual side of the application
	
	JTextField textField;
	JTextArea textArea;
	DefaultListModel model = new DefaultListModel();
    JList list = new JList(model);
    JPanel rightPanel = null;
    JPanel buttonPanel = null;
    JScrollPane scroll = null;
    
	
    /**
     * Adds a new polygon to the image panel.
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
		  		
		  		Object [] options = {"Yes", "No"};
		  		
		  		/**
		  		 * Prompts the user for exit
		  		 */
		  		
		  		int result = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Really quit?", JOptionPane.YES_NO_CANCEL_OPTION, 
		  				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		  			if (result == JOptionPane.YES_OPTION){
		  				
		  				int result1 = JOptionPane.showOptionDialog(null, "Do you want to save?", "Really quit?", JOptionPane.YES_NO_CANCEL_OPTION, 
				  				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		  				if (result1 == JOptionPane.YES_OPTION){
		  					
		  					Metadata saveFile = new Metadata();
			  				saveFile.saveMetadata(imagePanel.getCustomImage(),imagePanel.getCustomImage().getFileName());
			  				CustomImage something = saveFile.loadMetadata(imagePanel.getCustomImage().getFileName());
			  				System.out.println(something.size());
		  					
		  				}
		  				
		  				System.out.println("Bye bye!");
		  				System.exit(0);
		  			}else{
		  				;
		  			}
		  	}
		});


		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);
	
		//setup toolbox panel 
		
		toolboxPanel = new JPanel();
		toolboxPanel.setLayout(new GridLayout(0,1,0,10));
		toolboxPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// setup buttonPanel
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,1,0,10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			
		/**
		 * Instantiates the buttons that make up right side menu.
		 */
		
        MenuContainer menu = new MenuContainer();
        JButton newPolyButton =  menu.getNewPolyButton();
        
        /**
         * Take care of user generated errors when adding new polygons.
         */
        
        newPolyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (imagePanel.currentPolygon.size() < 3){
					JOptionPane.showMessageDialog(null,"You must have at least 3 points!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				String s = (String)JOptionPane.showInputDialog(null,"Select a tag!","Select Tag",JOptionPane.PLAIN_MESSAGE);
				if (s != null){
					if(s.length()>0){
						imagePanel.currentPolygon.setTag(s);
						model.addElement(imagePanel.currentPolygon);
						addNewPolygon();
					} else {
						JOptionPane.showMessageDialog(null,"You must enter an object tag","Error",JOptionPane.ERROR_MESSAGE);
					} 
				}
			}
		});
        toolboxPanel.add(newPolyButton);
        
        /**
         * The parameters and actions for the load button.
         */
        
        JButton loadButton = menu.getTestButton();
        loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object [] options = {"Yes", "No"};
				int result1 = JOptionPane.showOptionDialog(null, "Do you want to save?", "Really quit?", JOptionPane.YES_NO_CANCEL_OPTION, 
		  				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
  				if (result1 == JOptionPane.YES_OPTION){
  					
  					Metadata saveFile = new Metadata();
	  				saveFile.saveMetadata(imagePanel.getCustomImage(),imagePanel.getCustomImage().getFileName());
	  				CustomImage something = saveFile.loadMetadata(imagePanel.getCustomImage().getFileName());
	  				System.out.println(something.size());
  					
  				}
  				
  				/**
  				 * Loads the file alongside with the metadata.
  				 */
  				
				File file = null;
				int returnVal = fc.showOpenDialog(fc);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					file = fc.getSelectedFile();
					try{
						imagePanel.setImage(file.getPath());
					} catch(IOException e1){
						e1.printStackTrace();
					}
					//clear the previous polygons
					imagePanel.polygonsList.clear();
					model.clear();
					imagePanel.currentPolygon = new BetaPolygon();
					
					Metadata loadFile = new Metadata();
					if(loadFile.checkMetadata(file.getPath())){
						CustomImage something = loadFile.loadMetadata(imagePanel.getCustomImage().getFileName());
		  				System.out.println("Contained polygons are: " + something.size());
		  				imagePanel.loadPolygons(something);
		  				for (BetaPolygon poli : imagePanel.polygonsList){
		  					model.addElement(poli);
		  				}
					}
		  			repaint();
		  			rightPanel.updateUI();
				}		    	
			}
		});
        toolboxPanel.add(loadButton);
        
        /**
         * The parameter and actions for the save button.
         */
        
        JButton saveButton = menu.getSaveButton();
        saveButton.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		Metadata saveFile = new Metadata();
  				saveFile.saveMetadata(imagePanel.getCustomImage(),imagePanel.getCustomImage().getFileName());
  				//CustomImage something = saveFile.loadMetadata(imagePanel.getCustomImage().getFileName());
  				repaint();
        	}
        });
        toolboxPanel.add(saveButton);
        
        /**
         * The parameters and actions for the clear button.
         */
        
        JButton clearButton = menu.getClearButton();
        toolboxPanel.add(clearButton);
        
        /**
         * The parameters and actions for the delete button.
         */
        
        JButton deleteButton = menu.getDeleteButton();
        deleteButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		//list.getSelectedValue();
        		BetaPolygon polygon = new BetaPolygon();
        		polygon = (BetaPolygon)list.getSelectedValue();
        		if (polygon != null){
        			int result = JOptionPane.showConfirmDialog(null, "Are you sure do you want to delete "+polygon.getTag()+" tag?","Delete",JOptionPane.YES_NO_OPTION);
        			if (result == JOptionPane.YES_OPTION){
        				imagePanel.polygonsList.removePolygon(polygon);
        				model.removeElement(list.getSelectedValue());
        				repaint();
        			}
        		}
        	}
        });
        buttonPanel.add(deleteButton);
        
        /**
         * The parameters and actions for the renameButton.
         */
        
        JButton renameButton = menu.getRenameButton();
        renameButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		BetaPolygon poligon = new BetaPolygon();
        		poligon = (BetaPolygon) list.getSelectedValue();
        		//int index = list.getSelectedIndex();
        		if (poligon != null){
        			String s = (String)JOptionPane.showInputDialog(null,"Rename your tag","Rename",JOptionPane.PLAIN_MESSAGE);
        			if (s != null) {
        				 if (s.length() > 0){
        					poligon.setTag(s);
        					rightPanel.updateUI();
        				} else {
        					JOptionPane.showMessageDialog(null,"You must enter a tag name","Error",JOptionPane.ERROR_MESSAGE);
        				}
        			} 
        		} 
        	
        	}
        }); 
        buttonPanel.add(renameButton);
        
        
      //Create and set up the image panel.
      
        	imagePanel = new ImagePanel(imageFilename);
      		imagePanel.setOpaque(true); //content panes must be opaque
      		
              appPanel.add(imagePanel);
              
              //create the whole right panel
              rightPanel = new JPanel();
              rightPanel.setLayout(new BorderLayout());
              rightPanel.add(toolboxPanel,BorderLayout.NORTH);
              
              //create the scroll list
              JScrollPane scroll = new JScrollPane(list);
              scroll.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
              scroll.setPreferredSize(new Dimension(200, 150));
              rightPanel.add(scroll,BorderLayout.CENTER);
              
              //colour the selected polygon from the list
              list.addListSelectionListener(new ListSelectionListener(){
              	public void valueChanged(ListSelectionEvent e){
              		BetaPolygon polygon = new BetaPolygon();
              		polygon = (BetaPolygon) list.getSelectedValue();
              		if (polygon != null) {
              			for (BetaPolygon polygon1 : imagePanel.polygonsList) {
              				polygon1.isSelected = false;
              			}
              			polygon.isSelected = true;
              			repaint();
              		}
              	}
              });
              
              /**
               * Adds a mouseListener for selecting the tag edit field when clicking the polygon in the image.
               */
              
              imagePanel.addMouseListener(new MouseAdapter(){
                	public void mousePressed(MouseEvent evt){
                		//System.out.println("list clicked");
                		BetaPolygon b = new BetaPolygon();
                		b = (BetaPolygon) imagePanel.testInsideShape(evt.getX(), evt.getY());
                		if(b != null){                			
                			list.setSelectedValue(b, true);
                		}
                	}
              });
              
              /**
               * Adds a mouseListerner for repainting on mouse release.
               */
              
              imagePanel.addMouseListener(new MouseAdapter(){
            	 public void mouseReleased(MouseEvent evt){
            		 repaint();
            	 }
              });
  
              //add the buttons below
              rightPanel.add(buttonPanel,BorderLayout.SOUTH);
              
              appPanel.add(rightPanel);
              //appPanel.add(toolboxPanel);
              
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
