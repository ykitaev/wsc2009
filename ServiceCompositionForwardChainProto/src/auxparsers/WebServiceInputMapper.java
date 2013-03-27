package auxparsers;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import planner.DataType;
import planner.OutputManager;
import planner.WebService;


public class WebServiceInputMapper 
{
	public static ArrayList<WebService> getAll()
	{
		ArrayList<WebService> wResults = new ArrayList<WebService> (1000);
		
		// Read file
	    try 
	    {
	    	BufferedReader br = new BufferedReader(new FileReader("F:\\services.txt"));
	        String line = br.readLine();

	        boolean addingInputs = false;
	        boolean addingOutputs = false;
	        WebService wLastWs = null;
	        while (line != null) 
	        {
	            if (" Inputs".equalsIgnoreCase(line))
	            {
	            	addingInputs = true;
	            	addingOutputs = false;
	            }
	            else if (" Outputs".equalsIgnoreCase(line))
	            {
	            	addingOutputs = true;
	            	addingInputs = false;
	            }
	            else if (line.charAt(0) == '	')
	            {
	            	String wDataTypeName = line.substring(1);
	            	if (addingInputs)
	            		wLastWs.getPreConditions().add(new DataType(wDataTypeName));
	            	else if (addingOutputs)
	            		wLastWs.getPostConditions().add(new DataType(wDataTypeName));
	            	else
	            		OutputManager.writeToFile("Error parsing file: illegal state #1");
	            }
	            else
	            {
	            	wLastWs = new WebService (line);
	            	wResults.add(wLastWs);
	            	addingInputs = false;
	    	        addingOutputs = false;
	            }
	        	line = br.readLine();
	        }
	        br.close();
	    } 
	    catch (IOException e)
	    {
	    	OutputManager.writeToFile("Error reading the service file: " + e.getMessage());
	    }
	    return wResults;
	}
}
