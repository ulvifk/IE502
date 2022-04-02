package main;
import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;

public class Objective {
	private GRBModel model;
	private Variables variables;
	private Network network;
	
	public Objective(GRBModel model, Variables variables, Network network, double lowerBound) throws GRBException {
		this.model = model;
		this.variables = variables;
		this.network = network;
		setObjective(lowerBound);
	}
	
	private void setObjective(double lowerBound) throws GRBException {
		GRBLinExpr obj = new GRBLinExpr();
		obj.addTerm(1, this.variables.getTruckCompletion().get(this.network.getEndingDepot()));
		
		this.model.addConstr(obj, GRB.LESS_EQUAL, lowerBound, null);
		this.model.setObjective(obj, GRB.MINIMIZE);
		
	}
}
