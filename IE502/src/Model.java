import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

import gurobi.GRB;
import gurobi.GRBConstr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class Model {
	GRBModel model;
	Variables variables;
	
	public Model(Network network, String folderLocation) throws GRBException, IOException {
		File folder = new File(folderLocation);
		if(!Files.exists(Paths.get(folderLocation))) {
			folder.mkdir();
		}
		
		GRBEnv env = new GRBEnv();
		this.model = new GRBModel(env);

		model.getEnv().set(GRB.StringParam.LogFile, folderLocation + "\\logFile.txt");
		
		this.variables = new Variables(network, model);
		Objective obj = new Objective(model, variables, network);
		Constraints constraints = new Constraints(model, network, variables);
		this.model.optimize();
		
		this.model.write("Model.lp");
		if(this.model.get(GRB.IntAttr.Status) == GRB.INFEASIBLE) {
			this.model.computeIIS();
			
			for(GRBConstr c : this.model.getConstrs()) {
				if(c.get(GRB.IntAttr.IISConstr) == 1) {
					System.out.println(c.get(GRB.StringAttr.ConstrName));
				}
			}
		}
		
		
		WriteData writeData = new WriteData(network, variables, folderLocation);
		this.model.write(folderLocation + "\\model.lp");
		
		this.model.dispose();
		env.dispose();
	}
	
	private void printSolution(Network network, GRBModel mode) throws GRBException {
		for(Drone v : this.variables.getY().keySet()) {
			for(Node i : this.variables.getY().get(v).keySet()) {
				for(Node j : this.variables.getY().get(v).get(i).keySet()) {
					for(Node k : this.variables.getY().get(v).get(i).get(j).keySet()) {
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						double yValue = y.get(GRB.DoubleAttr.X);
						if(yValue > 0.5) {
							System.out.printf("y[%d][%d][%d][%d]: %f \n", v.getIndex(), i.getIndex(), j.getIndex(), k.getIndex(), yValue);
						}
					}
				}
			}
		}
		
		for(Node i : network.getNPlus()) {
			if(this.variables.getU().get(i).get(GRB.DoubleAttr.X) > 0.5) {
				System.out.printf("u[%d]: %f \n", i.getIndex(), this.variables.getU().get(i).get(GRB.DoubleAttr.X));
			}
		}
		
		for(Node i : network.getNPlus()) {
			if(this.variables.getU().get(i).get(GRB.DoubleAttr.X) > 0.5) {
				System.out.printf("u[%d]: %f \n", i.getIndex(), this.variables.getU().get(i).get(GRB.DoubleAttr.X));
			}
		}
		
		System.out.println();
		
		for(Node i : network.getN0()) {
			for(Node j : network.getC()) {
				if(i == j) continue;
				if(this.variables.getP().get(i).get(j).get(GRB.DoubleAttr.X) > 0.5) {
					System.out.printf("p[%d][%d]: %f \n", i.getIndex(), j.getIndex(), this.variables.getP().get(i).get(j).get(GRB.DoubleAttr.X));
				}
			}
		}
	}

}
