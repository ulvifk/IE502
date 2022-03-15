import java.util.HashMap;

import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class Variables {
	private HashMap<Node, HashMap<Node, GRBVar>> x;
	private HashMap<Node, HashMap<Node, GRBVar>> p;
	private HashMap<Drone, HashMap<Node, HashMap<Node, HashMap<Node, GRBVar>>>> y;
	private HashMap<Node, GRBVar> truckArrival;
	private HashMap<Node, GRBVar> truckService;
	private HashMap<Node, GRBVar> truckCompletion;
	private HashMap<Drone, HashMap<Node, GRBVar>> droneArrival;
	private HashMap<Drone, HashMap<Node, GRBVar>> droneCompletion;
	
	private HashMap<Drone, HashMap<Drone, HashMap<Node, GRBVar>>> zTwoDroneRetrieval;
	private HashMap<Drone, HashMap<Node, GRBVar>> zDroneRetrievedSecond;
	private HashMap<Drone, HashMap<Node, GRBVar>> zDroneRetrievedFirst;
	
	private HashMap<Drone, HashMap<Drone, HashMap<Node, GRBVar>>> zTwoDroneLaunch;
	private HashMap<Drone, HashMap<Node, GRBVar>> zDroneLaunchedSecond;
	private HashMap<Drone, HashMap<Node, GRBVar>> zDroneLaunchedFirst;
	
	private HashMap<Drone, HashMap<Drone, HashMap<Node, GRBVar>>> zFirstLaunchSecondRetrieval;
	private HashMap<Drone, HashMap<Drone, HashMap<Node, GRBVar>>> zFirstRetrievalSecondLaunch;
	
	private HashMap<Node, GRBVar> u;
	
	private Network network;
	private GRBModel model;
	
	public Variables(Network network, GRBModel model) {
		this.x = new HashMap();
		this.p = new HashMap();
		this.y = new HashMap();
		this.truckArrival = new HashMap();
		this.truckService = new HashMap();
		this.truckCompletion = new HashMap();
		this.droneArrival = new HashMap();
		this.droneCompletion = new HashMap();
		this.zTwoDroneRetrieval = new HashMap();
		this.zDroneRetrievedSecond = new HashMap();
		this.zDroneRetrievedFirst = new HashMap();
		this.zTwoDroneLaunch = new HashMap();
		this.zDroneLaunchedSecond = new HashMap();
		this.zDroneLaunchedFirst = new HashMap();
		this.zFirstLaunchSecondRetrieval = new HashMap();
		this.zFirstRetrievalSecondLaunch = new HashMap();
		this.network = network;
		this.model = model;
	}
	
	private void populateX() throws GRBException {
		for(Node i : this.network.getN0()) {
			this.x.put(i, new HashMap<Node, GRBVar>()); 
			for(Node j : this.network.getNPlus()) {
				if(i == j) continue;
				GRBVar xij = this.model.addVar(0, 1, 0, GRB.BINARY, "x[" + i.getIndex() + "][" + j.getIndex() + "]");
				this.x.get(i).put(j, xij);
			}
		}
	}
	
	private void populateP() throws GRBException {
		for(Node i : this.network.getN0()) {
			this.p.put(i, new HashMap<Node, GRBVar>());
			for(Node j : this.network.getC()) {
				if(i == j) continue;
				GRBVar pij = this.model.addVar(0, 1, 0, GRB.BINARY, "p_ij");
				this.p.get(i).put(j, pij);
			}
		}
	}
	
}
