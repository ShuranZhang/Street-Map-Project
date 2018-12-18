import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Canvas extends JComponent implements ActionListener {
	public Graph graph;
	public boolean showPath = false;
	public boolean textUse=false;
	public String st,en;
	public static JTextField start;
	public static JTextField end;
	public Canvas(String arg){
		graph=new Graph(arg);
	}
	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, 800, 800);
		g.setColor(Color.cyan);
		for(Edge e: graph.r) {
			g.drawLine(800-(int)e.v.longtitude, 650-(int)e.v.latitude, 800-(int)e.w.longtitude, 650-(int)e.w.latitude);
		}			graph.drawUR(g);
		if(showPath) {
			g.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 8));
			graph.draw(g);
			repaint();
		}
		graph.drawNYS(g);
		repaint();
	}

	public static void main(String[]args) {	
		JFrame frame = new JFrame("Project4");
		frame.setLayout(new BorderLayout());
		Canvas canvas=new Canvas(args[0]);
		if(args.length==5) {	
			canvas.graph.dijkstra(canvas.graph.v.get(args[3]), canvas.graph.v.get(args[4]));
			canvas.graph.scale();
			canvas.showPath = true;
		}else if(args[1].contains("--directions")) {
			canvas.graph.dijkstra(canvas.graph.v.get(args[2]), canvas.graph.v.get(args[3]));
			canvas.graph.scale();
			canvas.showPath = true;
		}
		if(canvas.textUse) {
			canvas.graph.dijkstra(canvas.graph.v.get(start.getText()), canvas.graph.v.get(end.getText()));
			canvas.showPath = true;
			canvas.repaint();
		}

		JPanel sub = new JPanel(new FlowLayout());
		start=new JTextField(5);
		start.addActionListener(canvas);
		sub.add(start);
		end=new JTextField(5);
		end.addActionListener(canvas);
		sub.add(end);
		frame.add(sub,BorderLayout.NORTH);
		frame.add(canvas,BorderLayout.CENTER);
		frame.setSize(800,720);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(graph.v.containsKey(start.getText())&&graph.v.containsKey(end.getText())) {
		st=start.getText();
		en=end.getText();
		textUse=true;
		}
	}
}
