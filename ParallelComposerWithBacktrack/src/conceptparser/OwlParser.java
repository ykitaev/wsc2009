package conceptparser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import planner.OutputManager;

public class OwlParser 
{
	/**
	 * Credit: http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
	 * @param filename
	 */
	public static void parse(String filename)
	{
		HashMap<String, Concept> classMap = new HashMap<String, Concept>(1000000);
		HashMap<Concept, ArrayList<Instance>> instanceTree = ConceptInstanceMap.getMap();
		HashMap<String, Instance> instMap = ConceptInstanceMap.getInstanceMap();
		try 
		{
			OutputManager.writeToFile("Owl: Parsing..."); 
			File wFile = new File(filename);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(wFile);
		 
			OutputManager.writeToFile("Owl: Normalizing...");
			doc.getDocumentElement().normalize();
		 
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			OutputManager.writeToFile("Owl: Getting elements...");
			NodeList classList = doc.getElementsByTagName("owl:Class");
			NodeList instanceList = doc.getElementsByTagName("owl:Thing");
		 
			//System.out.println("----------------------------");
		 
			// Get classes
			OutputManager.writeToFile("Owl: Getting classes...");
			int len = classList.getLength();
			for (int temp = 0; temp < len; temp++) 
			{
				
				Node nNode = classList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
		 
					Element eElement = (Element) nNode;
		 
					String className = eElement.getAttribute("rdf:ID");
					
					Element subClassOf = (Element)eElement.getElementsByTagName("rdfs:subClassOf").item(0);
					
					if (null == subClassOf)
					{
						classMap.put(className, new Concept(className, null));
					}
					else
					{
						String parentName = subClassOf.getAttribute("rdf:resource").substring(1);
						Concept parent = classMap.get(parentName);
						classMap.put(className, new Concept(className, parent));	
					}
					
				}
				
			}
			
			// Get instances
			OutputManager.writeToFile("Owl: Getting instances...");
			len = instanceList.getLength();
			for (int temp = 0; temp < len; temp++) 
			{
				
				Node nNode = instanceList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
		 
					Element eElement = (Element) nNode;
		 
					String instanceName = eElement.getAttribute("rdf:ID");
					
					Element typeOf = (Element)eElement.getElementsByTagName("rdf:type").item(0);
					
					
					String typeName = typeOf.getAttribute("rdf:resource").substring(1);
					Concept concept = classMap.get(typeName);
					Instance instance = new Instance(instanceName, concept);
					
					if (!instanceTree.containsKey(concept))
						instanceTree.put(concept, new ArrayList<Instance>());
					
					ArrayList<Instance> list = instanceTree.get(concept);
					list.add(instance);				
					instMap.put(instanceName, instance);
				}
				
			}
			
			OutputManager.writeToFile("Owl: Caching...");
			ConceptInstanceMap.cache();
		 } 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
