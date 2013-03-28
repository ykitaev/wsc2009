package auxparsers;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import planner.DataType;

public class ChallengeParser 
{
	ArrayList<DataType> inputs = new ArrayList<DataType>();
	ArrayList<DataType> outputs = new ArrayList<DataType>();
	boolean parsed = false;
	
	public void parse(String fileName)
	{
		try 
		{
			File wFile = new File(fileName);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(wFile);
			doc.getDocumentElement().normalize();
		 
			NodeList problemList = doc.getElementsByTagName("problemStructure");

				
			Node problem = problemList.item(0);
	 
			if (problem.getNodeType() == Node.ELEMENT_NODE) 
			{
	 
				NodeList taskList = problem.getChildNodes();
				Element task = (Element)taskList.item(0);
				
				NodeList given = task.getElementsByTagName("provided");
				NodeList wanted = task.getElementsByTagName("wanted");
				
				// Get given
				NodeList givenList  = given.item(0).getChildNodes();
				int len = givenList.getLength();
				for (int i = 0; i < len; ++i)
				{
					Node given1 = givenList.item(i);
					Element e = (Element) given1;
					String inst = e.getAttribute("name");
					inputs.add(new DataType(inst));
				}
				
				// Get wanted
				NodeList wantedList = wanted.item(0).getChildNodes();
				len = wantedList.getLength();
				for (int i = 0; i < len; ++i)
				{
					Node wanted1 = wantedList.item(i);
					Element e = (Element) wanted1;
					String inst = e.getAttribute("name");
					outputs.add(new DataType(inst));
				}
				
				parsed = true;
			}
		 } 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
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
