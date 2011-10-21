package hci.utils;

//import java.util.ArrayList;
import java.awt.Color;
import java.util.Iterator;
import java.awt.Polygon;


import hci.utils.Point;

/**
 * simple class for handling this
 * @author Daniel
 *
 */

public class BetaPolygon extends Polygon implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Color color;
	private String tag;
	public Boolean isSelected = false;
	
	public BetaPolygon(){
	}
	
	public void addPoint(Point point){
		this.addPoint(point.getX(), point.getY());
		//this.add(point);
	}
	
	public Point getPoint(int position){
		Point point = new Point(this.xpoints[position], this.ypoints[position]);
		//return this.get(position);
		return point;
	}
	
	public int getX(int x){
		return xpoints[x];
	}
	
	public int getY(int y){
		return ypoints[y];
	}
	
	
	
	public int size(){
		return this.npoints;
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
	
	public String toString(){
		return tag;
	}

	public void modifyPoint(int position, int x, int y){
		xpoints[position] = x;
		ypoints[position] = y;
	}

}
