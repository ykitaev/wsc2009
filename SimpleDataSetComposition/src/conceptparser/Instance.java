package conceptparser;

public class Instance 
{
	private String name;
	private Concept type;
	
	public Instance(String name, Concept type) {
		super();
		this.name = name;
		this.type = type;
	}

		
	public Concept getType() {
		return type;
	}

	public void setType(Concept type) {
		this.type = type;
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
		if (!(t instanceof Instance))
			return false;
		
		Instance s = (Instance) t;
		return name.equals(s.getName());
	}
	
	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}	
}
