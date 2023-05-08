import java.io.*;
import java.util.*;

public class tester {
	public static ArrayList<Router> routers = new ArrayList<Router>();
	
	public static void main(String[] args) throws IOException
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input a file path to create the network: ");
		String f = scan.next();
		
		Initializer i = new Initializer(f);
		
		Router.copyArrayList(i.exportNetwork(), routers);
	
		scan.close();System.out.println();System.out.println();
		
		i.routerList();System.out.println();System.out.println();
		
		i.setTablesDRT();
		
		i.setNeighbors(); 
		
		routers.get(0).printNeighbors();
		
		System.out.println();
		System.out.println("----------------------------------------");System.out.println();
		
		
		
		for(int a = 0; a < routers.size(); a++)
		{
			Router r = routers.get(a);
			
			r.builder(routers);

			System.out.println(r.getName() + "'s Routing Table: ");
			
			routers.get(a).printRoutingTable();
		}
		
		
	}
}
