import java.util.ArrayList;

enum Type{UAVELIGBLE, TRUCK;}
public class Node {
	private Type type;
	private int index;
	private Position position;
	private ArrayList<Node> outGoingArcs;
	private ArrayList<Node> inComingArcs;
	
	public Node(Type type, int index, Position position) {
		
	}
	
	public void addOutGoingArc(Node node) {
		this.outGoingArcs.add(node);
	}
	public void addInComingArc(Node node) {
		this.inComingArcs.add(node);
	}
}
