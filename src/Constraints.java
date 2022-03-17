import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class Constraints {
	private GRBModel model;
	private Network network;
	private Variables variables;
	private int M = 100;
	
	public Constraints(GRBModel model, Network network, Variables variables) {
		this.model = model;
		this.network = network;
		this.variables = variables;
	}
	private boolean isSortie(Node i, Node j, Node k) {
		if(i != j) {
			if(i != k) {
				if(j != k) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void constraint2() throws GRBException { // Visit each customer exactly once.
		for(Node j : this.network.getC()) {
			GRBLinExpr xSum = new GRBLinExpr();
			for(Node i : this.network.getN0()) {
				if(i == j) continue;
				GRBVar x = this.variables.getX().get(i).get(j);
				xSum.addTerm(1, x);
			}
			
			GRBLinExpr ySum = new GRBLinExpr();
			for(Drone v : this.network.getV()) {
				for(Node i : this.network.getN0()) {
					if(i == j) continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(i, j, k)) continue;
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						ySum.addTerm(1, y);
					}
				}
			}
			
			GRBLinExpr expr = new GRBLinExpr();
			expr.add(xSum);
			expr.add(ySum);
			this.model.addConstr(expr, GRB.EQUAL, 1, "Constraint2");
		}
	}

	private void constraint34() throws GRBException { // Leave starting depot once and enter ending depot once	
		GRBLinExpr x0Sum = new GRBLinExpr();
		for(Node j : this.network.getNPlus()) {
			GRBVar x = this.variables.getX().get(this.network.getStartingDepot()).get(j);
			x0Sum.addTerm(1, x);
		}
		
		this.model.addConstr(x0Sum, GRB.EQUAL, 1, "Constraint3");
		
		GRBLinExpr xEndingDepotSum = new GRBLinExpr();
		for(Node i : this.network.getN0()) {
			GRBVar x = this.variables.getX().get(i).get(this.network.getEndingDepot());
			xEndingDepotSum.addTerm(1, x);
		}
		
		this.model.addConstr(xEndingDepotSum, GRB.EQUAL, 1, "Constraint4");
	}
	
	private void constraint5() throws GRBException {// Flow Balance
		for(Node j : this.network.getC()) {
			GRBLinExpr lhs = new GRBLinExpr();
			for(Node i : this.network.getN0()) {
				if(i == j) continue;
				GRBVar x = this.variables.getX().get(i).get(j);
				lhs.addTerm(1, x);
			}
			
			GRBLinExpr rhs = new GRBLinExpr();
			for(Node k : this.network.getNPlus()) {
				if(k == j) continue;
				GRBVar x = this.variables.getX().get(j).get(k);
				rhs.addTerm(1, x);
			}
			
			this.model.addConstr(lhs, GRB.EQUAL, rhs, "Constraint5");
		}
	}

	private void constraint6() throws GRBException {// Each UAV may launched from a launch node at most once
		for(Node i : this.network.getN0()) {
			for(Drone v : this.network.getV()) {
				GRBLinExpr lhs = new GRBLinExpr();
				for(Node j : this.network.getC()) {
					if(i == j) continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(i, j, k)) continue;
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						lhs.addTerm(1, y);
					}
				}
				
				this.model.addConstr(lhs, GRB.LESS_EQUAL, 1, "Constrait6");
			}
		}
	}

	private void constraint7() throws GRBException { // Each UAV may be retrieved at rendezvous point at mos once
		for(Node k : this.network.getNPlus()) {
			for(Drone v : this.network.getV()) {
				GRBLinExpr lhs = new GRBLinExpr();
				for(Node i : this.network.getN0()) {
					if (i == k) continue;
					for(Node j : this.network.getC()) {
						if(!isSortie(i, j, k)) continue;
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						lhs.addTerm(1, y);
					}
				}
				
				this.model.addConstr(lhs, GRB.LESS_EQUAL, 1, "Constraint7");
			}
		}
	}

	private void constraint8() throws GRBException { // Truck has to be assigned to i and k in order to sortie [v,i,j,k] to be used
		for(Drone v : this.network.getV()) {
			for(Node i : this.network.getC()) {
				for(Node j : this.network.getC()) {
					if(i == j) continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(i, j, k)) continue;
						GRBLinExpr lhs = new GRBLinExpr();
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						lhs.addTerm(2, y);
						
						GRBLinExpr rhs = new GRBLinExpr();
						for(Node h : this.network.getN0()) {
							if(h == i) continue;
							GRBVar x = this.variables.getX().get(h).get(i);
							rhs.addTerm(1, x);
						}
						
						for(Node l : this.network.getC()) {
							if(l == k) continue;
							GRBVar x = this.variables.getX().get(l).get(k);
							rhs.addTerm(1, x);
						}
						
						this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint8");
					}
				}
			}
		}
	}

	private void constraint9() throws GRBException { // Same as Constraint8 but for startingdepot.
		for(Drone v : this.network.getV()) {
			for(Node j : this.network.getC()) {
				for(Node k : this.network.getNPlus()) {
					if(!isSortie(this.network.getStartingDepot(), j, k)) continue;
					GRBLinExpr lhs = new GRBLinExpr();
					GRBVar y = this.variables.getY().get(v).get(this.network.getStartingDepot()).get(j).get(k);
					lhs.addTerm(1, y);
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node h : this.network.getN0()) {
						if(h == k) continue;
						GRBVar x = this.variables.getX().get(h).get(k);
						rhs.addTerm(1, x);
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint9");
				}
			}
		}
	}

	private void constraint10() throws GRBException { // MTZ with drone variables
		for(Node i : this.network.getC()) {
			for(Node k : this.network.getNPlus()) {
				if(k == i) continue;
				for(Drone v : this.network.getV()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getU().get(k));
					lhs.addTerm(-1, this.variables.getU().get(i));
					
					GRBLinExpr rhs = new GRBLinExpr();
					GRBLinExpr sumOverY = new GRBLinExpr();
					for(Node j : this.network.getC()) {
						if(!isSortie(i, j, k)) continue;
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						sumOverY.addTerm(1, y);
					}
					
					rhs.addConstant(-1);
					rhs.addConstant(-this.network.getC().size());
					rhs.multAdd(this.network.getC().size() + 2, sumOverY);
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint10");
				}
			}
		}
	}

	private void constraint11() throws GRBException { // MTZ with truck route variables
		for(Node i : this.network.getC()) {
			for(Node j : this.network.getNPlus()) {
				if(i == j) continue;
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getU().get(i));
				lhs.addTerm(-1, this.variables.getU().get(j));
				lhs.addConstant(1);
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addConstant(this.network.getC().size() + 2);
				GRBVar x = this.variables.getX().get(i).get(j);
				rhs.addTerm(this.network.getC().size() + 2, x);
				
				this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Cosntraint11");
			}
		}
	}

	private void constraint12() throws GRBException { // p values
		for(Node i : this.network.getC()) {
			for(Node j : this.network.getC()) {
				if(i == j) continue;
				
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getU().get(i));
				lhs.addTerm(-1, this.variables.getU().get(j));
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addConstant(1);
				rhs.addTerm(-(this.network.getC().size() + 2), this.variables.getP().get(i).get(j));
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint12");
			}
		}
	}
	
	private void constraint13() throws GRBException { // p values
		for(Node i : this.network.getC()) {
			for(Node j : this.network.getC()) {
				if(i == j) continue;
				
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getU().get(i));
				lhs.addTerm(-1, this.variables.getU().get(j));
				
				GRBLinExpr rhs = new GRBLinExpr();
				GRBLinExpr expr = new GRBLinExpr();
				expr.addConstant(1);
				expr.addTerm(-1, this.variables.getP().get(i).get(j));
				
				rhs.addConstant(-1);
				rhs.multAdd(this.network.getC().size() + 2, expr);
				
				this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint13");			
			}
		}
	}
	
	private void constraint14() throws GRBException { // p values
		for(Node i : this.network.getC()) {
			for(Node j : this.network.getC()) {
				if(i == j) continue;
				
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getP().get(i).get(j));
				lhs.addTerm(1, this.variables.getP().get(j).get(i));
				
				this.model.addConstr(lhs, GRB.EQUAL, 1, "Constraint14");
			}
		}
	}

	private void constraint15() throws GRBException {
		for(Node i : this.network.getN0()) {
			for(Node k : this.network.getNPlus()) {
				if(i == k) continue;
				for(Node l : this.network.getC()) {
					if(!isSortie(i, i, k)) continue;
					for(Drone v : this.network.getV()) {
						GRBLinExpr lhs = new GRBLinExpr();
						lhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(l));
						
						GRBLinExpr rhs = new GRBLinExpr();
						GRBLinExpr expr = new GRBLinExpr();
						expr.addConstant(3);
						
						GRBLinExpr sum1 = new GRBLinExpr();
						for(Node j : this.network.getC()) {
							if(j == l) continue;
							if(!isSortie(i, j, k));
							GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
							sum1.addTerm(1, y);
						}
						
						GRBLinExpr sum2 = new GRBLinExpr();
						for(Node m : this.network.getC()) {
							if(m == i || m == k || m == l) continue;
							for(Node n : this.network.getNPlus()) {
								if(!isSortie(l, m, n)) continue;
								if(n == i || n == k) continue;
								GRBVar y = this.variables.getY().get(v).get(l).get(m).get(n);
								sum2.addTerm(1, y);
							}
						}
						
						expr.multAdd(-1, sum1);
						expr.multAdd(-1, sum2);
						expr.addTerm(-1, this.variables.getP().get(i).get(l));
						
						rhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
						rhs.multAdd(-this.M, sum2);
						
						this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint15");
					}
				}
			}
		}
	}
}
