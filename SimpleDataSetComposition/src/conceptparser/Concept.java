package conceptparser;

public class Concept 
{
	String name = "";
	Concept parent = null;
	
	public Concept(String name, Concept parent) {
		super();
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Concept getParent() {
		return parent;
	}

	public void setParent(Concept parent) {
		this.parent = parent;
	}	
	
	@Override
	public boolean equals(Object t)
	{
		//if (!(t instanceof Concept))
			//return false;
		
		Concept s = (Concept) t;
		return name.equals(s.getName());
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}	
}
