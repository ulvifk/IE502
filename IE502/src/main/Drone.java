package main;

public class Drone {
	private double volume;
	private int index;
	private double droneFactor;
	private double endurance;
	
	public Drone(int index) {
		this.index = index;
		this.droneFactor = 0.5;
		this.endurance = 1500;
	}
	public Drone(int index, double droneFactor, double endurance) {
		this.index = index;
		this.droneFactor = droneFactor;
		this.endurance = endurance;
	}

	public int getIndex() {
		return index;
	}
	
	public double getDroneFactor() {
		return this.droneFactor;
	}

	public double getEndurance() {
		return endurance;
	}
	
	
}
