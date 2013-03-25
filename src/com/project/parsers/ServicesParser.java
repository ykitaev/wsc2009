package com.project.parsers;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.project.domain.Service;

/**
 * Used to parse the services.wsdl
 * @author psyomn
 */
public class ServicesParser
{
  /**
   * Keys are service names, values are services
   */
  private HashMap<String, Service> mServices = new HashMap<String, Service>();
  
  /**
   * Give a relative file path, to try and parse services
   */
  public void parse(String iXMLContents)
  {
    Document doc = null;
    DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null; 
    NodeList nodes = null;
    
    try 
    {
      docBuilder =  fact.newDocumentBuilder();
      doc = docBuilder.parse(new ByteArrayInputStream(iXMLContents.getBytes()));
      nodes = doc.getElementsByTagName("service");
      
      /* Iterate through SERVICES */
      for (int i=0; i < nodes.getLength(); ++i)
      {
        Service  service = null;
        NodeList children;
        NodeList inputList;
        NodeList outputList;
        
        children = nodes.item(i).getChildNodes(); 
        
        service = new Service(nodes.item(i).getAttributes()
            .getNamedItem("name").getNodeValue());
        
        /* 0 is for inputs */
        inputList = children.item(0).getChildNodes();
        for (int j=0; j < inputList.getLength(); ++j)
        {
           service.addInService(inputList.item(j).getAttributes()
               .getNamedItem("name").getNodeValue());
        }
        
        /* 1 is for outputs */ 
        outputList = children.item(1).getChildNodes();
        for (int j=0; j < outputList.getLength(); ++j)
        {
          service.addOutService(outputList.item(j).getAttributes()
            .getNamedItem("name").getNodeValue() + "\n");
        }
        
        mServices.put(service.getName(), service);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }
}
