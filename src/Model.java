import gurobi.GRBEnv;
import gurobi.GRBModel;

public class Model {
	GRBModel model;
	
	public Model() {
	GRBEnv env = new GRBEnv();
	this.model = new GRBModel(env);
	}
}
