package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import model.Level2D;

public class CLI {
	Command command;
	String commandLine;
	ArrayList<String> args;
	Level2D level;
	public CLI() {
		level=new Level2D();
		args=new ArrayList<String>();
	}
	public void receiveInput()
	{
			BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
			boolean end=false;
			Scanner s=null;
			System.out.println("Welcome to Sokoban demo, by Itamar Sheffer and Matan Bachar.\n");
			System.out.println("Load a level using 'Load <Level Name> <Level Format>' , e.g: load level1.txt txt");
			System.out.println("Display a level using 'Display <display method>', e.g: display text");
			System.out.println("Move: 'Move <up,down,left,right>");
			System.out.println("Save a level using 'Save <destination name>', e.g: save myFile ");
			System.out.println("Exit using Exit");
			while(!end)
			{
				try
				{
					args.clear();
					commandLine=reader.readLine();
					s = new Scanner(commandLine);
					s.useDelimiter(" ");
					while(s.hasNext())
					{
						args.add(s.next());
					}
					switch (args.get(0).toLowerCase()) 
					{
					case "load":
						try{
							LoadLevelCommand com = new LoadLevelCommand(args.get(1),args.get(2),level);
							com.execute();
							this.level= (Level2D) com.getLevelToLoad();}catch(FileNotFoundException e)
						{
								System.out.println("File not found.");
						}
						catch(IndexOutOfBoundsException e)
						{
							System.out.println("Load a level using 'Load <Level Name> <Level Format>' , e.g: load level1.txt txt");
						}
						catch(Exception e){
							switch (e.getMessage()) {
							case "fileContext":
								System.out.println("Load a level using 'Load <Level Name> <Level Format>' , e.g: load level1.txt txt");
								break;
							default:
								e.printStackTrace();
								break;
							}
						}
					
						break;
					case "display":
						new DisplayCommand(this.level,args.get(1)).execute();
						break;
					case "move":
						MoveCommand2D command=new MoveCommand2D(level, args.get(1));
						try{
							command.execute();
							new DisplayCommand(this.level,"text").execute();
							if(command.isWinCondition())
							{
								this.displayWinningMessage();
								new ExitCommand().execute();
								end=true;
							}
						}catch(Exception e)
						{
							switch(e.getMessage())
							{
							case"InvalidDirection":
								System.out.println("Invalid direction, directions available: ");
								
								for (String dir : command.getDirectionsAvailable()) {
									System.out.print(dir+" ");
								}
							}
						}
						break;
					case "save":
						new SaveCommand(args.get(1), this.level).execute();
						break;
					case "exit":
						new ExitCommand().execute();
						end=true;
						break;
					default:
						System.out.println("Please pick from the following: Load, Display, Move, Save, Exit");
						break;
						
					}
					
					
				}
				catch (Exception e) {
					switch (e.getMessage()) {
					case "CantMove":
						System.out.println("Error: Can't Move in this direction.");
						break;
					case "fileNotFound":
						System.out.println("Error: No such file in directory");
						
					default:
						break;
					}
			}
			s.close();
		} 
	}
				
	private void displayWinningMessage()
	{
		System.out.println("You won!");
	}
}
