package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.print.attribute.HashAttributeSet;

public class Network {

	public double sigmaPenalty;
	public double sigmaTime;
	
	Random random = new Random(0);
	private double droneVolumePercentage = 0.15;
	
	private ArrayList<Node> C;
	private ArrayList<Drone> V;
	private HashMap<Drone, ArrayList<Node>> CDrone;
	private ArrayList<Node> N;
	private ArrayList<Node> NPlus;
	private ArrayList<Node> N0;
	
	private Vehicle vehicle;
	private Node startingDepot;
	private Node endingDepot;
	
	private double fixedTruckServiceTime = 30;
	private double fixedDroneLaunchTime = 60;
	private double fixedDroneRetrievalTime = 30;
	private double fixedDroneServiceTime = 60;
	
	public Network(int droneLimit, String fileLocation, int penaltyMin) throws IOException {
		this.V = new ArrayList<>();
		populateDrones(droneLimit);
		
		this.C = new ArrayList<>();
		this.CDrone = new HashMap();
		for(Drone v : this.V) {
			this.CDrone.put(v, new ArrayList<Node>());
		}
		this.N = new ArrayList();
		this.NPlus = new ArrayList();
		this.N0 = new ArrayList();
		
		
		readNodeInformation(fileLocation);
		populateSets();
		readDistanceMatrix(fileLocation);
		randomServiceTimeGeneration();
		generateNodePenalites(penaltyMin);
		
		this.vehicle = new Vehicle(Math.ceil(sumParcelWeight()));
		int x = 0;
	}
	
	private double sumParcelWeight() {
		double sum = 0;
		for(Node node : this.C) {
			sum += node.getParcelVolume();
		}
		
		return sum;
	}
	
	private void generateNodePenalites(int penaltyMin) {
		for(Node j : this.C) {
			j.setPenalty(penaltyMin*60);
		}
	}
	
	private void randomServiceTimeGeneration() {
		for(Drone v: this.V) {
			this.startingDepot.setDroneLaunchTime(v, fixedDroneLaunchTime);
		}
		for(Drone v: this.V) {
			this.endingDepot.setDroneRetrievalTime(v, fixedDroneRetrievalTime);
		}
		
		for(Node i : this.C) {
			i.setTruckServiceTime(this.fixedTruckServiceTime);
			for(Drone v : this.V) {
				i.setDroneLaunchTime(v, fixedDroneLaunchTime);
				i.setDroneRetrievalTime(v, fixedDroneLaunchTime);
				i.setDroneServiceTime(v, this.fixedDroneServiceTime);
			}
		}
		
		
		for(Drone v : this.V) {
			for(Node i : this.CDrone.get(v)) {
				i.setDroneLaunchTime(v, this.fixedDroneLaunchTime);
				i.setDroneRetrievalTime(v, this.fixedDroneRetrievalTime);
				i.setDroneServiceTime(v, this.fixedDroneServiceTime);
			}
		}
	}
	
	private void populateDrones(int droneLimit) {
		for(int i = 0; i<droneLimit; i++) {
			Drone drone = new Drone(i, 0.5, 1500);
			this.V.add(drone);
		}
	}

	private void readNodeInformation(String fileLocation) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileLocation+ ".csv"));
		String line = "";
		
		line = br.readLine();
		line = br.readLine();
		String[] tokens = line.split(",");
		Position depotPos = new Position(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
		Node startDepot = new Node("TRUCK", 0, depotPos, 0);
		
		this.N.add(startDepot);
		
		this.N0.add(startDepot);
		
		this.startingDepot = startDepot;
		
		while((line = br.readLine()) != null) {
			tokens = line.split(",");
			Position pos = new Position(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));	
			Node node = new Node(tokens[3], Integer.parseInt(tokens[0]), pos, Double.parseDouble(tokens[4]));
			this.N.add(node);
			
			if(Double.parseDouble(tokens[4]) == 0) {
				this.endingDepot = node;
				this.NPlus.add(node);
			}
		}
		
	}
	
	private void populateSets() {
		for(Node node : this.N) {
			if(node == this.endingDepot || node == this.startingDepot) continue;
			if(node.getType() == Type.UAVELIGIBLE) {
				this.C.add(node);
				for(Drone v : this.V) {
					this.CDrone.get(v).add(node);
				}
				this.NPlus.add(node);
				this.N0.add(node);
			}
			else if(node.getType() == Type.TRUCK) {
				this.C.add(node);
				this.NPlus.add(node);
				this.N0.add(node);
			}
		}
	}
	
	private Node findNodeByIndex(int index) {
		for(Node node : this.N) {
			if(node.getIndex() == index) {
				return node;
			}
		}
		return null;
	}
	
	private void readDistanceMatrix(String fileLocation) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileLocation + "_DistanceMatrix.csv"));
		String line = "";
		line = br.readLine();
		String[] columns = line.split(",");
		
		while((line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			Node sourceNode = findNodeByIndex(Integer.parseInt(tokens[0]));
			
			for(int colNumber = 1; colNumber<columns.length; colNumber++) {
				Node destNode = findNodeByIndex(colNumber - 1);
				double distance = Double.parseDouble(tokens[colNumber]);
				sourceNode.addDistanceTo(destNode, distance);
			}
		}
	}
	// Getter setters.
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

	public double getDroneVolumePercentage() {
		return droneVolumePercentage;
	}
}
