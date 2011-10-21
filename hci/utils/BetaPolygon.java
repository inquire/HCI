package hci.utils;

//import java.util.ArrayList;
import java.awt.Color;
//import java.util.Iterator;
import java.awt.Polygon;


import hci.utils.Point;

/**
 * Custom Class that extends the Java polygon and defines a polygon 
 * @author Daniel
 *
 */

public class BetaPolygon extends Polygon implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private Color color;
	private String tag;
	public Boolean isSelected = false;
	
	public BetaPolygon(){}
	
	
	/**
	 * Method for adding a point to the polygon
	 * @param point : coordinates of the point
	 */
	
	
	public void addPoint(Point point){
		this.addPoint(point.getX(), point.getY());
	}
	
	/**
	 * Gets the point that is located at a particular position in the polygon
	 * @param position Position of the point
	 * @return The point located at position.
	 */
	
	
	public Point getPoint(int position){
		Point point = new Point(this.xpoints[position], this.ypoints[position]);
		return point;
	}
	
	/**
	 * Returns the x coordinate of a given point
	 * @param x coordinate
	 * @return the point with the corresponding x coordinate
	 */
	
	public int getX(int x){
		return xpoints[x];
	}
	
	/**
	 * Returns the y coordinate of a given point
	 * @param y coordinate
	 * @return the point with the corresponding x coordinate 
	 */
	
	public int getY(int y){
		return ypoints[y];
	}
	
	/**
	 * Returns the numbers of points in a polygon
	 * @return the number of points
	 */
	public int size(){
		return this.npoints;
	}
	
	
	/**
	 * Sets the color of a polygon
	 * @param color the color to set
	 */
	
	public void setColor(Color color){
		this.color = color;	
	}
	
	/**
	 * Gets the color of the polygon
	 * @return polygon color
	 */
	
	public Color getColor(){
		return this.color;
	}
	
	/**
	 * Getter method for the polygon tag
	 * @return polygon tag
	 */
	
	public String getTag(){
		return tag;
	}
	
	/**
	 * Setter method for a polygon tag
	 * @param tag sets a tag
	 */
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	/**
	 * ToString method for returning the polygon tag
	 */
	public String toString(){
		return tag;
	}

	/**
	 * Method for modifying the position of a certain point
	 * @param position of the point in the polygon
	 * @param x coordinate to modify
	 * @param y coordinate to modify
	 */
	
	public void modifyPoint(int position, int x, int y){
		xpoints[position] = x;
		ypoints[position] = y;
	}

}
