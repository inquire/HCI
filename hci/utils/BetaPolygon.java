package hci.utils;

import java.util.ArrayList;
import java.awt.Color;
import hci.utils.Point;

/**
 * simple class for handling polygons
 * @author Daniel
 *
 */

public class Polygon implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Point> polygons = null;
	private Color color;
	private String tag;
	
	public Polygon(){
		polygons = new ArrayList<Point>();
	}
	
	public void addPoint(Point point){
		polygons.add(point);
	}
	
	public Point getPoint(int position){
		return polygons.get(position);
	}
	
	public int size(){
		return polygons.size();
	}
	
	public void setColor(Color color){
		this.color = color;	
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public String getTag(){
		return tag;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
}
