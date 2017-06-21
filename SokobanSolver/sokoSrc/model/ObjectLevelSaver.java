package model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectLevelSaver {
	
	public boolean SaveLevel(OutputStream out,Level2D level) {
		try{
			ObjectOutputStream output = new ObjectOutputStream(out);
			output.writeObject(level);
			return true;
		}catch(IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
