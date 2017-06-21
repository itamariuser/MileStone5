package gameObjects;

@SuppressWarnings("serial")
public class GoalPoint extends Winnable{
	private boolean winCondition;
	
	public GoalPoint() {
		super();
	}
	
	public GoalPoint(Position position) {
		super(position);
		GoalPoint.texture='o';
		this.solid=false;
		priority=1;
	}

	public boolean isWinCondition() {
		return winCondition;
	}

	public void setWinCondition(boolean winCondition) {
		this.winCondition = winCondition;
	}
	
	public void checkIfMovableOnTop(MovableObject mov)
	{
		if(mov.getCurrentLocation()==this.getCurrentLocation())
		{
			this.setWinCondition(true);
		}
	}

	@Override
	public boolean checkWin() {
		
		return false;
	}
	
}
