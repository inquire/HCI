package hci.utils;

import hci.utils.CustomImage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Metadata {

	public void saveMetadata(CustomImage imageMetadata, String file){
	
		try
		{	
			System.out.println(file);
			String filename = file.replaceAll(".jpg", ".pisi");
			// Write to disk with FileOutputStream
			FileOutputStream f_out = new 
			FileOutputStream(filename);

			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new
			ObjectOutputStream (f_out);

			// Write object out to disk
			obj_out.writeObject ( imageMetadata);
		
		}catch(IOException err){
				System.err.print(err);
		}
	} 
	
	// FileNotFound , IOException, ClassNotFound	
	public CustomImage loadMetadata(String file){	
		
		CustomImage ci = null;
		try
		{
			String filename = file.replaceAll(".jpg", ".pisi");
			// Read from disk using FileInputStream
			FileInputStream f_in = new 
			FileInputStream(filename);

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream (f_in);

			// Read an object
			Object obj = obj_in.readObject();

			if (obj instanceof CustomImage)
			{
				ci = (CustomImage) obj;	
			}
			
		}catch(FileNotFoundException err){
			System.err.print(err);
		}catch(IOException err){
			System.err.print(err);
		}catch(ClassNotFoundException err){
			System.err.print(err);
		}
		
		return ci;
		}
}
