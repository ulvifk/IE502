import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;

public class Objective {
	private GRBModel model;
	private Variables variables;
	private Network network;
	
	public Objective(GRBModel model, Variables variables, Network network) throws GRBException {
		this.model = model;
		this.variables = variables;
		this.network = network;
		setObjective();
	}
	
	private void setObjective() throws GRBException {
		GRBLinExpr obj = new GRBLinExpr();
		obj.addTerm(1, this.variables.getTruckCompletion().get(this.network.getEndingDepot()));
		
		this.model.setObjective(obj, GRB.MINIMIZE);
	}
}
