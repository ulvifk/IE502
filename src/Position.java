
public class Position {
	private double x;
	private double y;
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double droneDistance (Position pos, double droneFactor) {
		return distanceTo(pos) * droneFactor;
	}
	
	public double truckDistance (Position pos) {
		return distanceTo(pos);
	}
	
	private double distanceTo(Position pos) {
		return 0;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	
}
