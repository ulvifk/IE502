package TSP;

import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import main.*;

public class TSPConstraints {
	private GRBModel model;
	private Network network;
	private TSPVariables variables;
	
	public TSPConstraints(GRBModel model, Network network, TSPVariables variables) throws GRBException {
		this.model = model;
		this.network = network;
		this.variables = variables;
		
		inFLow();
		outFlow();
		MTZ();
	}
	
	private void inFLow() throws GRBException {
		for(Node j : this.network.getN()) {
			GRBLinExpr inFlow = new GRBLinExpr();
			for(Node i : this.network.getN()) {
				if(!this.variables.getX().get(i).containsKey(j)) continue;
				inFlow.addTerm(1, this.variables.getX().get(i).get(j));
			}
			
			this.model.addConstr(inFlow, GRB.EQUAL, 1, String.format("Inflow[%d]", j.getIndex()));
		}
	}
	
	private void outFlow() throws GRBException {
		for(Node i : this.network.getN()) {
			GRBLinExpr outFlow = new GRBLinExpr();
			for(Node j : this.variables.getX().get(i).keySet()) {
				outFlow.addTerm(1, this.variables.getX().get(i).get(j));
			}
			
			this.model.addConstr(outFlow, GRB.EQUAL, 1, String.format("OutFlow[%d]", i.getIndex()));
		}
	}
	
	private void MTZ() throws GRBException {
		for(Node i : this.variables.getX().keySet()) {
			for(Node j : this.variables.getX().get(i).keySet()) {
				if(i == j) continue;
				if(i == this.network.getEndingDepot()) continue;
				
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getU().get(i));
				lhs.addTerm(-1, this.variables.getU().get(j));
				lhs.addTerm(this.network.getN().size()+10, this.variables.getX().get(i).get(j));
				
				this.model.addConstr(lhs, GRB.LESS_EQUAL, this.network.getN().size()+10-1, String.format("MTZ[%d][%d]", i.getIndex(), j.getIndex()));
				
			}
		}
	}
}
