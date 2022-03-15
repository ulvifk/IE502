import java.util.ArrayList;
import java.util.HashMap;

enum Type{UAVELIGBLE, TRUCK;}
public class Node {
	private Type type;
	private int index;
	private Position position;
	private int parcelWeight;
	private ArrayList<Node> outGoingArcs;
	private ArrayList<Node> inComingArcs;
	private HashMap<Drone, Double> launchTime;
	private HashMap<Drone, Double> retrievalTime;
	
	public Node(Type type, int index, Position position, int parcelWeight) {
		this.type = type;
		this.index = index;
		this.position = position;
		this.parcelWeight = parcelWeight;
		this.launchTime = new HashMap();
		this.retrievalTime = new HashMap();
	}
	
	public void addOutGoingArc(Node node) {
		this.outGoingArcs.add(node);
	}
	
	public void addInComingArc(Node node) {
		this.inComingArcs.add(node);
	}

	public Type getType() {
		return type;
	}

	public int getIndex() {
		return index;
	}

	public Position getPosition() {
		return position;
	}
	
	public int getParcelWeight() {
		return this.parcelWeight;
	}

	public double getDroneLaunchTime(Drone drone) {
		return this.launchTime.get(drone);
	}
	
	public double getDroneRetrievalTime(Drone drone) {
		return this.retrievalTime.get(drone);
	}
	
	public void setDroneLaunchTime(Drone drone, double time) {
		this.launchTime.put(drone, time);
	}
	
	public void setDroneRetrievalTime(Drone drone, double time) {
		this.retrievalTime.put(drone, time);
	}
	
}
