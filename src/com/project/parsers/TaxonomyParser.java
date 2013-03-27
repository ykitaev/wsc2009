package com.project.parsers;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Responsible for actually parsing the OWL document (Taxonomy.owl)
 * @author psyomn
 */
public class TaxonomyParser
{
  /**
   * Parse the Taxonomy file
   * @param iXMLContents
   */
  public void parse(String iXMLContents)
  {
    Document doc = null;
    DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null; 
    NodeList nodes = null;
    Stack<Node> unvisitedConcepts = new Stack<Node>();
    Set<String> visitedConcepts = new HashSet<String>();
    
    try
    {
      docBuilder =  fact.newDocumentBuilder();
      doc = docBuilder.parse(new ByteArrayInputStream(iXMLContents.getBytes()));
      nodes = doc.getElementsByTagName("concept");
      System.out.println(nodes.getLength());
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}
