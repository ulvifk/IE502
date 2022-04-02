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
		
		if(model.get(GRB.IntAttr.Status) == GRB.INFEASIBLE) {
			model.computeIIS();
			
			for(GRBConstr c : model.getConstrs()) {
				if(c.get(GRB.IntAttr.IISConstr) == 1) {
					System.out.println(c.get(GRB.StringAttr.ConstrName));
				}
			}
		}
		this.objectiveValue = model.get(GRB.DoubleAttr.ObjVal);
		
//		TSPWriteData write = new TSPWriteData(network, variables, folderLocation);
		
		model.dispose();
		env.dispose();
	}
}
