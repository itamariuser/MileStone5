package controller;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

import model.Level;
import model.LevelLoader;
import model.ObjectLevelLoader;
import model.TextLevel2DLoader;
import model.XMLLevelLoader;

public class LoadLevelCommand implements Command {
	
	String fileName;
	Level levelToLoad;
	static HashMap<String, LevelLoader> levelLoaderFactory;
	String fileType;
	
	public LoadLevelCommand(String fileName, String fileType,Level l) throws Exception {
		this.levelToLoad=l;
		this.fileName = fileName;
		addLoader("obj",new ObjectLevelLoader());
		addLoader("txt",new TextLevel2DLoader());
		addLoader("xml",new XMLLevelLoader());
		this.fileType=fileType;
		if(fileType==null)
		{
			throw new Exception("fileContext");
		}
		
	}
	public LoadLevelCommand() {
		// TODO Auto-generated constructor stub
	}
	public Level getLevelToLoad() {
		return levelToLoad;
	}

	public void setLevelToLoad(Level levelToLoad) {
		this.levelToLoad = levelToLoad;
	}

	public void addLoader(String endsWith,LevelLoader l)
	{
		if(levelLoaderFactory==null)
		{
			levelLoaderFactory=new HashMap<String, LevelLoader>();
		}
		levelLoaderFactory.put(endsWith, l);
	}
	
	@Override
	public void execute() throws Exception{
		FileInputStream fileIn=null;
		Scanner s = new Scanner(fileName);
		s.useDelimiter(".");
		s.close(); 
		fileIn = new FileInputStream("levels/"+fileName);
		if(levelLoaderFactory.get(this.fileType)!=null)
		{
			levelToLoad= levelLoaderFactory.get(this.fileType).loadLevelFromStream(fileIn);
			fileIn.close();
		}
		else
		{
			fileIn.close();
			throw new Exception("fileNotFound");//file format not supported, prompt to add levelloader
		}
	}
}



	
	
	
	/*
	switch (fileEnds) {
	case "txt":
		levelToLoad = new TextLevelLoader().LoadLevelFromStream(fileIn);
		return true;
	case "xml":
		InputStream in=new FileInputStream(fileName);
		levelToLoad=new XMLLevelLoader().LoadLevelFromStream(in);
		return true;
	case "obj":
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			levelToLoad = new TextLevelLoader().LoadLevelFromStream(objIn);
			return true;
	default:
		return false;
	}
	}catch(FileNotFoundException e){
		e.printStackTrace();
	}catch(EOFException e){
		e.printStackTrace();
	}catch(IOException e)
	{
		e.printStackTrace();
	}*/

