package TSP;

import java.util.HashMap;
import main.*;
import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class TSPVariables {
	private HashMap<Node, HashMap<Node, GRBVar>> x;
	private HashMap<Node, GRBVar> u;
	private GRBModel model;
	private Network network;
	
	public TSPVariables(Network network, GRBModel model) throws GRBException {
		this.model = model;
		this.network = network;
		this.x = new HashMap<>();
		this.u = new HashMap<>();
		
		populateX();
		populateU();
	}
	
	private void populateX() throws GRBException {
		for(Node i : this.network.getN()) {
			this.x.put(i, new HashMap<>());
			for(Node j : this.network.getN()) {
				if(i == j) continue;
				this.x.get(i).put(j, this.model.addVar(0, 1, 0, GRB.BINARY, String.format("x[%d][%d]", i.getIndex(), j.getIndex())));
			}
		}
	}
	
	private void populateU() throws GRBException {
		for(Node i : this.network.getN()) {
			this.u.put(i, this.model.addVar(0, this.network.getN().size()+10, 0, GRB.CONTINUOUS, String.format("u[%d]", i.getIndex())));
		}
	}

	public HashMap<Node, HashMap<Node, GRBVar>> getX() {
		return x;
	}

	public HashMap<Node, GRBVar> getU() {
		return u;
	}
}
