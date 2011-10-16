package hci;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.Random;

// My imports
import java.awt.Polygon;
import hci.utils.*;

/**
 * Handles image editing panel
 * @author Michal
 *
 */
public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener {
	/**
	 * some java stuff to get rid of warnings
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * image to be tagged
	 */
	BufferedImage image = null;
	
	/**
	 * list of current polygon's vertices 
	 */
	BetaPolygon currentPolygon = null;
	
	/**
	 * list of polygons
	 */
	CustomImage polygonsList = null;
	
	String filename = null;
	
	boolean drag = false;
	BetaPolygon currentModified = null;
	
	/** 
	 * Cursor information regarding different events
	 * 
	 */
	
	int[] clicked = new int[2];
	int[] released = new int[2];
	
	
	// ============================ Random Colors ===========================
	
	Color[] polyColor = new Color[]{Color.pink, Color.green, Color.blue, Color.cyan, Color.magenta, Color.orange, Color.red, Color.yellow};
	Random rand = new Random();

	private boolean polyInProgress;
	
	// ======================================================================
	
	/**
	 * default constructor, sets up the window properties
	 */
	
	public ImagePanel() {
		currentPolygon = new BetaPolygon();
		polygonsList = new CustomImage();
		

		this.setVisible(true);

		Dimension panelSize = new Dimension(800, 600);
		this.setSize(panelSize);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * extended constructor - loads image to be labelled
	 * @param imageName - path to image
	 * @throws Exception if error loading the image
	 */
	public ImagePanel(String imageName) throws Exception{
		this();
		filename = imageName;
		setImage(imageName);
	}
	
	public void setImage(String imageName) throws IOException{
		image = ImageIO.read(new File(imageName));
		filename = (imageName);
		if (image.getWidth() > 800 || image.getHeight() > 600) {
			int newWidth = image.getWidth() > 800 ? 800 : (image.getWidth() * 600)/image.getHeight();
			int newHeight = image.getHeight() > 600 ? 600 : (image.getHeight() * 800)/image.getWidth();
			System.out.println("SCALING TO " + newWidth + "x" + newHeight );
			Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
			image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(scaledImage, 0, 0, this);
		}
	}

	/**
	 * Displays the image
	 */
	public void ShowImage() {
		Graphics g = this.getGraphics();
		
		if (image != null) {
			g.drawImage(
					image, 0, 0, null);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//display image
		ShowImage();
		
		//display all the completed polygons
		for(BetaPolygon polygon : polygonsList) {
			drawPolygon(polygon);
			finishPolygon(polygon);
		}
		
		//display current polygon
		drawPolygon(currentPolygon);
	}
	
	/**
	 * displays a polygon without last stroke
	 * @param polygon to be displayed
	 */
	public void drawPolygon(BetaPolygon polygon) {
		
		Graphics2D g = (Graphics2D)this.getGraphics();
		g.setColor(polygon.getColor());
		for(int i = 0; i < polygon.size(); i++) {
			Point currentVertex = polygon.getPoint(i);
			if (i != 0) {
				Point prevVertex = polygon.getPoint(i - 1);
				g.drawLine(prevVertex.getX(), prevVertex.getY(), currentVertex.getX(), currentVertex.getY());
			}
			g.fillOval(currentVertex.getX() - 5, currentVertex.getY() - 5, 10, 10);
		}
	}
	
	/**
	 * displays last stroke of the polygon (arch between the last and first vertices)
	 * @param polygon to be finished
	 */
	public void finishPolygon(BetaPolygon polygon) {
		//if there are less than 3 vertices than nothing to be completed
		if (polygon.size() >= 3) {
			Point firstVertex = polygon.getPoint(0);
			Point lastVertex = polygon.getPoint(polygon.size() - 1);
		
			Graphics2D g = (Graphics2D)this.getGraphics();
			g.setColor(polygon.getColor());
			g.drawLine(firstVertex.getX(), firstVertex.getY(), lastVertex.getX(), lastVertex.getY());
		}
	}
	
	/**
	 * moves current polygon to the list of polygons and makes pace for a new one
	 */
	public void addNewPolygon() {
		//finish the current polygon if any
		polyInProgress=false;
		if (currentPolygon != null ) {
			if(currentPolygon.size() < 3){
				System.out.println("Can't create polygon with less than 3 points Mr! Insert at least "+ (3 - currentPolygon.size()) + " points! " );
				return;
			}
			finishPolygon(currentPolygon);
			polygonsList.addPolygon(currentPolygon);
		}
		
		currentPolygon = new BetaPolygon();	
		
		// ======================== Assign Random Colors To Polygon ========================
		
			int whatColor = rand.nextInt(polyColor.length);
			currentPolygon.setColor(polyColor[whatColor]);
		
		// =================================================================================	
			
	}
	
	public CustomImage getCustomImage(){
		polygonsList.setFileName(filename);
		return polygonsList;
	}
	
	
	public void loadPolygons(CustomImage customImage){
		polygonsList = customImage;
	}
	
	
	public Polygon testInsideShape(int x, int y){
		for(BetaPolygon polygon : polygonsList){
			if (polygon.contains(x, y)){
				//drag = true;
				currentModified = polygon;
				//System.out.println("I'm on a boat!");
				return polygon;
			}
		}
		return null;
	}
	
	

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//check if the cursor is within image area
		if (x > image.getWidth() || y > image.getHeight()) {
			//if not do nothing
			return;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (testInsideShape(x, y)!=null){
				if (polyInProgress == true){
					
					Graphics2D g = (Graphics2D)this.getGraphics();
					
					//if the left button than we will add a vertex to polygon
					
					polyInProgress=true;
					g.setColor(currentPolygon.getColor());
					if (currentPolygon.size() != 0) {
						Point lastVertex = currentPolygon.getPoint(currentPolygon.size() - 1);
						g.drawLine(lastVertex.getX(), lastVertex.getY(), x, y);
					
					g.fillOval(x-5,y-5,10,10);
					
					currentPolygon.addPoint(new Point(x,y));
					System.out.println(x + " " + y);
					}
				}else{	
						
					drag = true;
					
					System.out.println("Pressed button inside mousePressed");
					clicked[0] = x;
					clicked[1] = y;
					
				}
			}else{
				
				Graphics2D g = (Graphics2D)this.getGraphics();
				
				//if the left button than we will add a vertex to polygon
				if (e.getButton() == MouseEvent.BUTTON1) {
					polyInProgress=true;
					g.setColor(currentPolygon.getColor());
					if (currentPolygon.size() != 0) {
						Point lastVertex = currentPolygon.getPoint(currentPolygon.size() - 1);
						g.drawLine(lastVertex.getX(), lastVertex.getY(), x, y);
					}
					g.fillOval(x-5,y-5,10,10);
					
					currentPolygon.addPoint(new Point(x,y));
				}
			}
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (polyInProgress==true){
				addNewPolygon();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		
		
		
		if (x > image.getWidth() || y > image.getHeight()) {
			//if not do nothing
			return;
		}
		
		if (e.getButton() == MouseEvent.BUTTON1){
			
			if(drag == true){
				System.out.println("Pressed button 3 inside mouseReleased!");
				released[0] = x;
				released[1] = y;	
				
				System.out.println("Stuff: " + e.getXOnScreen() + " / " + e.getYOnScreen());
			
				currentModified.translate(released[0] - clicked[0], released[1] - clicked[1]);
			}	
			//Polygon area = testInsideShape(x,y);
			//area.translate(released[0] - clicked[0], released[1] - clicked[1]);
			//polygonsList.modify(area, currentModified);
			
			System.out.println("Pair of foxy ladies:" + (released[0] - clicked[0]) + " / " + (released[1] - clicked[1]));
			
			repaint();
			drag = false;
		}
	}
	
	
	public void mouseMoved(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		//testInsideShape(x, y);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
