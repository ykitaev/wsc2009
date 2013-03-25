package com.project.domain;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * An atomic service class for the services.wsdl file.
 * @author psyomn
 */
public class Service
{
  public String mServiceName; 
  
  private HashSet<String> mInputs = new HashSet<String>(); 
  private HashSet<String> mOutputs = new HashSet<String>();
  
  public Service(String iName)
  {
    mServiceName = iName;
  }
  
  public void addOutService(String iChildServiceName)
  {
    mOutputs.add(iChildServiceName);
  }
  
  public void addInService(String iChildServiceName)
  {
    mOutputs.add(iChildServiceName);
  }
  
  /**
   * checks to see if service has all the string inputs
   * @param iInputs An array list that contains all the terms you want to check
   * if the service has
   * @return
   */
  public boolean hasInputs(ArrayList<String> iInputs)
  {
    return mInputs.containsAll(iInputs);
  }
  
  /**
   * checks to see if service has all the string outputs
   * @param iOutputs
   * @return true if it has all the outputs
   */
  public boolean hasOutputs(ArrayList<String> iOutputs)
  {
    return mOutputs.containsAll(iOutputs);
  }

  /**
   * Get the name of the service
   * @return
   */
  public String getName()
  {
    return mServiceName;
  }
}
