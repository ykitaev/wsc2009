package planner;

public class DataType extends Proposition
{

	public DataType(String name) {
		super(name);
	}
	
	@Override
	public String toString()
	{
		return name;		
	}
	
}
