package planner;

import java.util.ArrayList;

public class ParallelActionPack 
{
	private ArrayList<Action> actions = new ArrayList<Action>();
	
	public ArrayList<Action> getActions()
	{
		return actions;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 0;
		for (Action a : actions)
			hash += a.hashCode();
		
		return hash;
	}
	
	@Override
	public String toString()
	{
		return actions.toString();
	}
}
