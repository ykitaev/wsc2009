package planner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextArea;


public class OutputManager 
{
	private static PrintWriter fileOut = null;
	private static boolean mute = true;
	private static JTextArea textBox = null;
	
	static {
	try
	{
		fileOut = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Yuri\\Documents\\search.txt",true)));
	}
	catch (Exception e){}
	}
	
	public static void setTextArea(JTextArea t)
	{
		textBox = t;
	}
	
	public static void outputLevelStatus(int level, State state, State goalState, ArrayList<? extends Action> applicableActions)
	{		
		if (mute)
			return ;
		
		writeToFile("************Level " + level + "***************** ");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("State at this level: ");
		Iterator<? extends Proposition> itrState = state.getState().iterator();
        while(itrState.hasNext()){
            sb.append(itrState.next());
            if (itrState.hasNext())
            	sb.append(", ");
        }
        sb.append(".\n");
		

		sb.append("Applicable services: ");
		
		Iterator<? extends Action> itr = applicableActions.iterator();
        while(itr.hasNext()){
            sb.append(itr.next().toString());
            if (itr.hasNext())
            	sb.append(", ");
        }
        sb.append(".\n");
        sb.append("Number of services : ");
        sb.append(applicableActions.size());
        sb.append("\n");
		writeToFile(sb.toString());
	}
	
	public static  void outputStartInfo(State initialState, State goalState)
	{		
		StringBuilder sb = new StringBuilder();
		sb.append("Input concepts: ");
		
		Iterator<? extends Proposition> itr = initialState.getState().iterator();
        while(itr.hasNext()){
            sb.append(itr.next().toString());
            if (itr.hasNext())
            	sb.append(", ");
        }
        sb.append(".\n");
        
        sb.append("Goal concepts: ");
        
		itr = goalState.getState().iterator();
        while(itr.hasNext()){
            sb.append(itr.next().toString());
            if (itr.hasNext())
            	sb.append(", ");
        }
        sb.append(".\n");
		
        writeToFile(sb.toString());
	}
	
	public static void backtrack(ArrayList<Action> tail)
	{
		if (mute)
			return;
		
		if (null == tail)
			writeToFile("Dead end!\n");
		else if (tail.size() == 0)
			writeToFile("Backtrack: goal state has been reached!");
		else 
			writeToFile("Backtrack with : " + tail.toString());
	}
	
	public static void writeToFile(String s)
	{
		textBox.append(s + "\n");
		//System.out.println(s);
		    fileOut.println(s);
		    fileOut.flush();
	}

}
