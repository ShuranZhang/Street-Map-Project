import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graph {
	public  double minY=Double.MAX_VALUE;
	public  double minX=1000;
	public  double maxY=Double.MIN_VALUE;
	public  double maxX=-1000;
	public double ratioY, ratioX;
	public static HashMap<String, Node>v=new HashMap<String,Node>();
	public LinkedList<Node> path=new LinkedList<Node>(); 
	public  ArrayList<Edge>r=new ArrayList<Edge>();
	public  void addEdge(String ID,Node x, Node y) {
		x.adj.add(y);
		y.adj.add(x);
	}
	public Graph(String inputFile) {
		try {
			BufferedReader reader=new BufferedReader(new FileReader(inputFile));
			String current = reader.readLine();
			while(current!=null) {
				String[]s=current.split("\\s+");
				if(s[0].equals("i")) {
					if(Double.parseDouble(s[2])<minY) {
						minY=Double.parseDouble(s[2]);
					}else if(Double.parseDouble(s[2])>maxY){
						maxY=Double.parseDouble(s[2]);
					}
					if(Double.parseDouble(s[3])<minX) {
						minX=Double.parseDouble(s[3]);
					}else if(Double.parseDouble(s[3])>maxX){
						maxX=Double.parseDouble(s[3]);
					}
					v.put(s[1],new Node(s[1],Double.parseDouble(s[2]),Double.parseDouble(s[3])));
				}else if(s[0].equals("r")) {
					r.add(new Edge(s[1],v.get(s[2]),v.get(s[3])));
					addEdge(s[1],v.get(s[2]),v.get(s[3]));
				}
				current=reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics g) {
		g.setColor(Color.red);
		for(int i=0;i<path.size()-1;i++){
			Graphics2D g2 = (Graphics2D) g;
	        g2.setStroke(new BasicStroke(3));
			g2.drawLine(800-(int)path.get(i).longtitude, 650-(int)path.get(i).latitude, 800-(int)path.get(i+1).longtitude,650-(int) path.get(i+1).latitude);
		}
	}
	public void drawUR(Graphics g) {
		g.setColor(Color.red);
		for(String s:v.keySet()) {
			if(!s.contains("i")) {
				g.drawString(v.get(s).ID, 800-(int)v.get(s).longtitude, 650-(int)v.get(s).latitude);
			}
		}
	}
	public void drawNYS(Graphics g) {
		g.setColor(Color.RED);
		g.drawString("NYC", 800-(int)(800-40.7128*ratioX), (int)(650-(-74.0060)*ratioY));
	}
	public void dijkstra(Node s, Node d) {
		for(String st:v.keySet()) {
			v.get(st).visitied=false;
			v.get(st).distance=Double.MAX_VALUE;
		}
		PriorityQueue<Node> que=new PriorityQueue<Node>();
		que.offer(s);
		s.distance=0;
		s.start=true;
		while(!que.isEmpty()) {
			Node curr=que.poll();
			curr.visitied=true;
			for(Node node:curr.adj) {
				if(!node.visitied) {
					double dis=curr.distance+Math.sqrt(Math.pow((curr.longtitude-node.longtitude), 2)+Math.pow((curr.latitude-node.latitude), 2));
					if(node.distance>dis) {
						node.distance=dis;
						node.previous = curr;
						que.add(node);
					}
				}
			}

		}
		
		printPath(d);		
		System.out.println("The path is: "+Arrays.toString(path.toArray()));
		 ArrayList<Edge>path2=new ArrayList<Edge>();
		Double totalMile=0.0;
		for(int i=0;i<path.size()-1;i++) {
			path2.add(new Edge(" ",path.get(i),path.get(i+1)));
		}
		for(Edge e:path2) {
			totalMile+=e.weight;
		}
		System.out.println("The total travel distance is: "+totalMile+" Miles");
	}
	public void scale() {
		double ratioX=800/(maxX-minX)*(-1);
		double ratioY=650/(maxY-minY);
		for(String s: v.keySet()) {
			v.get(s).latitude-=minY;
			v.get(s).latitude*=ratioY;
			v.get(s).longtitude-=maxX;
			v.get(s).longtitude*=ratioX;
		}
	}
	
	public void printPath(Node n) {
		if(n.previous != null){
			printPath(n.previous);
			
		}
		path.add(n);
	}
	
}
