package boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import algorithm.Action;
import algorithm.Plan;
import algorithm.Strips;
import data.BestFirstSearcher;
import gameObjects.Position2D;
import model.TextLevel2DLoader;
import solver.SokobanSolver;

public class Main {

	public static void main(String[] args) throws IOException  {
		TextLevel2DLoader loader=new TextLevel2DLoader();
		Plan<Position2D> plan=null;
		try{
			Strips<Position2D> strips=new Strips<>();
			plan=strips.plan(new SokobanSolver(loader.loadLevelFromStream(new FileInputStream(args[0])), new BestFirstSearcher<>()));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		String direction="";
		PrintWriter fus=new PrintWriter(new File(args[1]));
		for (Action<Position2D> act : plan.getActions()) {
			if(act.getName().startsWith("Move_MainCharacter_In_Direction"))
			{
				for (String string : act.getSub()) {
					fus.println(string);
				}
			}
			if(act.getName().equals("Move_Crate_To"))
			{
				//String name=act.getEffects().getComponents().get(0).getName();
				Position2D cratePos=act.getEffects().getComponents().get(0).getData();
				Position2D playerPos=act.getEffects().getComponents().get(1).getData();
				//differentiate
				if(playerPos.getX() == cratePos.getX()+1)
				{
					direction = "left";
				}
				else if(playerPos.getX() == cratePos.getX()-1)
				{
					direction = "right";
				}
				else if(playerPos.getY() == cratePos.getY()+1)
				{
					direction = "up";
				}
				else if(playerPos.getY() == cratePos.getY()-1)
				{
					direction = "down";
				}
				String line="Move " + direction;
				fus.write(line);
			}
			if(act.getName().equals("Move_MainCharacter_To"))
			{
				Position2D from=act.getPreconditions().getComponents().get(0).getData();
				Position2D to=act.getEffects().getComponents().get(0).getData();
				if(from.getX() == to.getX()+1)
				{
					direction = "left";
				}
				else if(from.getX() == to.getX()-1)
				{
					direction = "right";
				}
				else if(from.getY() == to.getY()+1)
				{
					direction = "up";
				}
				else if(from.getY() == to.getY()-1)
				{
					direction = "down";
				}
				String line="Move " + direction;
				fus.println(line);
			}
		}
		fus.flush();	

	}
}
