package main;
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
	
	private HashMap<Drone, GRBVar> droneUsageVar;
	private HashMap<Node, GRBVar> customerServiceVar;
	
	private Network network;
	private GRBModel model;
	
	public Variables(Network network, GRBModel model) throws GRBException {
		this.x = new HashMap();
		this.p = new HashMap();
		this.y = new HashMap();
		this.u = new HashMap<>();
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
		
		this.droneUsageVar = new HashMap<>();
		this.customerServiceVar = new HashMap<>();
		this.network = network;
		this.model = model;
		
		populateX();
		populateP();
		populateY();
		populateTruckArrival();
		populateTruckService();
		populateTruckCompletion();
		populateDroneArrival();
		populateDroneCompletion();
		populateTwoDroneRetrieval();
		populateDroneRetrievedSecond();
		populateDroneRetrievedFirst();
		populateTwoDroneLaunch();
		populateDroneLaunchedSecond();
		populateDroneLaunchedFirst();
		populateFirstLaunchSecondRetrieval();
		populateFirstRetrievalSecondLaunch();
		populateU();
		
		populateDroneUsageVar();
		populateCustomerServiceVar();
	}
	
	private void populateDroneUsageVar() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.droneUsageVar.put(v, this.model.addVar(0, 1, 0, GRB.BINARY, String.format("droneUsageVar[%d]", v.getIndex())));
		}
	}
	
	private void populateCustomerServiceVar() throws GRBException {
		for(Node i : this.network.getC()) {
			this.customerServiceVar.put(i, this.model.addVar(0, 1, 0, GRB.BINARY, String.format("customerServiceVar[%d]", i.getIndex())));
		}
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
				GRBVar pij = this.model.addVar(0, 1, 0, GRB.BINARY, "p_" +i.getIndex() + j.getIndex());
				this.p.get(i).put(j, pij);
			}
		}
	}
	
	private boolean isSortie(Drone v, Node i, Node j, Node k) {
		if(i == j) return false;
		if(i == k) return false;
		if(j == k) return false;
		if(!this.network.getCDrone().get(v).contains(j)) return false;
		
		return true;
	}
	
	// Note: It is assumed that every sortie is viable.
	private void populateY() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.y.put(v, new HashMap());
			for(Node i : this.network.getN0()) {
				this.y.get(v).put(i, new HashMap());
				for(Node j : this.network.getCDrone().get(v)) {
					if(i == j) continue;
					this.y.get(v).get(i).put(j, new HashMap());
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						GRBVar y = this.model.addVar(0, 1, 0, GRB.BINARY, "y_" + v.getIndex() + i.getIndex() + j.getIndex() + k.getIndex());
						this.y.get(v).get(i).get(j).put(k, y);
					}
				}
			}
		}
	}
	
	private void populateTruckArrival() throws GRBException {
		for(Node i : this.network.getN()) {
			GRBVar t = this.model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "TruckArrival_" + i.getIndex());
			this.truckArrival.put(i, t);
		}
	}
	
	private void populateTruckService() throws GRBException {
		for(Node i : this.network.getNPlus()) {
			GRBVar t = this.model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "TruckService_" + i.getIndex());
			this.truckService.put(i, t);
		}
		
		this.truckService.put(this.network.getStartingDepot(), this.model.addVar(0, 0, 0, GRB.CONTINUOUS, "TruckService_" + 0));
	}
	
	private void populateTruckCompletion() throws GRBException {
		for(Node i : this.network.getN()) {
			GRBVar t = this.model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "TruckCompletion_" + i.getIndex());
			this.truckCompletion.put(i, t);
		}
	}
	
	private void populateDroneArrival() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.droneArrival.put(v, new HashMap());
			for(Node i : this.network.getN()) {
				GRBVar t = this.model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "DroneArrival_" + v.getIndex() + i.getIndex());
				this.droneArrival.get(v).put(i, t);
			}
		}
	}
	
	private void populateDroneCompletion() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.droneCompletion.put(v, new HashMap());
			for(Node i : this.network.getN()) {
				GRBVar t = this.model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "DroneCompletion_" + v.getIndex() + i.getIndex());
				this.droneCompletion.get(v).put(i, t);
			}
		}
	}
	
	private void populateTwoDroneRetrieval() throws GRBException {
		for(Drone v1 : this.network.getV()) {
			this.zTwoDroneRetrieval.put(v1, new HashMap());
			for(Drone v2 : this.network.getV()) {
				if (v1 == v2) continue;
				this.zTwoDroneRetrieval.get(v1).put(v2, new HashMap());
				for(Node k : this.network.getNPlus()) {
					GRBVar z = this.model.addVar(0, 1, 0, GRB.BINARY, "TwoDroneRetrieval_" + v1.getIndex() + v2.getIndex() + k.getIndex());
					this.zTwoDroneRetrieval.get(v1).get(v2).put(k, z);
				}
			}
		}
	}
	
	private void populateDroneRetrievedSecond() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.zDroneRetrievedSecond.put(v, new HashMap());
			for(Node k : this.network.getNPlus()) {
				GRBVar z = this.model.addVar(0, 1, 0, GRB.BINARY, "DroneRetrievedSecond" + v.getIndex() + k.getIndex());
				this.zDroneRetrievedSecond.get(v).put(k, z);
			}
		}
	}
	
	private void populateDroneRetrievedFirst() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.zDroneRetrievedFirst.put(v, new HashMap());
			for(Node k : this.network.getNPlus()) {
				GRBVar z = this.model.addVar(0, 1, 0, GRB.BINARY, "DroneRetrievedFirst" + v.getIndex() + k.getIndex());
				this.zDroneRetrievedFirst.get(v).put(k, z);
			}
		}
		
		for(Drone v : this.network.getV()) {
			this.zDroneRetrievedFirst.get(v).put(this.network.getEndingDepot(), this.model.addVar(0, 0, 0, GRB.CONTINUOUS, "DroneRetrievedFirst" + v.getIndex() + 0));
		}
	}
	
	private void populateTwoDroneLaunch() throws GRBException {
		for(Drone v1 : this.network.getV()) {
			this.zTwoDroneLaunch.put(v1, new HashMap());
			for(Drone v2 : this.network.getV()) {
				if (v1 == v2) continue;
				this.zTwoDroneLaunch.get(v1).put(v2, new HashMap());
				for(Node k : this.network.getN0()) {
					GRBVar z = this.model.addVar(0, 1, 0, GRB.BINARY, "TwoDroneLaunch_" + v1.getIndex() + v2.getIndex() + k.getIndex());
					this.zTwoDroneLaunch.get(v1).get(v2).put(k, z);
				}
			}
		}
	}
	
	private void populateDroneLaunchedSecond() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.zDroneLaunchedSecond.put(v, new HashMap());
			for(Node k : this.network.getN0()) {
				GRBVar z = this.model.addVar(0, 1, 0, GRB.BINARY, "DroneLaunchedSecond" + v.getIndex() + k.getIndex());
				this.zDroneLaunchedSecond.get(v).put(k, z);
			}
		}
	}
	
	private void populateDroneLaunchedFirst() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.zDroneLaunchedFirst.put(v, new HashMap());
			for(Node k : this.network.getN0()) {
				GRBVar z = this.model.addVar(0, 1, 0, GRB.BINARY, "DroneLaunchedFirst" + v.getIndex() + k.getIndex());
				this.zDroneLaunchedFirst.get(v).put(k, z);
			}
		}
	}
	
	private void populateFirstLaunchSecondRetrieval() throws GRBException {
		for(Drone v1 : this.network.getV()) {
			this.zFirstLaunchSecondRetrieval.put(v1, new HashMap());
			for(Drone v2 : this.network.getV()) {
				if(v1 == v2) continue;
				this.zFirstLaunchSecondRetrieval.get(v1).put(v2, new HashMap());
				for(Node i : this.network.getC()) {
					GRBVar z = this.model.addVar(0, 1, 0, GRB.BINARY, "FirstLaunchSecondRetrieval" +v1.getIndex() + v2.getIndex() + i.getIndex());
					this.zFirstLaunchSecondRetrieval.get(v1).get(v2).put(i, z);
				}
			}
		}
	}
	
	private void populateFirstRetrievalSecondLaunch() throws GRBException {
		for(Drone v1 : this.network.getV()) {
			this.zFirstRetrievalSecondLaunch.put(v1, new HashMap());
			for(Drone v2 : this.network.getV()) {
				if(v1 == v2) continue;
				this.zFirstRetrievalSecondLaunch.get(v1).put(v2, new HashMap());
				for(Node i : this.network.getC()) {
					GRBVar z = this.model.addVar(0, 1, 0, GRB.BINARY, "FirstRetrievalSecondLaunch" +v1.getIndex() + v2.getIndex() + i.getIndex());
					this.zFirstRetrievalSecondLaunch.get(v1).get(v2).put(i, z);
				}
			}
		}
	}
	
	private void populateU() throws GRBException {
		for(Node i : this.network.getNPlus()) {
			GRBVar u = this.model.addVar(1, this.network.getC().size() + 2, 0, GRB.CONTINUOUS, "u_" + i.getIndex());
			this.u.put(i, u);
		}
	}

	
	public HashMap<Node, HashMap<Node, GRBVar>> getX() {
		return x;
	}

	public HashMap<Node, HashMap<Node, GRBVar>> getP() {
		return p;
	}

	public HashMap<Drone, HashMap<Node, HashMap<Node, HashMap<Node, GRBVar>>>> getY() {
		return y;
	}

	public HashMap<Node, GRBVar> getTruckArrival() {
		return truckArrival;
	}

	public HashMap<Node, GRBVar> getTruckService() {
		return truckService;
	}

	public HashMap<Node, GRBVar> getTruckCompletion() {
		return truckCompletion;
	}

	public HashMap<Drone, HashMap<Node, GRBVar>> getDroneArrival() {
		return droneArrival;
	}

	public HashMap<Drone, HashMap<Node, GRBVar>> getDroneCompletion() {
		return droneCompletion;
	}

	public HashMap<Drone, HashMap<Drone, HashMap<Node, GRBVar>>> getzTwoDroneRetrieval() {
		return zTwoDroneRetrieval;
	}

	public HashMap<Drone, HashMap<Node, GRBVar>> getzDroneRetrievedSecond() {
		return zDroneRetrievedSecond;
	}

	public HashMap<Drone, HashMap<Node, GRBVar>> getzDroneRetrievedFirst() {
		return zDroneRetrievedFirst;
	}

	public HashMap<Drone, HashMap<Drone, HashMap<Node, GRBVar>>> getzTwoDroneLaunch() {
		return zTwoDroneLaunch;
	}

	public HashMap<Drone, HashMap<Node, GRBVar>> getzDroneLaunchedSecond() {
		return zDroneLaunchedSecond;
	}

	public HashMap<Drone, HashMap<Node, GRBVar>> getzDroneLaunchedFirst() {
		return zDroneLaunchedFirst;
	}

	public HashMap<Drone, HashMap<Drone, HashMap<Node, GRBVar>>> getzFirstLaunchSecondRetrieval() {
		return zFirstLaunchSecondRetrieval;
	}

	public HashMap<Drone, HashMap<Drone, HashMap<Node, GRBVar>>> getzFirstRetrievalSecondLaunch() {
		return zFirstRetrievalSecondLaunch;
	}

	public HashMap<Node, GRBVar> getU() {
		return u;
	}

	public HashMap<Drone, GRBVar> getDroneUsageVar() {
		return droneUsageVar;
	}

	public HashMap<Node, GRBVar> getCustomerServiceVar() {
		return customerServiceVar;
	}

	
}
