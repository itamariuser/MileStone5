package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import model.Level2D;
import model.ObjectLevelSaver;

public class SaveCommand implements Command {
	String levelName;
	Level2D level;
	
	public SaveCommand(String levelName,Level2D level) {
		this.levelName=levelName;
		this.level=level;
	}
	@Override
	public void execute() throws Exception {
		
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(this.levelName);
			ObjectLevelSaver saver=new ObjectLevelSaver();
			if(!saver.SaveLevel(fileOut, level))
			{
				throw new Exception("File couldn't be saved.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
