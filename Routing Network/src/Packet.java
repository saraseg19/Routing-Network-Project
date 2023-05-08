import java.util.HashMap;

public class Packet {
	public String message;
	public int cost;
	public boolean acknowledgement;
	//public Router destination = new Router();

	public Packet()
	{}
	
	public Packet(String m)
	{
		message = m;;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void changeMessage(String m)
	{
		message = m;
	}
	
	public boolean posAcknowledgement()
	{
		acknowledgement = true;
		return acknowledgement;
	}
	
	public boolean negAcknowledgement()
	{
		acknowledgement = false;
		return acknowledgement;
	}
	
	
	public int costSum(Router sender, Router receiver)
	{
		int sum = 0;
		
		return sum;
	}
	
	public void shortestPath(Router sender, Router receiver)
	{
		//Dijkstra�s Shortest Path Algorithm
		//int min = costSum(sender, receiver);
	}
	
	
	public void printTable()
	{
		
	}
	
}
