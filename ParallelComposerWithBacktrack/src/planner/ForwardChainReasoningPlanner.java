package planner;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Planner class. Uses Forward Chain Reasoning with Depth Limited abilities. 
 * Properties: Optimal, if depth is incremented by 1 each time, resistant to loops.
 * The algorithm is able to find several solutions, provided that the previous solutions'
 * hashes are listed in the set
 * @author Yuri Kitaev
 */
public class ForwardChainReasoningPlanner
{
	public static boolean active = true;
	/**
	 * Planner function
	 * @param initialState The state that specifies the initial given conditions as a set of Propositions 
	 * @param goalState The goal state specified as a set of propositions
	 * @param actions The list of available actions, assuming that each action can only be executed once
	 * @param currentLevel The current level of depth. Should be equal to 1 when called from the outside
	 * @param maxDepth Maximum level. To get the optimal solution, start with 1 and increment by 1
	 * @param knownSolutionHashes A set of hashes of solutions which should be discarded as "known" by the algorithm. Use getSolutionHash() to compute hashes 
	 * @param partialSolutionHash The partial solution hash, used by the algorithm. Must be 0 when called from the outside.
	 * @return The next available solutions, which fits the criteria listed above.
	 */
	public ArrayList<ParallelActionPack> planAdvanced(final State initialState
			, State goalState
			, ArrayList<? extends Action> availableActions
			, int currentLevel
			, int maxDepth
			, HashSet<Long> knownSolutionHashes
			, long partialSolutionHash)
	{
		if (!active)
			return null;
		
		// Base case - depth limiting part
		if (currentLevel > maxDepth+1)
			return null;
		
		// Include the semantically-equivalent concepts in the starting state
		//initialState.expand();

		// Base case - check if we have reached the goal
		if (initialState.entails(goalState))
		{
			// Check if the solution found should be discarded as "known" or returned
			if (knownSolutionHashes.contains(partialSolutionHash))
			{
				OutputManager.writeToFile(partialSolutionHash + " is already in " + knownSolutionHashes + ", keep searching.");
				return null;
			}
			else
			{
				OutputManager.writeToFile(partialSolutionHash + " is not in " + knownSolutionHashes + ", returning solution.");
				return new ArrayList<ParallelActionPack>(); // Empty
			}
		}
		
		// "result" will store the entire solution down the recursive road.
		ArrayList<ParallelActionPack> result = new ArrayList<ParallelActionPack>();
		// The set of applicable action at this level. Redundant, useless, and unavailable actions are discarded
		final ArrayList<Action> applicable = new ArrayList<Action>();
		
		// Get the list of applicable actions
		for (int i = 0; i < availableActions.size(); ++i)
		{
			if (initialState.hasPreconditionForExecution(availableActions.get(i))
					&& !initialState.alreadyEntailsPostconditions(availableActions.get(i)))
				applicable.add(availableActions.get(i));
		}
		
		OutputManager.outputLevelStatus(currentLevel, initialState, goalState, applicable);
		
		// If no actions are possible, return failure.
		if (0 == applicable.size())
			return null;
		
		// Attempt recursive paths with all possible applicable actions, one at a time
		
		State s = initialState.copy();
		
		// Apply action onto the state to get the state, which is the result of executing this action
		for (Action action : applicable)
		{
			for (Proposition oneOutput : action.getPostConditions())
			{
				s.addWithExpansion(oneOutput);
			}
		}


		// Determine which actions are available for the next level. 
		// This set is equal to the all actions (not just applicable) minus the one 
		// currently considered - because the assumption is that you cannot apply one action twice.
		ArrayList<Action> availableForNextLevel = new ArrayList<Action>();
		for (Action action : availableActions)
			if (!applicable.contains(action))
				availableForNextLevel.add(action);
		
		 // compute the partial hash
		long hash = partialSolutionHash;
		for (int k = 0; k < applicable.size(); ++k)
		{
			// Keep the position the same for hashing to tell the algorithm
			// that switching actions around which are done in parallel
			// does not give us a new solution and more search is required
			Action action = applicable.get(k);
			hash += action.getDomainLevelHash();
		}
		
		// Recursive call. Gets the remaining part of the solution
		ArrayList<ParallelActionPack> tail = planAdvanced(s
							, goalState
							, availableForNextLevel
							, currentLevel + 1 // increment the depth for depth-limiting purposes
							, maxDepth
							, knownSolutionHashes 
							, hash);
		
		// Let the user know if backtracking is possible, and if so - what is the tail contents
		//OutputManager.backtrack(tail);
		
		// Check if the solution is valid, and if yes - return it to the caller
		if (null != tail)
		{
			// Since the solution exists with the action in progress, it should be added as the head of the final solution
			ParallelActionPack pack = new ParallelActionPack(applicable.size());
			for (Action a : applicable)
			{
				pack.getActions().add(a);
			}
			result.add(pack);
			
			// .. while the rest of the solution is the tail
			for (int j = 0; j < tail.size(); ++j)
				result.add(tail.get(j));
			
			return result;
		}
		
		// If nothing has been found, return failure.
		return null;
	}
	
	/**
	 * Computes the hash of a particular solution 
	 * @param solution The ArrayList of actions
	 * @return Customly-generated hash-code
	 */
	public static long getSolutionHash(ArrayList<? extends Action> solution, int degreeOfParallelism)
	{
		long hash = 0;
		for (int i = 0; i < solution.size(); ++i)
			hash += getActionHash(solution.get(i), i/degreeOfParallelism+1);
		
		return hash;
	}
	
	public static long getSolutionHash(ArrayList<? extends ParallelActionPack> s)
	{
		long hash = 0;
		for (ParallelActionPack p : s)
			hash += p.hashCode();
		return hash;
	}

	/**
	 * Private method. Computes hash-code of one element, taking into account its position in the solution.
	 * @param a Element
	 * @param pos Position. 0 based!
	 * @return The custom hash-code
	 */
	private static long getActionHash(Action a, int pos)
	{
		if (null == a)
			return 0;
		
		return (a.hashCode()) % 4194304;
	}
	
	/**
	 * Computes lean solution via backtrack
	 * @param initialState The initial state (expanded)
	 * @param goalState The goal state
	 * @param fatSolution The non-filtered solution
	 * @return New Lean Solution
	 */
	public static ArrayList<ParallelActionPack> computeLeanSolution(State initialState, State goalState, ArrayList<ParallelActionPack> fatSolution)
	{
		OutputManager.writeToFile("Computing Lean Solution...");
		State desiredState= goalState.copy();

		// First, remove from the desired goal state all items which were already in the initial state
		for (Proposition p : initialState.getState())
			if(desiredState.getState().contains(p))
				desiredState.getState().remove(p);

		for (int i = fatSolution.size()-1; i >= 0; i--)
		{
			ParallelActionPack pack = fatSolution.get(i);
			ArrayList<Action> actions = new ArrayList<Action>();
			for (Action a : pack.getActions())
			{
				if (isActionUseful(a, desiredState))
				{
					actions.add(a);
					markActionEffectsAsAchieved(a, desiredState);
				}
			}

			pack.setActions(actions);
			
			desiredState = State.union(computeMinumumStateToExecutePack(pack), desiredState);		
		}
		return fatSolution;
	}
	
	/**
	 * Verfies if the solution leads to the path from initial state to final state
	 * @param is Initial state (expanded)
	 * @param fs Final state
	 * @param sln Solution
	 * @return True if solution is good, false otherwise
	 * @note Auxiliary methods, just to demostrate the correctness. Not actually used in computation
	 */
	public static boolean verifySolution(State is, State fs, ArrayList<ParallelActionPack> sln)
	{
		State s = is.copy();
		for (int i = 0; i < sln.size(); ++i)
		{
			ParallelActionPack pack = sln.get(i);
			for (Action a : pack.getActions())
				s.applyActionWithExpansion(a);
		}
		if (s.entails(fs))
			return true;
		else
			return false;
	}
	
	/**
	 * Computes pre-conditions to execute a group of actions in parallel
	 * @param pack
	 * @return
	 */
	private static State computeMinumumStateToExecutePack (ParallelActionPack pack)
	{
		State s = new State();
		for (Action a : pack.getActions())
			for (Proposition p : a.getPreConditions())
				s.add(p);
		return s;
	}
	
	/**
	 * Tells if any of the effects of the action are in the specified set
	 * @param a Action
	 * @param s Set
	 * @return
	 */
	private static boolean isActionUseful(Action a, State s)
	{
		State blank = new State();
		blank.applyActionWithExpansion(a);
		for (Proposition p : blank.getState())
			if (s.getState().contains(p))
				return true;
		
		return false;
	}
	
	/**
	 * Removes the effects of the specified action from the specified set
	 * @param a Action 
	 * @param s Set
	 */
	private static void markActionEffectsAsAchieved(Action a, State s)
	{
		State blank = new State ();
		blank.applyActionWithExpansion(a);
		for (Proposition p : blank.getState())
			if (s.getState().contains(p))
				s.getState().remove(p);
	}
}