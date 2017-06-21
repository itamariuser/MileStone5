package controller;

import java.util.HashMap;

import model.Level2D;
import model.LevelDisplayer;
import model.TextDisplayer;

public class DisplayCommand implements Command {
	Level2D level;
	LevelDisplayer displayer;
	String displayType;
	static HashMap<String, LevelDisplayer> factoryDisplayer;
	
	public DisplayCommand(Level2D level,String displayType) {
		this.displayType="text";
		this.level=level;
		if(factoryDisplayer==null)
		{
			factoryDisplayer=new HashMap<String, LevelDisplayer>();
		}
		TextDisplayer s=new TextDisplayer();
		factoryDisplayer.put("text", s);
	}
	@Override
	public void execute() throws Exception {
		if(factoryDisplayer.get(displayType)!=null)
		{
			factoryDisplayer.get(displayType).displayLevel(this.level);
		}
		else
		{
			throw new Exception("levelDisplay");//no displayer for displayType
		}
		
	}

}
