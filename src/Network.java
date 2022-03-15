import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Network {
	private ArrayList<Node> C;
	private ArrayList<Drone> V;
	private ArrayList<Node> CDrone;
	private ArrayList<Node> N;
	private ArrayList<Node> NPlus;
	private ArrayList<Node> N0;
	
	private Vehicle vehicle;
	
	public Network(int droneLimit) throws FileNotFoundException {
		this.C = new ArrayList<>();
		this.V = new ArrayList<>();
		this.CDrone = new ArrayList<>();
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
			Drone drone = new Drone();
			this.V.add(drone);
		}
	}
	private void randomDataGeneration(int numberOfCustomers, int numberOfDroneNodes, int range, int parcelRange) {
		Random random = new Random(0);
		
		Node departDepot = new Node(Type.TRUCK, 0, new Position(0, 0), 0);
		Node arrivalDepot = new Node(Type.TRUCK, numberOfCustomers+1, new Position(0, 0), 0);
		
		this.N.add(departDepot);
		this.N.add(arrivalDepot);
		
		this.NPlus.add(arrivalDepot);
		this.N0.add(departDepot);
		
		for(int i = 1; i<numberOfDroneNodes+1; i++) {
			Position position = new Position(random.nextDouble()* range, random.nextDouble() * range);
			Node node  = new Node(Type.UAVELIGBLE, i, position, random.nextInt(parcelRange) + 1);
			this.C.add(node);
			this.CDrone.add(node);
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

	public ArrayList<Node> getCDrone() {
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

	
}
