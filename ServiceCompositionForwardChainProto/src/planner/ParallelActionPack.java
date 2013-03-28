package planner;

import java.util.ArrayList;

public class ParallelActionPack 
{
	private ArrayList<Action> actions;
	
	public ParallelActionPack()
	{
		this(5); 
	}
	
	public ParallelActionPack(int size)
	{
		actions = new ArrayList<Action>(size);
	}
	
	public ArrayList<Action> getActions()
	{
		return actions;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 0;
		for (Action a : actions)
			hash += a.getDomainLevelHash();
		
		return hash;
	}
	
	@Override
	public String toString()
	{
		return actions.toString();
	}
}
