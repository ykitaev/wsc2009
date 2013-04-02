package presentation;

import javax.swing.*;

import planner.Action;
import planner.ParallelActionPack;
import planner.Proposition;
import planner.State;

import java.awt.*;
import java.util.ArrayList;

public class SolutionDraw extends JDialog{
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<ParallelActionPack> solution = null;
	State gs = null;
	State is = null;

	public SolutionDraw(ArrayList<ParallelActionPack> s,State initialState, State goalState)
	{
		super();
		  solution = s;
		  gs = goalState;
		  is = initialState;
          setSize(800,600);
          setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
          setLocationRelativeTo(null);
          setModal(true);
          setVisible(true);
          getContentPane().setBackground(Color.WHITE);
          setBackground (Color.WHITE);
     }
	
     public void paint(Graphics g)
     {
    	 super.paint(g);
    	 
    	 State ssf = new State();
    	 int hoffset = 200;
    	 int voffset = 100;
    	 int steph = 110;
    	 int stepv = 20;
    	 int rech = 15;
    	 int recw = 100;
    	   	 
    	 // Draw initial state
    	 int isy = voffset;
    	 int isx = hoffset - steph;
    	 g.drawString("Initial state:" , isx , isy);
    	 for (Proposition p : is.getState())
    	 {
    		 isy+=20;
    		 g.drawString(p.toString(), isx, isy);
    	 }
    	 
    	 // Draw the sequence of actions
    	 int lastx = 0;
    	 for (int i = 0; i < solution.size(); ++i)
    	 {
    		 int x = hoffset + i * steph;
    		 int starty = voffset;
    		 lastx = x;
    		 ParallelActionPack p = solution.get(i);
    		 int lasty = 0;
    		 for (int j = 0; j < p.getActions().size(); ++j)
    		 {
    			 Action a = p.getActions().get(j);
    			 ssf.applyActionWithExpansion(a);
    			 int y = voffset + j * stepv;
    			 g.drawString(a.toString(), x, y);
    			
    			 lasty = y;
    		 }
    		 
    		 g.drawRect(x-(stepv-rech)/2, starty-rech, recw, lasty-starty+rech+10);
    		 
    		 int y = lasty + 30;
 
    		 // Draw useful outcomes (in terms of the goal state)
    		 g.drawString("Useful for goal:", x, y);
    		 y+=20;
    		 for (Proposition gp : gs.getState())
    		 {
    			 if (ssf.getState().contains(gp))
    			 {
    				 g.drawString(gp.toString(), x, y);
    				 y+=20;
    			 }
    		 }
    	 }
    	 
    	 // Show the goal state:
    	 int x = lastx + steph;
    	 int y = voffset;
    	 g.drawString("Goal state:" , x , y);
    	 for (Proposition p : gs.getState())
    	 {
    		 y+=20;
    		 g.drawString(p.toString(), x, y);
    	 }
           
     }
}