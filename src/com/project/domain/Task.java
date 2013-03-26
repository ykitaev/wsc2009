package com.project.domain;

import java.util.HashSet;
import java.util.Set;

public class Task
{
  private Set<String> mGiven = new HashSet<String>();
  private Set<String> mGoal  = new HashSet<String>();
  
  public Task(){}
  
  public void addGivenInstance(String iInst)
  {
    mGiven.add(iInst);
  }
  
  public void addGoalInstance(String iInst)
  {
    mGoal.add(iInst);
  }
  
  public Set<String> getInputs()
  {
    return mGiven;
  }
  
  public Set<String> getOutputs()
  {
    return mGoal;
  }
  
  @Override
  public String toString()
  {
    return
        "[task| Given: [" + mGiven.toString() + "] " +
        "Goal: [" + mGoal.toString() + "]]";
  }
}
