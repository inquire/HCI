package hci;

import javax.imageio.ImageIO;
//import javax.swing.JOptionPane;
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
 * @author Daniel
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
	BetaPolygon currentModified2 = null;
	
	/** 
	 * Cursor information regarding different events
	 * 
	 */
	
	int[] clicked = new int[2];
	int[] released = new int[2];
	int[] margin = new int[2];	
	int[] moved = new int[2];
	int movedPointCoordinate;
	
	// ============================ Random Colors ===========================
	
	Color[] polyColor = new Color[]{Color.pink, Color.green, Color.blue, Color.cyan, Color.magenta, Color.orange, Color.yellow};
	Random rand = new Random();

	private boolean polyInProgress;
	private boolean onTheEdge;
	Point modified;
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
		
		
		System.out.print(imageName);
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
		if(polygon.isSelected){
			g.setColor(Color.RED);
		}else{
			g.setColor(polygon.getColor());
		}
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
			if(polygon.isSelected){
				g.setColor(Color.RED);
			}else{
				g.setColor(polygon.getColor());

			}
			g.drawLine(firstVertex.getX(), firstVertex.getY(), lastVertex.getX(), lastVertex.getY());
		}
	}
	
	/**
	 * Moves current polygon to the list of polygons and makes space for a new one
	 * It also ensures that a polygon is not smaller than 3 points.
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
	
	/**
	 * Sets the filename of the image in the object
	 * @return
	 */
	public CustomImage getCustomImage(){
		polygonsList.setFileName(filename);
		return polygonsList;
	}
	
	/**
	 * Loads all the polygons of the image.
	 * @param customImage
	 */
	public void loadPolygons(CustomImage customImage){
		polygonsList = customImage;
	}
	
	/**
	 * Tests if the mouse cursor is inside a polygon and colors the polygon if inside it
	 * @param x coordinate of the mouse
	 * @param y coordinate of the mouse
	 * @return the polygon you are inside
	 */
	
	public Polygon testInsideShape(int x, int y){
		
		for(BetaPolygon polygon : polygonsList){
			polygon.isSelected = false;
			if (polygon.contains(x, y)){
				//drag = true;
				currentModified = polygon;
				polygon.isSelected = true;
				//System.out.println("I'm on a boat!");
			}
		}
		
		
		for(BetaPolygon polygon : polygonsList){
			polygon.isSelected = false;
			if (polygon.contains(x, y)){
				//drag = true;
				currentModified = polygon;
				polygon.isSelected = true;
				return currentModified;
			}
		}
		return null;
	}
	
	/**
	 * Tests if you are in one of the points on the boundary of the polygon
	 * @param x coordinate of the mouse
	 * @param y coordinate of the mouse
	 * @return null
	 */
	
	public Point testContourShape(int x, int y){
		for (BetaPolygon polygon : polygonsList){
			polygon.invalidate();
			for(int i = 0; i < polygon.size(); i++ ){
			//	System.out.println("Pair of darn ladies:" + (x) + " / " + (y) + " !!! ");
				if(((polygon.getX(i)+5 >= x ) &&(polygon.getX(i)-5 <= x)) &&
				    ((polygon.getY(i)+5 >= y ) && (polygon.getY(i)-5 <= y))){
					currentModified2 = polygon;
					movedPointCoordinate = i;
					onTheEdge = true;
				}
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
		
		/**
		 * Check if you are inside a shape and if you are memorize the point when you pressed mouse button 1
		 * Else it starts drawing a polygon where the mouse button 1 was pressed
		 */
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			
			onTheEdge = false;
			testContourShape(x, y);
			
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
		
		/**
		 * Get the location where the mouse button nr 3 has been pushed
		 */
		
		if(e.getButton() == MouseEvent.BUTTON3){
			onTheEdge = false;
			testContourShape(x, y);
			if(onTheEdge == true){
				onTheEdge = true;
				margin[0] = x;
				margin[1] = y;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	
		if (x > image.getWidth() || y > image.getHeight()) {
			return;
		}
		
		/**
		 * Set the final location of a moved polygon.
		 */
		
		
		if (e.getButton() == MouseEvent.BUTTON1){
			
			if(drag == true){
				System.out.println("Pressed button 3 inside mouseReleased!");
				released[0] = x;
				released[1] = y;	
				
				System.out.println("Stuff: " + e.getXOnScreen() + " / " + e.getYOnScreen());
				for(BetaPolygon polygon : polygonsList){
					if(polygon.equals(currentModified)){
						polygon.translate(released[0] - clicked[0], released[1] - clicked[1]);
					}
				}
				repaint();
				testInsideShape(x, y);
				drag = false;
			}	
		}
		
		/**
		 * Set the final location of a moved point
		 */
		
		if(e.getButton() == MouseEvent.BUTTON3){
			if(onTheEdge == true){
				currentModified.modifyPoint(movedPointCoordinate, x, y);
				testInsideShape(x, y);
				//printMeStuff(currentModified);
				//repaint();
					
				onTheEdge = true;
				drag = true;
				testInsideShape(x, y);
			}
		}
	}
	
	/**
	 * Continuous test if inside a polygon & test if are on a point that makes it up.
	 */
	
	public void mouseMoved(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		testContourShape(x, y);
		testInsideShape(x, y);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
	
		moved[0] = x;
		moved[1] = y;
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		if (x > image.getWidth() || y > image.getHeight()) {
			//if not do nothing
			return;
		}
		
		/**
		 * Redraw the point movement.
		 */
		
		if(onTheEdge == true){
			currentModified.modifyPoint(movedPointCoordinate,x, y);
			repaint();
		}
		

	}
}
