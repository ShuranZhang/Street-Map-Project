public class Edge implements Comparable<Edge>{
 
 	public final String ID;	
 	public double weight;
 	public Node v, w;
	@Override
	public int compareTo(Edge o) {
		return (int) (o.weight-weight);
	}
  
 	public Edge(String ID, Node v, Node w) {
 		this.ID=ID;
 		this.v=v;
 		this.w=w;
 		this.weight=CoordDistance()/0.621371;
 	}
 	double CoordDistance(){
 		double latDistance = Math.toRadians(v.latitude - w.latitude);
 	
    double lngDistance = Math.toRadians(v.longtitude - w.longtitude);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
      + Math.cos(Math.toRadians(v.latitude)) * Math.cos(Math.toRadians(w.latitude))
      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return 6371 * c;
}
}