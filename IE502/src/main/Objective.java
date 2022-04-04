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
		obj.addTerm(this.network.sigmaTime, this.variables.getTruckCompletion().get(this.network.getEndingDepot()));
		
		GRBLinExpr penaltySum = new GRBLinExpr();
		for(Node j : this.network.getC()) {
			penaltySum.addConstant(j.getPenalty());
			penaltySum.addTerm(-j.getPenalty(), this.variables.getCustomerServiceVar().get(j));
		}
		
		obj.multAdd(this.network.sigmaPenalty, penaltySum);
		
		this.model.setObjective(obj, GRB.MINIMIZE);
		
	}
}
