package planner;

public abstract class Proposition 
{
	protected String name;
	
	public Proposition(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object t)
	{
		if (!(t instanceof Proposition))
			return false;
		
		Proposition s = (Proposition) t;
		return name.equals(s.getName());
	}
	
	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}	
}
