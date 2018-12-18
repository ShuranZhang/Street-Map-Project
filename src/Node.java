import java.util.ArrayList;

public class Node implements Comparable<Node>{

	public final String ID;
	public double distance, longtitude, latitude;
	public boolean visitied,start,end,path;
	public ArrayList<Node>adj=new ArrayList<Node>();
	public Node previous;
	@Override
	public int compareTo(Node o) {
		if(distance > o.distance) {
			return 1;
		}else if(distance == o.distance) {
			return 0;
		}
		else {
			return -1;
		}
	}
	public Node(String ID, double latitude, double longtitude) {
		this.ID=ID;
		this.latitude=latitude;
		this.longtitude=longtitude;
		this.adj=new ArrayList<Node>();
		this.visitied=false;

	}
	
	public String toString() {
		return ID;
	}
 	//Constructor, probably taking ID, longitude and latitude as parameters
}