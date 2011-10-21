package hci.utils;

//import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;

import hci.utils.BetaPolygon;

/**
 * @Author Daniel Stanoescu
 * 
 * Class containing the set of polygons that are attached to an image.
 */

public class CustomImage implements Iterable<BetaPolygon>, java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<BetaPolygon> shapes = null;
	private String fileName;
	
	/**
	 * Creates an array that will hold the polygons assigned to the image
	 */
	
	public CustomImage(){
		shapes = new ArrayList<BetaPolygon>();
	}  
	
	/**
	 * Adds a polygon to the array of polygons
	 * @param polygon to add
	 */
	public void addPolygon(BetaPolygon polygon){
		shapes.add(polygon);
	}
	
	/**
	 * Returns a polygon that resides at the provided position in the array
	 * @param position
	 * @return a polygon
	 */
	
	public BetaPolygon getPolygon(int position){
		return shapes.get(position);
	}
	
	/**
	 * Removes a polygon that resides at the provided position in the array
	 * @param position
	 */
	
	public void removePolygon(int position){
		shapes.remove(position);
	}
		
	/**
	 * Returns the size of the array of polygons
	 * @return
	 */
	
	public int size(){
		return shapes.size();
	}
	
	/**
	 * Embeds the object with the filename of the loaded image that is processed.
	 * @param fileName
	 */
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	/**
	 * Returns the filename of the image associated with the object
	 * @return image filename
	 */
	
	public String getFileName(){
		return fileName;
	}
	
	/**
	 * Clears all the polygons in the polygon array.
	 */
	
	public void clear(){
		shapes.clear();
	}
	
	/**
	 * Removes a given object from the array of polygons.
	 * @param object
	 */
	
	public void removePolygon(Object object){
		shapes.remove(object);
	}
	
	/**
	 * Modifies a given point that resides in a specified polygon
	 * @param polygon the polygon that contains the point that needs modifying
	 * @param position the position of the point in the provided polygon
	 * @param x the x coordinate that will replace the current x coordinate of the point
	 * @param y the y coordinate that will replace the current y coordinate of the point
	 */

	public void modifyPolygonPoint(BetaPolygon polygon, int position, int x, int y){
		for(BetaPolygon currentPolygon : shapes){
			if(currentPolygon.equals(polygon)){
				currentPolygon.modifyPoint(position, x, y);
			}
		}
	}
	
	/**
	 * Provides an iterator through the polygons that the object holds
	 */
	
	@Override
	public Iterator<BetaPolygon> iterator(){
		return shapes.iterator();
	}
		
}
