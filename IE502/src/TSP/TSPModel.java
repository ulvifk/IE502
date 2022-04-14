package TSP;
import java.io.IOException;

import gurobi.GRB;
import gurobi.GRBConstr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBModel;
import main.*;

public class TSPModel {
	public double objectiveValue;
	
	public TSPModel(Network network, String folderLocation) throws GRBException, IOException {
		GRBEnv env =  new GRBEnv();
		GRBModel model = new GRBModel(env);
		
		TSPVariables variables = new TSPVariables(network, model);
		TSPConstraints constraints = new TSPConstraints(model, network, variables);
		TSPObjective objective = new TSPObjective(model, network, variables);
		
		model.optimize();
		
		TSPWriteData write = new TSPWriteData(network, variables, folderLocation);
		this.objectiveValue = model.get(GRB.DoubleAttr.ObjVal);
		
		model.dispose();
		env.dispose();
	}
}
