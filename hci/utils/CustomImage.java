package hci.utils;

import java.util.ArrayList;
import java.util.Iterator;

import hci.utils.Polygon;

public class CustomImage implements Iterable<Polygon>, java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Polygon> shapes = null;
	private String fileName;
	
	public CustomImage(){
		shapes = new ArrayList<Polygon>();
	}
	
	public void addPolygon(Polygon polygon){
		shapes.add(polygon);
	}
	
	public Polygon getPolygon(int position){
		return shapes.get(position);
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
	
	@Override
	public Iterator<Polygon> iterator(){
		return shapes.iterator();
	}
		
}
