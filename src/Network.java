import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.print.attribute.HashAttributeSet;

public class Network {
	private ArrayList<Node> C;
	private ArrayList<Drone> V;
	private HashMap<Drone, ArrayList<Node>> CDrone;
	private ArrayList<Node> N;
	private ArrayList<Node> NPlus;
	private ArrayList<Node> N0;
	
	private Vehicle vehicle;
	private Node startingDepot;
	private Node endingDepot;
	
	public Network(int droneLimit) throws FileNotFoundException {
		this.C = new ArrayList<>();
		this.V = new ArrayList<>();
		this.CDrone = new HashMap();
		for(Drone v : this.V) {
			this.CDrone.put(v, new ArrayList<Node>());
		}
		this.N = new ArrayList();
		this.NPlus = new ArrayList();
		this.N0 = new ArrayList();
		this.vehicle = new Vehicle(sumParcelWeight());
		randomDataGeneration(10, 4, 10, 10);
		writeNetworkToCsv();
	}
	
	private int sumParcelWeight() {
		int sum = 0;
		for(Node node : this.C) {
			sum += node.getParcelWeight();
		}
		
		return sum;
	}
	
	private void populateDrones(int droneLimit) {
		for(int i = 0; i<droneLimit; i++) {
			Drone drone = new Drone(i);
			this.V.add(drone);
		}
	}
	private void randomDataGeneration(int numberOfCustomers, int numberOfDroneNodes, int range, int parcelRange) {
		Random random = new Random(0);
		
		Node startingDepot = new Node(Type.TRUCK, 0, new Position(0, 0), 0);
		Node endingDepot = new Node(Type.TRUCK, numberOfCustomers+1, new Position(0, 0), 0);
		
		this.startingDepot = startingDepot;
		this.endingDepot = endingDepot;
		
		this.N.add(startingDepot);
		this.N.add(endingDepot);
		
		this.NPlus.add(endingDepot);
		this.N0.add(startingDepot);
		
		for(int i = 1; i<numberOfDroneNodes+1; i++) {
			Position position = new Position(random.nextDouble()* range, random.nextDouble() * range);
			Node node  = new Node(Type.UAVELIGBLE, i, position, random.nextInt(parcelRange) + 1);
			this.C.add(node);
			for(Drone v : this.V) {
				this.CDrone.get(v).add(node);
			}
			this.N.add(node);
			this.NPlus.add(node);
			this.N0.add(node);
		}
		
		for(int i = numberOfDroneNodes+1; i<numberOfCustomers+1; i++) {
			Position position = new Position(random.nextDouble()* range, random.nextDouble() * range);
			Node node  = new Node(Type.TRUCK, i, position, random.nextInt(parcelRange) + 1);
			this.C.add(node);
			this.N.add(node);
			this.NPlus.add(node);
			this.N0.add(node);
		}	
	}
	private void writeNetworkToCsv() throws FileNotFoundException {
		File file = new File("network.csv");
		PrintWriter out = new PrintWriter(file);
		
		out.println("Index, x, y");
		for(Node node : this.C) {
			out.printf("%d, %f, %f\n", node.getIndex(), node.getPosition().getX(), node.getPosition().getY());
		}
		
		out.close();
	}

	public ArrayList<Node> getC() {
		return C;
	}

	public ArrayList<Drone> getV() {
		return V;
	}

	public HashMap<Drone, ArrayList<Node>> getCDrone() {
		return CDrone;
	}

	public ArrayList<Node> getN() {
		return N;
	}

	public ArrayList<Node> getNPlus() {
		return NPlus;
	}

	public ArrayList<Node> getN0() {
		return N0;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Node getStartingDepot() {
		return startingDepot;
	}

	public Node getEndingDepot() {
		return endingDepot;
	}

	
}
