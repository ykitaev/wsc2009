package planner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import conceptparser.ConceptInstanceMap;

/**
 * Denotes one state; consists of a set of propositions
 * @author Yuri Kitaev
 *
 */
public class State
{
	private HashSet<Proposition> state;
	
	public State()
	{
		state = new HashSet<Proposition>();
	}

	public HashSet<Proposition> getState() {
		return state;
	}

	public void setState(HashSet<Proposition> state) {
		this.state = state;
	}
	
	public void add (Proposition element)
	{
		state.add(element);
	}
	
	public boolean entails(State s)
	{
		Iterator<Proposition> itr = s.getState().iterator();
        while(itr.hasNext()){
            if (!state.contains(itr.next()))
            	return false;
        }
        return true;
	}
	
	public boolean hasPreconditionForExecution(Action s)
	{
		for (int i = 0; i < s.getPreConditions().size(); ++i)
		{
			if (!state.contains(s.getPreConditions().get(i)))
				return false;
		}
		return true;
	}
	public boolean alreadyEntailsPostconditions(Action s)
	{
		for (int i = 0; i < s.getPostConditions().size(); ++i)
		{
			if (!state.contains(s.getPostConditions().get(i)))
				return false;
		}
		return true;
	}
	
	public void applyActionWithExpansion(Action a)
	{
		for (Proposition p : a.getPostConditions())
			addWithExpansion(p);
	}
	
	/**
	 * Clones the state
	 * @return A copy of the object
	 */
	public State copy()
	{
		State clone = new State();
		Iterator<Proposition> itr = state.iterator();
        while(itr.hasNext()){
            clone.add(itr.next());
        }
        return clone;
	}
	
	public void addWithExpansion(Proposition p)
	{
		ArrayList<String> equivalentInstanceNames = ConceptInstanceMap.findEquivalentInstancesCached(p.getName());
        for (int i = 0; i < equivalentInstanceNames.size(); ++i)
        	state.add(new DataType(equivalentInstanceNames.get(i)));	
        
        state.add(p);
	}
	
	/**
	 * Expands the state by making it include the semantically-equivalent instances
	 */
	/*
	private void expand()
	{
		Proposition[] stateContents = new Proposition[state.size()];
		stateContents = state.toArray(stateContents);
        for (int j = 0; j < stateContents.length; ++j)
        {
        	ArrayList<String> equivalentInstanceNames = ConceptInstanceMap.findEquivalentInstancesCached(stateContents[j].getName());
            for (int i = 0; i < equivalentInstanceNames.size(); ++i)
            	getState().add(new DataType(equivalentInstanceNames.get(i)));
        }	
	}
	*/
	
	@Override
	public String toString()
	{
		return getState().toString();
	}
}
