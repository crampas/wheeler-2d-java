package de.mw.scene2d.swing.game.games.camping;

import de.mw.scene2d.swing.game.games.Game;

public abstract class GameGoal<T extends Game>
{
	public boolean started = false;
	public String name;
	private boolean isAccomplished = false;
	
	protected final T game;
	
	public GameGoal(T game, String name)
	{
		this.game = game;
		this.name = name;
	}
	
	public GameGoal<T> update()
	{
		boolean oldValue = isAccomplished;
		isAccomplished = isAccomplished || checkCondition();
		if (isAccomplished && !oldValue)
			return onFinished();
		return this;
	}
	

	public abstract boolean checkCondition();

	public abstract void onStart();
	public abstract GameGoal<T> onFinished();
}
