package serviceparser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import planner.DataType;
import planner.WebService;

/**
 * Used to parse the services.wsdl
 * @author psyomn
 */
public class ServiceParser
{
 
  /**
   * Give a relative file path, to try and parse services
   */
  public static ArrayList<WebService> parse(String iFileName)
  {
    Document doc = null;
    DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null; 
    NodeList nodes = null;
    ArrayList<WebService> result = new ArrayList<WebService>(1000);
    
    try 
    {
      docBuilder =  fact.newDocumentBuilder();
      String data = Common.getFileContentsAsString(iFileName);
      doc = docBuilder.parse(new ByteArrayInputStream(data.getBytes()));
      nodes = doc.getElementsByTagName("service");
      
      /* Iterate through SERVICES */
      int len = nodes.getLength();
      for (int i=0; i < len; ++i)
      {
        WebService  service = null;
        NodeList children;
        NodeList inputList;
        NodeList outputList;
        
        children = nodes.item(i).getChildNodes(); 
        
        service = new WebService(nodes.item(i).getAttributes()
            .getNamedItem("name").getNodeValue());
        
        /* 0 is for inputs */
        inputList = children.item(0).getChildNodes();
        for (int j=0; j < inputList.getLength(); ++j)
        {
           service.getPreConditions().add(new DataType(inputList.item(j).getAttributes()
               .getNamedItem("name").getNodeValue()));
        }
        
        /* 1 is for outputs */ 
        outputList = children.item(1).getChildNodes();
        for (int j=0; j < outputList.getLength(); ++j)
        {
          service.getPostConditions().add(new DataType(outputList.item(j).getAttributes()
            .getNamedItem("name").getNodeValue()));
        }
        
        result.add(service);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }    
    return result;
  }
}