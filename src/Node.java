import java.util.ArrayList;
import java.util.HashMap;

enum Type{UAVELIGIBLE, TRUCK;}
public class Node {
	private Type type;
	private int index;
	private Position position;
	private double parcelVolume;
	
	private HashMap<Drone, Double> launchTime;
	private HashMap<Drone, Double> retrievalTime;
	private HashMap<Drone, Double> droneServiceTime;
	private double truckServiceTime;
	
	private HashMap<Node, Double> distancesTo;
	
	public Node(String type, int index, Position position, double parcelVolume) {
		if(type.equals("UAVELIGIBLE")) this.type = Type.UAVELIGIBLE;
		else if(type.equals("TRUCK")) this.type = Type.TRUCK;
		this.index = index;
		this.position = position;
		this.parcelVolume = parcelVolume;
		this.launchTime = new HashMap();
		this.retrievalTime = new HashMap();
		this.droneServiceTime = new HashMap<>();
		this.distancesTo = new HashMap<>();
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
	
	public double getParcelVolume() {
		return this.parcelVolume;
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
	
	public double getDroneDistanceTo(Node j, Drone v) {
		return this.distancesTo.get(j) * v.getDroneFactor();
	}
	
	public double getTruckDistanceTo(Node j) {
		return this.distancesTo.get(j);
	}

	public void addDroneServiceTime(Drone v, double time) {
		this.droneServiceTime.put(v, time);
	}
	
	public double getDroneServiceTime(Drone v) {
		return this.droneServiceTime.get(v);
	}
	
	public void setDroneServiceTime(Drone v, double time) {
		this.droneServiceTime.put(v, time);
	}

	public double getTruckServiceTime() {
		return truckServiceTime;
	}

	public void setTruckServiceTime(double truckServiceTime) {
		this.truckServiceTime = truckServiceTime;
	}
	
	public void addDistanceTo(Node node, double distance) {
		this.distancesTo.put(node, distance);
	}
	
	
}
