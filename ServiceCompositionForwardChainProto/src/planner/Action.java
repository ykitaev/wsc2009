package planner;
import java.util.ArrayList;


public abstract class Action
{
	protected String name;
	protected ArrayList<Proposition> preConditions;
	protected ArrayList<Proposition> postConditions;
	
	public Action(String name) {
		super();
		
		this.name = name;
		preConditions = new ArrayList<Proposition>();
		postConditions = new ArrayList<Proposition>();
	}	
	
	public String getName() {
		return name;
	}	

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Proposition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(ArrayList<Proposition> preConditions) {
		this.preConditions = preConditions;
	}

	public ArrayList<Proposition> getPostConditions() {
		return postConditions;
	}

	public void setPostConditions(ArrayList<Proposition> postConditions) {
		this.postConditions = postConditions;
	}

	@Override
	public String toString()
	{
		return name;
	}
	
	public long getDomainLevelHash()
	{
		return (hashCode()) % 4194304;
	}
	
}
