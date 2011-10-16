package hci.utils;

import java.util.ArrayList;
import java.awt.Color;
import java.util.Iterator;
import java.awt.Polygon;


import hci.utils.Point;

/**
 * simple class for handling polygons
 * @author Daniel
 *
 */

public class BetaPolygon implements Iterable<Point>, java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private BetaPolygon polygons = null;
	private ArrayList<Point> polygons= null;
	private Color color;
	private String tag;
	
	
	public BetaPolygon(){
		//polygons = new BetaPolygon();
		polygons = new ArrayList<Point>();
	}
	
	public void addPoint(Point point){
		//polygons.addPoint(point.getX(), point.getY());
		polygons.add(point);
	}
	
	public Point getPoint(int position){
		//Point point = new Point(polygons.xpoints[position], polygons.ypoints[position]);
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

	public void translate(Polygon area){
		polygons.clear();
		int []x = area.xpoints;
		int []y = area.ypoints;
			for(int i = 0; i<area.npoints; i++){
				Point point = new Point(x[i], y[i]);
				polygons.add(point);
			}
	
		
	}
	
	@Override
	public Iterator<Point> iterator() {
		return polygons.iterator();
	}
}
