package de.mw.scene2d.swing.game.games.camping;

public class GameGoal
{
	public String name;
	private GoalCondition condition;
	private GoalAction action;
	private boolean isAccomplished = false;
	
	public GameGoal(String name, GoalCondition condition, GoalAction action)
	{
		this.name = name;
		this.condition = condition;
		this.action = action;
	}
	
	public boolean isAccomplished()
	{
		boolean oldValue = isAccomplished;
		isAccomplished = isAccomplished || condition.calcCondition();
		if (isAccomplished && !oldValue)
			action.action();
		return isAccomplished;
	}
	
	public static interface GoalCondition
	{
		public abstract boolean calcCondition(); 
	}

	public static interface GoalAction
	{
		public abstract void action(); 
	}

}
