
import java.util.*;

//I think this is done
public class Router {
	public String name;
	//public ArrayList<RoutingTable> table = new ArrayList <RoutingTable>();
	public Packet message = new Packet();
	public HashMap<Router, Integer> neighbors = new HashMap<Router, Integer>();
	public HashMap<Router, Integer> routingTable = new HashMap<Router, Integer>();
	public boolean sentPacket;
	public ArrayList<Router> destinations = new ArrayList<Router>();
	public boolean receivingPacket = false;

	public Router()
	{
		name = "R#";
	}

	public Router(String n)
	{
		name = n;
	}

	public void setName(String n)
	{
		name = n;
	}

	public String getName()
	{
		return name;
	}

	public void addNeighbor(Router r, int cost)
	{
		neighbors.put(r, cost);
		System.out.println("Router " + r.getName() + " is now a neighbor of " + name);
		updateRT(r, cost);
	}

	public void removeNeighbor(Router r)
	{
		neighbors.remove(r);
		destinations.remove(r);
		routingTable.remove(r);
		System.out.println("Neighbor " + r + " has been removed.");
	}

	public HashMap<Router, Integer> getNeighbors()
	{
		return neighbors;
	}

	public void printNeighbors()
	{
		System.out.println(name + "'s neighbors: ");
		for(Map.Entry<Router, Integer> mapElement : neighbors.entrySet())
		{
			System.out.print(mapElement.getKey().getName() + " ");
		}
		System.out.println();
	}

	public void changeCost(Router r, int c)
	{
		neighbors.replace(r, c);
	}

	public void addDestination(Router r)
	{
		destinations.add(r);
		routingTable.put(r, Integer.MAX_VALUE-10);
	}

	public void removeDestination(Router r)
	{
		destinations.remove(r);
	}

	public ArrayList<Router> getDestination()
	{
		return destinations;
	}

	public void printDestination()
	{
		System.out.println(name + "'s destinations: ");
		for(int i = 0; i < destinations.size(); i++)
		{
			System.out.print(destinations.get(i).getName() + " ");
		}
		System.out.println();
	}

	public void incomingPacket(Boolean b, Packet p, ArrayList<Router> net)
	{
		receivingPacket = b;
		listener(net, p);
		receivingPacket = false;
	}

	public void listener(ArrayList<Router> net, Packet p)
	{
		if(receivingPacket == true)
			readingBuilder(net, p);
	}

	public void createRoutingTable(ArrayList<Router> d)
	{
		for(int i = 0; i < d.size(); i++)
		{
			routingTable.put(d.get(i), Integer.MAX_VALUE-10); //sets all routers to have max integer value, helps with dijsktra's algo
		}
	}
	
	public void updateRT(Router r, int c)
	{
		String rName = r.getName();
		
		for(Map.Entry<Router, Integer> mapElement: routingTable.entrySet())
		{
			Router rt = mapElement.getKey();
			String rtName = rt.getName();
			
			if(rtName.equals(rName))
			{
				routingTable.replace(rt, c);
				break;
			}
				
		}
	}

	public void dijkstra(Router send, HashMap<Router, Integer> s)
	{
		Router[] m = new Router[routingTable.size()];
		int[] l = new int[routingTable.size()];
		int w = 0;


		System.out.println("In Dijkstra, this is sender's name " + send.getName());

		for(Map.Entry<Router,Integer> mapElement : routingTable.entrySet())
		{
			Router r = mapElement.getKey();
			int o = routingTable.get(r);

			m[w] = r;
			l[w] = o;

			w++;
		}

		for(Map.Entry<Router,Integer> mapElement : s.entrySet())
		{
			Router r = mapElement.getKey();

			if(routingTable.get(r) > s.get(r))
			{
				int sum = routingTable.get(send) + s.get(r);
				routingTable.replace(r, sum);
				System.out.println("Router " + r.getName() + "'s value in the Routing Table is now " + routingTable.get(r));
			}

		}
	}

	public void builder(ArrayList<Router> net)
	{
		//String object, router name, router's neighbors, and the cost of each neighbor
		String message = name + ": ";
		message = message + stringHashMap(neighbors);

		System.out.println("This is the packet's massage substring: " + message.substring(0, 2));

		ArrayList<Router> netMinusThis = new ArrayList<Router>();

		System.out.println("Original Network Array:"); printArrayList(net);

		copyArrayList(net, netMinusThis);

		for(int i = 0; i < net.size(); i++)
		{
			if(name.equals(net.get(i).getName()))
			{
				netMinusThis.remove(i);
			}
		}

		System.out.println();
		System.out.println(name + "'s Network Array:"); printArrayList(netMinusThis);

		for(int i = 0; i < netMinusThis.size(); i++)
		{		
			Router r = netMinusThis.get(i);	

			Packet hello = new Packet(message);
			r.incomingPacket(true, hello, net);
			//r.readingBuilder(hello);
		}
	}


	public void readingBuilder(ArrayList<Router> net, Packet h)
	{
		String message = h.getMessage();
		String[] s = message.split("");
		String rName = message.substring(0, 2);
		String n;
		Router sender = new Router();
		HashMap<Router, Integer> senderNeigh = new HashMap<Router, Integer>();



		for(int i = 0; i < net.size(); i++)
		{
			n = net.get(i).getName();

			System.out.println();
			System.out.println("Does " + rName + " substring equal " + n + "?");

			if(rName.equals(n))
			{
				sender = net.get(i);
				break;
			}
		}

		System.out.println("Who sent this? " + sender.getName());	
		copyHashMap(sender.getNeighbors(), senderNeigh);

		for(Map.Entry<Router, Integer> mapElement : senderNeigh.entrySet())
		{
			Router r = mapElement.getKey();
			addDestination(r);
		}


		System.out.println(name +"'s destinations: ");
		printDestination();

		//copyHashMap(sender.getNeighbors(), senderNeigh);

		//dijkstra(sender, senderNeigh);
	}



	public void printRoutingTable()
	{
		Formatter labels = new Formatter();
		//Iterator rtIterator = routingTable.entryS
		labels.format("%15s %15s\n", "Router", "Cost");

		for(Map.Entry<Router, Integer> mapElement : routingTable.entrySet())
		{
			Router i = mapElement.getKey();
			String router = i.getName();
			int c = routingTable.get(i);

			labels.format("%14s %17s %n", router, c);	
		}

		System.out.println(labels);

	}

	public String stringHashMap(HashMap<Router, Integer> hm)
	{
		String h = "";
		for(Map.Entry<Router, Integer> mapElement : hm.entrySet())
		{
			Router r = mapElement.getKey();
			int c = mapElement.getValue();

			h += "(" + r + ", " + c + ") ";
		}

		return h;
	}


	public void copyHashMap(HashMap<Router, Integer> original, HashMap<Router, Integer> copy)
	{
		for(Map.Entry<Router, Integer> mapElement : original.entrySet())
		{
			Router cop;
			int copyValue;

			cop = mapElement.getKey();
			copyValue = original.get(cop);

			copy.put(cop, copyValue);
		}
	}

	public static void copyArrayList(ArrayList<Router> original, ArrayList<Router> copy)
	{
		for(int i = 0; i < original.size(); i++)
		{
			copy.add(original.get(i));
		}
	}

	public static void printArrayList(ArrayList<Router> list)
	{
		System.out.println("List: ");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.print(list.get(i).getName() + " ");
		}
		System.out.println();
	}

}
