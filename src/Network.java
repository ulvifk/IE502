import java.util.ArrayList;
import java.util.Random;

public class Network {
	private ArrayList<Node> nodes;
	private ArrayList<Drone> drones;
	private ArrayList<Node> droneNodes;
	
	
	private void randomDataGeneration(int numberOfNodes, int numberOfDroneNodes, int range) {
		Random random = new Random(0);
		
		for(int i = 0; i<numberOfDroneNodes; i++) {
			Position position = new Position(random.nextDouble()* range, random.nextDouble() * range);
			Node node  = new Node(Type.UAVELIGBLE, i, position);
		}
		
		for(int i = numberOfDroneNodes; i<numberOfNodes; i++) {
			Position position = new Position(random.nextDouble()* range, random.nextDouble() * range);
			Node node  = new Node(Type.TRUCK, i, position);
		}
		
	}
}
