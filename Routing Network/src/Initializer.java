import java.io.*;
import java.util.*;

public class Initializer {
	public ArrayList<Router> network;
	public File networkFile;

	public Initializer(String f) throws IOException
	{
		networkFile = new File(f);
		Scanner scan = new Scanner(networkFile);
		int networkSize = scan.nextInt();
		System.out.println("Network size: " + networkSize);
		network = new ArrayList<Router>();

		for(int i = 1; i <= networkSize; i++)
		{
			String name = "R" + i;
			addToList(name);
		}

		scan.close();
	}

	public void addToList(String n)
	{
		Router r = new Router(n);
		network.add(r);
	}

	public void setTablesDRT()
	{
		for(int i = 0; i < network.size(); i++)
		{
			Router r = network.get(i);
			for(int j = 0; j < network.size(); j++)
			{
				if(i == j)
				{}
				else
				{
					r.addDestination(network.get(j));
				}
			}
		}
	}

	public void setNeighbors()
	{	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(networkFile));

			String line = "er";
			line = reader.readLine();

			int j = 0;

			while(line != null && j < network.size())
			{
				line = reader.readLine();

				if(line == null)
					break;

				String[] s = line.split("");

				infoSubstring(network.get(j), s, line);

				j++;
			}

			for(int i = 0; i < network.size(); i++)
			{
				System.out.println(network.get(i).toString());
			}
			
			reader.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public void infoSubstring(Router r, String[] s, String l)
	{
		int open = 0;
		int close = 0;

		for(int i = 0; i < s.length; i++)
		{
			if(s[i].equals("("))
				open = i;			
			if(s[i].equals(")"))
				close = i;

			if((open != 0) && (close != 0))
			{
				String sub = l.substring(open, close);

				findInfo(r, sub);

				open = 0; close = 0; //restarting the search for the next neighbor in the line
			}
		}
	}

	//Takes router and will add neighbors and their info, from line s
	public void findInfo(Router r, String s)
	{
		String name = s.substring(1, 3);
		int c = Integer.parseInt((s.substring(5, s.length()-1)));
		Router n = new Router(name);

		neighborInfo(r, n, c);
	}

	public void neighborInfo(Router r, Router n, int c)
	{
		r.addNeighbor(n, c);
	}

	public void routerList()
	{
		System.out.println();
		System.out.print("[");

		for(int i = 0; i < network.size(); i++)
		{
			System.out.print(network.get(i).getName() + ", ");
		}

		System.out.print("]");
	}

	public ArrayList<Router> exportNetwork()
	{
		return network;
	}

	public void printStringArray(String[] a)
	{
		System.out.println();
		System.out.print("[");
		for(int i = 0; i < a.length-2; i++)
		{
			System.out.print(a[i] + " | ");
		}
		System.out.print(a[a.length-1] + "]"); System.out.println();

	}

} 
