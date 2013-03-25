package com.project.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The class for concepts extracted from the owl file
 * @author psyomn
 *
 */
public class Concept
{
 
  public Concept(String iName)
  {
    mName = iName;
    mRelatedInstances = new HashSet<String>();
  }

  public String getName()
  {
    return mName;
  }
  
  public void addInstance(String iInst)
  {
    mRelatedInstances.add(iInst);
  }
  
  public boolean hasInstance(String iInst)
  {
    return mRelatedInstances.contains(iInst);
  }
  
  public boolean hasAllInstances(ArrayList<String> iInstances)
  {
    return mRelatedInstances.containsAll(iInstances);
  }
  
  public Set<String> getInstances()
  {
    return mRelatedInstances;
  }
  
  private String mName;
  
  private Set<String> mRelatedInstances;
}
