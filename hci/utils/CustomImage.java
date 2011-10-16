package hci.utils;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;

import hci.utils.BetaPolygon;

public class CustomImage implements Iterable<BetaPolygon>, java.io.Serializable{

	/**
	 * @Author Daniel Stanoescu
	 * 
	 * Class containing a set of polygons which are comprised of multiple points.
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<BetaPolygon> shapes = null;
	private String fileName;
	
	public CustomImage(){
		shapes = new ArrayList<BetaPolygon>();
	}
	
	public void addPolygon(BetaPolygon polygon){
		shapes.add(polygon);
	}
	
	public BetaPolygon getPolygon(int position){
		return shapes.get(position);
	}
	
	public void removePolygon(int position){
		shapes.remove(position);
	}
		
	public int size(){
		return shapes.size();
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	// Parial Implementation.
	public void clear(){
		shapes.clear();
	}
	
	public void modify(Polygon area, BetaPolygon polygon){
		int location = shapes.indexOf(polygon);
		shapes.get(location).translate(area);
	}
	
	
	
	@Override
	public Iterator<BetaPolygon> iterator(){
		return shapes.iterator();
	}
		
}
