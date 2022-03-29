
public class Position {
	private double x;
	private double y;
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double droneDistanceTo (Position pos, double droneFactor) {
		return distanceTo(pos) * droneFactor;
	}
	
	public double truckDistanceTo (Position pos) {
		return distanceTo(pos);
	}
	
	private double distanceTo(Position pos) {
		double distance = Math.sqrt(Math.pow(this.x - pos.getX(), 2) + Math.pow(this.y - pos.getY(), 2));
		
		return distance;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	
}
