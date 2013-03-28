package auxparsers;


import java.util.ArrayList;

import planner.DataType;

public class ChallengeParser 
{
	ArrayList<DataType> inputs = new ArrayList<DataType>();
	ArrayList<DataType> outputs = new ArrayList<DataType>();
	boolean parsed = false;
	
	public void parse(String fileName)
	{
		// Stub method for the sample data.
		DataType a = new DataType("A");
		DataType b = new DataType("B");
		DataType c = new DataType("C");
		DataType d = new DataType("D");
		
		inputs.add(a);
		inputs.add(b);
		inputs.add(c);
		
		outputs.add(d);
		
		parsed = true;
	}
	
	public ArrayList<DataType> getInputs() throws Exception
	{
		if (!parsed)
			throw new Exception("The challenge have not been parsed!");
		
		return inputs;
	}
	
	public ArrayList<DataType> getOuputs() throws Exception
	{
		if (!parsed)
			throw new Exception("The challenge have not been parsed!");
		
		return outputs;
	}
	
}
