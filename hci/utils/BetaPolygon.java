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
	//private BetaPolygon this;
	//private ArrayList<Point> this= null;
	private Color color;
	private String tag;
	
	
	public BetaPolygon(){
		//this = new BetaPolygon();
		//this = new ArrayList<Point>();
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

	/**
	
	public void translate(Polygon area){
		this.clear();
		int []x = area.xpoints;
		int []y = area.ypoints;
			for(int i = 0; i<area.npoints; i++){
				Point point = new Point(x[i], y[i]);
				this.add(point);
			}
	}
	
	**/
	
	
	
	
	//@Override
	//public Iterator<Point> iterator() {
	//	return this.iterator();
	//}
}
