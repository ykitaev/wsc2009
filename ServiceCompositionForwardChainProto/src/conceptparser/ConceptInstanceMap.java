package conceptparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ConceptInstanceMap 
{
	private static HashMap<Concept, ArrayList<Instance>> map =  new HashMap<Concept, ArrayList<Instance>>(1000000);
	private static HashMap<String, Instance> instanceMap = new HashMap<String, Instance>(1000000);
	private static HashMap<String, ArrayList<String>> cache = new HashMap<String, ArrayList<String>>(1000000);
	
	public static  HashMap<Concept, ArrayList<Instance>> getMap()
	{
		return map;
	}
	
	public static void reset()
	{
		map.clear();
		instanceMap.clear();
		cache.clear();
		System.gc();
		Runtime.getRuntime().gc();
	}
	
	public static HashMap<String, Instance> getInstanceMap() {
		return instanceMap;
	}

	public static void setInstanceMap(HashMap<String, Instance> instanceMap) {
		ConceptInstanceMap.instanceMap = instanceMap;
	}

	public static ArrayList<Instance> findInstancesForConcept(Concept c)
	{
		return map.get(c);
	}
	
	
	private static ArrayList<String> findEquivalentInstances(String instName)
	{
		ArrayList<String> result = new ArrayList<String>();
		result.add(instName);
		Instance inst = instanceMap.get(instName);
		
		Concept iter = inst.getType();
		while (iter != null)
		{
			ArrayList<Instance> neighbours = findInstancesForConcept(iter);
			for (int i = 0; i < neighbours.size(); ++i)
				result.add(neighbours.get(i).getName());
			
			iter = iter.getParent();
		}
		
		return result;	
	}
	
	public static ArrayList<String> findEquivalentInstancesCached(String instName)
	{
		return cache.get(instName);
	}
	
	public static void cache()
	{
		Iterator<String> itr = instanceMap.keySet().iterator();
        while(itr.hasNext())
        {
            String name = itr.next();
            cache.put(name, findEquivalentInstances(name));
        }
	}
	
}
