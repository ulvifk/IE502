package TSP;

import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import main.*;

public class TSPObjective {
	private GRBModel model;
	private Network network;
	private TSPVariables variables;
	
	public TSPObjective(GRBModel model, Network network, TSPVariables variables) throws GRBException {
		this.model = model;
		this.network = network;
		this.variables = variables;
		
		setObjective();
	}
	
	private void setObjective() throws GRBException {
		GRBLinExpr obj = new GRBLinExpr();
		
		for(Node i : this.variables.getX().keySet()) {
			for(Node j : this.variables.getX().get(i).keySet()) {
				obj.addTerm(i.getTruckDistanceTo(j), this.variables.getX().get(i).get(j));
			}
		}
		
		this.model.setObjective(obj, GRB.MINIMIZE);
	}
}
