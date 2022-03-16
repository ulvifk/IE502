import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBModel;

public class Model {
	GRBModel model;
	
	public Model(Network network) throws GRBException {
	GRBEnv env = new GRBEnv();
	this.model = new GRBModel(env);
	
	Variables variables = new Variables(network, model);
	}
}
