package main;
import java.util.Locale;

import gurobi.GRB;
import gurobi.GRBConstr;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class Constraints {
	private GRBModel model;
	private Network network;
	private Variables variables;
	private double M; // Sum of all edges / 5
	
	
	public Constraints(GRBModel model, Network network, Variables variables, double lowerBound) throws GRBException {
		this.model = model;
		this.network = network;
		this.variables = variables;
		this.M = lowerBound;
//		findM();
		
		depotP();
		startingDepotTruckTimes();
		startingDepotDroneOrderDoesNotMatter();
		droneUsageSymmetryBreaking();
		visitOrderSymmetryBreaking();
		
		customerServiceLink();
		droneUsageLink();
		truckCapacity();
		
//		constraint2();
		constraint3and4();
		constraint5();
		constraint6();
		constraint7();
		constraint8();
		constraint9();
		constraint10();
		constraint11();
		constraint12();
		constraint13();
		constraint14();
		constraint15();
		constraint16();
		constraint17();
		constraint18();
		constraint19();
		constraint20();
		constraint21();
		constraint22();
		constraint23();
		constraint24();
		constraint25();
		constraint26();
		constraint27();
		constraint28();
		constraint29();
		constraint30();
		constraint31();
		constraint32();
		constraint33();
		constraint34();
		constraint35();
		constraint36();
		constraint37();
		constraint38();
		constraint39();
		constraint40();
		constraint41();
		constraint42();
		constraint43();
		constraint44();
		constraint45();
		constraint46();
		constraint47();
		constraint48();
		constraint49();
		constraint50();
		constraint51();
		constraint52();
		constraint53();
		constraint54();
		constraint55();
	}
	
	private void droneUsageSymmetryBreaking() throws GRBException {
		for(int v = 1; v<this.network.getV().size(); v++) {
			GRBVar v1 = this.variables.getDroneUsageVar().get(this.network.getV().get(v-1));
			GRBVar v2 = this.variables.getDroneUsageVar().get(this.network.getV().get(v));
			
			this.model.addConstr(v1, GRB.GREATER_EQUAL, v2, null);
		}
	}
	
	private void visitOrderSymmetryBreaking() throws GRBException {
		for(int v = 1; v<this.network.getV().size(); v++) {
			Drone v1 = this.network.getV().get(v-1);
			Drone v2 = this.network.getV().get(v);
			for(Node i : this.network.getN0()) {
				for(Node j : this.network.getCDrone().get(v1)) {
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v1, i, j, k)) continue;
						
						GRBLinExpr lhs = new GRBLinExpr();
						for(Node l : this.network.getN0()) {
							for(Node m : this.network.getCDrone().get(v1)) {
								if(m.getIndex() >= j.getIndex()) continue;
								if(l == m) continue;
								for(Node n : this.network.getNPlus()) {
									if(n == m) continue;
									if(!isSortie(v2, l, m, n)) continue;
									lhs.addTerm(1, this.variables.getY().get(v1).get(l).get(m).get(n));
								}
							}
						}
						
						GRBLinExpr rhs = new GRBLinExpr();
						rhs.addTerm(1, this.variables.getY().get(v2).get(i).get(j).get(k));
						
						this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, null);
					}
				}
			}
		}
	}
	
	private void findM() {
		double sum = 0;
		for(Node i : this.network.getN()) {
			for(Node j : this.network.getN()) {
				if(i == j) continue;
				sum += i.getTruckDistanceTo(j);
			}
		}
		
		this.M = sum / 2;
	}

	private boolean isSortie(Drone v, Node i, Node j, Node k) {
		if(i == j) return false;
		if(i == k) return false;
		if(j == k) return false;
		if(!this.network.getCDrone().get(v).contains(j)) return false;
		
		double totalDistance = i.getDroneDistanceTo(j, v) + j.getDroneDistanceTo(k, v);
		if(totalDistance > v.getEndurance()) return false;
		
		return true;
	}
	
	private void depotP() throws GRBException {
		for(Node j : this.network.getC()) {
			GRBLinExpr lhs = new GRBLinExpr();
			lhs.addTerm(1, this.variables.getP().get(this.network.getStartingDepot()).get(j));
			
			this.model.addConstr(lhs, GRB.EQUAL, 1, null);
		}
	}
	
	private void startingDepotTruckTimes() throws GRBException {
		this.model.addConstr(this.variables.getTruckArrival().get(this.network.getStartingDepot()), GRB.EQUAL, 0, null);
		this.model.addConstr(this.variables.getTruckService().get(this.network.getStartingDepot()), GRB.EQUAL, 0, null);	
	}
	
	private void startingDepotDroneOrderDoesNotMatter() throws GRBException {
		for(Drone v : this.network.getV()) {
			this.model.addConstr(this.variables.getzDroneRetrievedFirst().get(v).get(this.network.getEndingDepot()), GRB.EQUAL, 0, null);
		}
	}
	
	private void droneUsageLink() throws GRBException {
		for(Drone v : this.network.getV()) {
			GRBLinExpr lhs = new GRBLinExpr();
			for(Node i : this.network.getN0()) {
				for(Node j : this.network.getCDrone().get(v)) {
					if(i == j) continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						lhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
				}
			}
			
			double bigM = Math.pow(this.network.getN().size(), 3) * this.network.getV().size();
			GRBLinExpr rhs = new GRBLinExpr();
			rhs.addTerm(bigM, this.variables.getDroneUsageVar().get(v));
			
			this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Drone usage");
		}
	}
	
	private void truckCapacity() throws GRBException {
		// Upper bound to capacity. Always active. Becomes redundant if #UAV>2
		GRBLinExpr usedCapacity = new GRBLinExpr();
		for(Node j : this.network.getC()) {
			usedCapacity.addTerm(j.getParcelVolume(), this.variables.getCustomerServiceVar().get(j));
		}
		
		this.model.addConstr(usedCapacity, GRB.LESS_EQUAL, this.network.getVehicle().getCapacity(), "Vehicle Capacity");
		
		// if more than 2 drones used, some additional capacity will be used.
		double bigM = 100000;
		
		// Either less than 2 drones
		GRBLinExpr numberOfDrones = new GRBLinExpr();
		for(Drone v : this.network.getV()) {
			numberOfDrones.addTerm(1, this.variables.getDroneUsageVar().get(v));
		}
		GRBVar k = this.model.addVar(0, 1, 0, GRB.BINARY, null);
		
		GRBLinExpr rhs = new GRBLinExpr();
		rhs.addConstant(2);
		rhs.addTerm(bigM, k);
		this.model.addConstr(numberOfDrones, GRB.LESS_EQUAL, rhs, "Less than 2 drones");
		
		// Or tighter capacity constraint
		GRBLinExpr tighterRhs = new GRBLinExpr();
		
		GRBLinExpr numberOfDronesMinus2 = new GRBLinExpr();
		for(Drone v : this.network.getV()) {
			numberOfDronesMinus2.addTerm(1, this.variables.getDroneUsageVar().get(v));
		}
		numberOfDronesMinus2.addConstant(-2);
		
		tighterRhs.addConstant(this.network.getVehicle().getCapacity());
		tighterRhs.multAdd(-this.network.getVehicle().getCapacity() * this.network.getDroneVolumePercentage(), numberOfDronesMinus2);
		tighterRhs.addConstant(bigM);
		tighterRhs.addTerm(-bigM, k);
		
		this.model.addConstr(usedCapacity, GRB.LESS_EQUAL, tighterRhs, "Tighter Capacity");
	}
	
	private void customerServiceLink() throws GRBException {
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
						if(!isSortie(v, i, j, k)) continue;
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						ySum.addTerm(1, y);
					}
				}
			}
			
			GRBLinExpr expr = new GRBLinExpr();
			expr.add(xSum);
			expr.add(ySum);
			this.model.addConstr(expr, GRB.EQUAL, this.variables.getCustomerServiceVar().get(j), "Customer Service Link " + j.getIndex());
		}
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
						if(!isSortie(v, i, j, k)) continue;
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						ySum.addTerm(1, y);
					}
				}
			}
			
			GRBLinExpr expr = new GRBLinExpr();
			expr.add(xSum);
			expr.add(ySum);
			this.model.addConstr(expr, GRB.EQUAL, 1, "Constraint2_" + j.getIndex());
		}
	}

	private void constraint3and4() throws GRBException { // Leave starting depot once and enter ending depot once	
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
						if(!isSortie(v, i, j, k)) continue;
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
						if(!isSortie(v, i, j, k)) continue;
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
						if(!isSortie(v, i, j, k)) continue;
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
					if(!isSortie(v, this.network.getStartingDepot(), j, k)) continue;
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
					GRBLinExpr expr = new GRBLinExpr();
					expr.addConstant(1);
					for(Node j : this.network.getC()) {
						if(!isSortie(v, i, j, k)) continue;
						expr.addTerm(-1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
					
					rhs.addConstant(1);
					rhs.multAdd(-(this.network.getN().size()), expr);
					
					GRBConstr c = this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint10");
					c.set(GRB.IntAttr.Lazy, 1);
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
				GRBLinExpr expr = new GRBLinExpr();
				expr.addConstant(1);
				expr.addTerm(-1, this.variables.getX().get(i).get(j));
				rhs.multAdd(this.network.getN().size(), expr);
				
				GRBConstr c = this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Cosntraint11");
				c.set(GRB.IntAttr.Lazy, 1);
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
				rhs.addTerm(-this.network.getN().size(), this.variables.getP().get(i).get(j));
				
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
				rhs.multAdd(this.network.getN().size(), expr);
				
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

	private void constraint15() throws GRBException { // prevent overlaping of sorties
		for(Node i : this.network.getN0()) {
			for(Node k : this.network.getNPlus()) {
				if(i == k) continue;
				for(Node l : this.network.getC()) {
					if(l == i || l == k) continue;
					for(Drone v : this.network.getV()) {
						GRBLinExpr lhs = new GRBLinExpr();
						lhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(l));
						
						GRBLinExpr rhs = new GRBLinExpr();
						GRBLinExpr expr = new GRBLinExpr();
						expr.addConstant(3);
						
						GRBLinExpr sum1 = new GRBLinExpr();
						for(Node j : this.network.getC()) {
							if(j == l) continue;
							if(!isSortie(v, i, j, k)) continue;
							GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
							sum1.addTerm(1, y);
						}
						
						GRBLinExpr sum2 = new GRBLinExpr();
						for(Node m : this.network.getC()) {
							if(m == i || m == k || m == l) continue;
							for(Node n : this.network.getNPlus()) {
								if(!isSortie(v, l, m, n)) continue;
								if(n == i || n == k) continue;
								GRBVar y = this.variables.getY().get(v).get(l).get(m).get(n);
								sum2.addTerm(1, y);
							}
						}
						
						expr.multAdd(-1, sum1);
						expr.multAdd(-1, sum2);
						expr.addTerm(-1, this.variables.getP().get(i).get(l));
						
						rhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
						rhs.multAdd(-this.M, expr);
						
						GRBConstr c = this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint15");
						c.set(GRB.IntAttr.Lazy, 1);
					}
				}
			}
		}
	}

	private void constraint16() throws GRBException { // if drone arrives later than service time of the truck.
		for(Drone v : this.network.getV()) {
			for(Node i : this.network.getN0()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(i));
				
				GRBLinExpr rhs = new GRBLinExpr();
				GRBLinExpr expr = new GRBLinExpr();
				expr.addConstant(1);
				for(Node j : this.network.getC()) {
					if(j == i )continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						expr.addTerm(-1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
				}
				
				rhs.addTerm(1, this.variables.getDroneArrival().get(v).get(i));
				rhs.addConstant(i.getDroneLaunchTime(v));
				rhs.multAdd(-M, expr);
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint16");
			}
		}
	}

	private void constraint17() throws GRBException { // If drone launched before truck serves
		for(Drone v : this.network.getV()) {
			for(Node i : this.network.getN0()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(i));
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addTerm(1, this.variables.getTruckArrival().get(i));
				rhs.addConstant(i.getDroneLaunchTime(v));
				rhs.addConstant(-M);
				rhs.addTerm(M, this.variables.getzDroneLaunchedFirst().get(v).get(i));
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint17");
			}
		}
	}

	private void constraint18() throws GRBException { // Drone launched after truck serves.
		for(Drone v : this.network.getV()) {
			for(Node i : this.network.getN0()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(i));
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addTerm(1, this.variables.getTruckService().get(i));
				rhs.addConstant(i.getDroneLaunchTime(v));
				rhs.addConstant(-M);
				rhs.addTerm(M, this.variables.getzDroneLaunchedSecond().get(v).get(i));
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint18");
			}
		}
	}
	
	private void constraint19() throws GRBException { // Case two drone launch
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node i : this.network.getN0()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(i));
					
					GRBLinExpr rhs = new GRBLinExpr();
					rhs.addTerm(1, this.variables.getDroneCompletion().get(v2).get(i));
					rhs.addConstant(i.getDroneLaunchTime(v));
					rhs.addConstant(-M);
					rhs.addTerm(M, this.variables.getzTwoDroneLaunch().get(v2).get(v).get(i));
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint 19");
				}
			}
		}
	}

	private void constraint20() throws GRBException { // First drone retrieved then second drone launched
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node i : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getDroneCompletion().get(v2).get(i));
					
					GRBLinExpr rhs = new GRBLinExpr();
					rhs.addTerm(1, this.variables.getDroneArrival().get(v).get(i));
					rhs.addConstant(i.getDroneLaunchTime(v2));
					rhs.addConstant(-M);
					rhs.addTerm(M, this.variables.getzFirstRetrievalSecondLaunch().get(v).get(v2).get(i));
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint20");
				}
			}
		}
		
	}
	
	private void constraint21() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Node j : this.network.getC()) {
				for(Node i : this.network.getN0()) {
					if(i == j) continue;
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getDroneArrival().get(v).get(j));
					
					GRBLinExpr rhs = new GRBLinExpr();
					GRBLinExpr expr = new GRBLinExpr();
					expr.addConstant(1);
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						expr.addTerm(-1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
					
					rhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(i));
					rhs.addConstant(i.getDroneDistanceTo(j, v));
					rhs.multAdd(-M, expr);
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint21");
				}
			}
		}
	}
	
	private void constraint22() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Node j : this.network.getC()) {
				for(Node i : this.network.getN0()) {
					if(i == j) continue;
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getDroneArrival().get(v).get(j));
					
					GRBLinExpr rhs = new GRBLinExpr();
					GRBLinExpr expr = new GRBLinExpr();
					expr.addConstant(1);
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						expr.addTerm(-1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
					
					rhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(i));
					rhs.addConstant(i.getDroneDistanceTo(j, v));
					rhs.multAdd(M, expr);
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint22[" + v.getIndex() + "][" + j.getIndex() + "][" + i.getIndex() + "]");
				}
			}
		}
	}

	private void constraint23() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Node j : this.network.getC()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(j));
				
				GRBLinExpr rhs = new GRBLinExpr();
				GRBLinExpr expr = new GRBLinExpr();
				for(Node i : this.network.getN0()) {
					if(i == j) continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						expr.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
				}
				
				rhs.addTerm(1, this.variables.getDroneArrival().get(v).get(j));
				rhs.multAdd(j.getDroneServiceTime(v), expr);
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint23");
			}
		}
	}
	
	private void constraint24() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Node j : this.network.getC()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(j));
				
				GRBLinExpr rhs = new GRBLinExpr();
				GRBLinExpr expr = new GRBLinExpr();
				expr.addConstant(1);
				for(Node i : this.network.getN0()) {
					if(i == j) continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						expr.addTerm(-1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
				}
				
				rhs.addTerm(1, this.variables.getDroneArrival().get(v).get(j));
				rhs.addConstant(j.getDroneServiceTime(v));
				rhs.multAdd(M, expr);
				
				this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint24");
			}
		}
	}

	private void constraint25() throws GRBException { // Drone retrieval, if drone arrives first.
		for(Drone v : this.network.getV()) {
			for(Node k : this.network.getNPlus()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addTerm(1, this.variables.getTruckArrival().get(k));
				rhs.addConstant(k.getDroneRetrievalTime(v));
				rhs.addConstant(-M);
				rhs.addTerm(M, this.variables.getzDroneRetrievedFirst().get(v).get(k));
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint25");
			}
		}
	}
	
	private void constraint26() throws GRBException { // Drone retrieval, if truck arrives first
		for(Drone v : this.network.getV()) {
			for(Node k : this.network.getNPlus()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addTerm(1, this.variables.getTruckService().get(k));
				rhs.addConstant(k.getDroneRetrievalTime(v));
				rhs.addConstant(-M);
				rhs.addTerm(M, this.variables.getzDroneRetrievedSecond().get(v).get(k));
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint26");
			}
		}
	}
	
	private void constraint27() throws GRBException { // 2 Drone retrieval
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getNPlus()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					rhs.addTerm(1, this.variables.getDroneArrival().get(v2).get(k));
					rhs.addConstant(k.getDroneRetrievalTime(v));
					rhs.addConstant(-M);
					rhs.addTerm(M, this.variables.getzTwoDroneRetrieval().get(v2).get(v).get(k));
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint27");
				}
			}
		}
	}

	private void constraint28() throws GRBException { // 2 drone first launch then retrieval
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					rhs.addTerm(1, this.variables.getDroneCompletion().get(v2).get(k));
					rhs.addConstant(k.getDroneRetrievalTime(v));
					rhs.addConstant(-M);
					rhs.addTerm(M, this.variables.getzFirstLaunchSecondRetrieval().get(v2).get(v).get(k));
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint27");
				}
			}
		}
	}

	private void constraint29() throws GRBException { // if drone arrives later than service time of truck
		for(Drone v : this.network.getV()) {
			for(Node k : this.network.getNPlus()) {
				for(Node j : this.network.getC()) {
					if(k == j) continue;
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					GRBLinExpr expr = new GRBLinExpr();
					expr.addConstant(1);
					for(Node i : this.network.getN0()) {
						if(!isSortie(v, i, j, k)) continue;
						expr.addTerm(-1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
					rhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(j));
					rhs.addConstant(j.getDroneDistanceTo(k, v));
					rhs.addConstant(k.getDroneRetrievalTime(v));
					rhs.multAdd(-M, expr);
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint29");
				}
			}
		}
	}

	private void constraint30() throws GRBException { // Endurance
		for(Drone v : this.network.getV()) {
			for(Node i : this.network.getN0()) {
				for(Node j : this.network.getCDrone().get(v)) {
					if(i == j) continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						GRBLinExpr lhs = new GRBLinExpr();
						lhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
						lhs.addConstant(-k.getDroneRetrievalTime(v));
						lhs.addTerm(-1, this.variables.getDroneCompletion().get(v).get(i));
						
						GRBLinExpr rhs = new GRBLinExpr();
						rhs.addConstant(v.getEndurance());
						rhs.addConstant(M);
						rhs.addTerm(-M, this.variables.getY().get(v).get(i).get(j).get(k));
						
						this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint30");
					}
				}
			}
		}
	}

	private void constraint31() throws GRBException { // Truck arrival
		for(Node i : this.network.getN0()) {
			for(Node j : this.network.getNPlus()) {
				if(j == i) continue;
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getTruckArrival().get(j));
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addTerm(1, this.variables.getTruckCompletion().get(i));
				rhs.addConstant(i.getTruckDistanceTo(j));
				rhs.addConstant(-M);
				rhs.addTerm(M, this.variables.getX().get(i).get(j));
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint31_[" + i.getIndex() + "][" + j.getIndex() + "]");
			}
		}
	}

	private void constraint32() throws GRBException { // Truck service time if truck serves first
		for(Node k : this.network.getNPlus()) {
			GRBLinExpr lhs = new GRBLinExpr();
			lhs.addTerm(1, this.variables.getTruckService().get(k));
			
			GRBLinExpr rhs = new GRBLinExpr();
			GRBLinExpr expr = new GRBLinExpr();
			for(Node j : this.network.getN0()) {
				if (j == k) continue;
				expr.addTerm(1, this.variables.getX().get(j).get(k));
			}
			rhs.addTerm(1, this.variables.getTruckArrival().get(k));
			rhs.multAdd(k.getTruckServiceTime(), expr);
			
			this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint32");
		}
	}

	private void constraint33() throws GRBException { // Truck service time if drone retrieved first
		for(Node k : this.network.getNPlus()) {
			for(Drone v : this.network.getV()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getTruckService().get(k));
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
				rhs.addConstant(k.getTruckServiceTime());
				rhs.addConstant(-M);
				rhs.addTerm(M, this.variables.getzDroneRetrievedFirst().get(v).get(k));
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint33");				
			}
		}
	}
	
	private void constraint34() throws GRBException { // Truck service time if drone launched first
		for(Node k : this.network.getC()) {
			for(Drone v : this.network.getV()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getTruckService().get(k));
				
				GRBLinExpr rhs = new GRBLinExpr();
				rhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(k));
				rhs.addConstant(k.getTruckServiceTime());
				rhs.addConstant(-M);
				rhs.addTerm(M, this.variables.getzDroneLaunchedFirst().get(v).get(k));
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint34");				
			}
		}
	}

	private void constraint35() throws GRBException { // Truck completion time lower bounded by service time
		for(Node k : this.network.getNPlus()) {
			this.model.addConstr(this.variables.getTruckCompletion().get(k), GRB.GREATER_EQUAL, this.variables.getTruckService().get(k), "Constraint35");
		}
	}

	private void constraint36() throws GRBException { // Truck completion lower bound if drone retrieved first
		for(Node k : this.network.getNPlus()) {
			for(Drone v : this.network.getV()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getTruckCompletion().get(k));
				
				GRBLinExpr rhs = new GRBLinExpr();
				GRBLinExpr expr = new GRBLinExpr();
				expr.addConstant(1);
				for(Node i : this.network.getN0()) {
					if(i == k) continue;
					for(Node j : this.network.getC()) {
						if(!isSortie(v, i, j, k)) continue;
						expr.addTerm(-1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
				}
				rhs.addTerm(1, this.variables.getDroneArrival().get(v).get(k));
				rhs.multAdd(-M, expr);
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint36");
			}
		}
	}

	private void constraint37() throws GRBException { // Truck completion lower bound if drone launched first
		for(Node k : this.network.getN0()) {
			for(Drone v : this.network.getV()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getTruckCompletion().get(k));
				
				GRBLinExpr rhs = new GRBLinExpr();
				GRBLinExpr expr = new GRBLinExpr();
				expr.addConstant(1);
				for(Node l : this.network.getC()) {
					if(l == k) continue;
					for(Node m : this.network.getNPlus()) {
						if(!isSortie(v, k, l, m)) continue;
						expr.addTerm(-1, this.variables.getY().get(v).get(k).get(l).get(m));
					}
				}
				rhs.addTerm(1, this.variables.getDroneCompletion().get(v).get(k));
				rhs.multAdd(-M, expr);
				
				this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint37");
			}
		}
	}

	private void constraint38() throws GRBException { // if sortie (v,i,j,k) used either drone retrieved first in k or trucks serves first
		for(Drone v : this.network.getV()) {
			for(Node k : this.network.getNPlus()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getzDroneRetrievedSecond().get(v).get(k));
				lhs.addTerm(1, this.variables.getzDroneRetrievedFirst().get(v).get(k));
				
				GRBLinExpr rhs = new GRBLinExpr();
				for(Node i : this.network.getN0()) {
					if(i == k) continue;
					for(Node j : this.network.getC()) {
						if(!isSortie(v, i, j, k)) continue;
						rhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
				}
				
				this.model.addConstr(lhs, GRB.EQUAL, rhs, "Constraint38");
			}
		}
	}

	private void constraint39() throws GRBException { // if no v arrives to k, v cannot be retrieved before v2.
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getNPlus()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzTwoDroneRetrieval().get(v).get(v2).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node i : this.network.getN0()) {
						if(i == k) continue;
						for(Node j : this.network.getC()) {
							if(!isSortie(v, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
						}
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint39");
				}
			}
		}
	}

	private void constraint40() throws GRBException { // if no v2 arrives to k, v2 cannot be retrieved after v.
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getNPlus()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzTwoDroneRetrieval().get(v).get(v2).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node i : this.network.getN0()) {
						if(i == k) continue;
						for(Node j : this.network.getC()) {
							if(!isSortie(v2, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v2).get(i).get(j).get(k));
						}
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint40");
				}
			}
		}
	}

	private void constraint41() throws GRBException { // z_vv2 and z_v2v cannot occur at the same time.
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getNPlus()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzTwoDroneRetrieval().get(v).get(v2).get(k));
					lhs.addTerm(1, this.variables.getzTwoDroneRetrieval().get(v2).get(v).get(k));
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, 1, "Constraint41");
				}
			}
		}
	}

	private void constraint42() throws GRBException { // if v and v2 arrives to k at least one of the cases {z_vv2, z_v2v} should occur.
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getNPlus()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzTwoDroneRetrieval().get(v).get(v2).get(k));
					lhs.addTerm(1, this.variables.getzTwoDroneRetrieval().get(v2).get(v).get(k));
					lhs.addConstant(1);
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node i : this.network.getN0()) {
						if(i == k) continue;
						for(Node j : this.network.getC()) {
							if(!isSortie(v, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
						}
					}
					for(Node i : this.network.getN0()) {
						if(i == k) continue;
						for(Node j : this.network.getC()) {
							if(!isSortie(v2, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v2).get(i).get(j).get(k));
						}
					}
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint42");
				}
			}
		}
	}

	private void constraint43() throws GRBException { // if sortie (v,i,j,k) used either drone launched first in i or trucks serves first 
		for(Drone v : this.network.getV()) {
			for(Node i : this.network.getN0()) {
				GRBLinExpr lhs = new GRBLinExpr();
				lhs.addTerm(1, this.variables.getzDroneLaunchedSecond().get(v).get(i));
				lhs.addTerm(1, this.variables.getzDroneLaunchedFirst().get(v).get(i));
				
				GRBLinExpr rhs = new GRBLinExpr();
				for(Node j : this.network.getC()) {
					if(j == i) continue;
					for(Node k : this.network.getNPlus()) {
						if(!isSortie(v, i, j, k)) continue;
						rhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
					}
				}
				
				this.model.addConstr(lhs, GRB.EQUAL, rhs, "Constraint43");
			}
		}
	}

	private void constraint44() throws GRBException { // Drone v can be launched from v before v2 if v uses some sortie starting with i.
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node i : this.network.getN0()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzTwoDroneLaunch().get(v).get(v2).get(i));
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node j : this.network.getC()) {
						if(i == j) continue;
						for(Node k : this.network.getNPlus()) {
							if(!isSortie(v, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
						}
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint44");
				}
			}
		}
	}

	private void constraint45() throws GRBException { // Drone v2 can be launched from v2 before v if v2 uses some sortie starting with i.
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node i : this.network.getN0()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzTwoDroneLaunch().get(v).get(v2).get(i));
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node j : this.network.getC()) {
						if(i == j) continue;
						for(Node k : this.network.getNPlus()) {
							if(!isSortie(v2, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v2).get(i).get(j).get(k));
						}
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint45");
				}
			}
		}
	}

	private void constraint46() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node i : this.network.getN0()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzTwoDroneLaunch().get(v).get(v2).get(i));
					lhs.addTerm(1, this.variables.getzTwoDroneLaunch().get(v2).get(v).get(i));
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, 1, "Constraint46");
				}
			}
		}
	}

	private void constraint47() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node i : this.network.getN0()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzTwoDroneLaunch().get(v).get(v2).get(i));
					lhs.addTerm(1, this.variables.getzTwoDroneLaunch().get(v2).get(v).get(i));
					lhs.addConstant(1);
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node j : this.network.getC()) {
						if(j == i) continue;
						for(Node k : this.network.getNPlus()) {
							if(!isSortie(v, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
						}
					}
					for(Node j : this.network.getC()) {
						if(j == i) continue;
						for(Node k : this.network.getNPlus()) {
							if(!isSortie(v2, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v2).get(i).get(j).get(k));
						}
					}
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint47");					
				}
			}
		}
	}

	private void constraint48() throws GRBException {
		for(Drone v2 : this.network.getV()) {
			for(Drone v : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzFirstLaunchSecondRetrieval().get(v2).get(v).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node l : this.network.getC()) {
						if(l == k) continue;
						for(Node m : this.network.getNPlus()) {
							if(!isSortie(v2, k, l, m)) continue;
							rhs.addTerm(1, this.variables.getY().get(v2).get(k).get(l).get(m));
						}
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint48");
				}
			}
		}
	}

	private void constraint49() throws GRBException {
		for(Drone v2 : this.network.getV()) {
			for(Drone v : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzFirstRetrievalSecondLaunch().get(v2).get(v).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node l : this.network.getC()) {
						if(l == k) continue;
						for(Node m : this.network.getNPlus()) {
							if(!isSortie(v, k, l, m)) continue;
							rhs.addTerm(1, this.variables.getY().get(v).get(k).get(l).get(m));
						}
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint49");
				}
			}
		}
	}

	private void constraint50() throws GRBException {
		for(Drone v2 : this.network.getV()) {
			for(Drone v : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzFirstLaunchSecondRetrieval().get(v2).get(v).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node i : this.network.getN0()) {
						if(i == k) continue;
						for(Node j : this.network.getC()) {
							if(!isSortie(v, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
						}
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint50");
				}
			}
		}
	}

	private void constraint51() throws GRBException {
		for(Drone v2 : this.network.getV()) {
			for(Drone v : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzFirstRetrievalSecondLaunch().get(v2).get(v).get(k));
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node i : this.network.getN0()) {
						if(i == k) continue;
						for(Node j : this.network.getC()) {
							if(!isSortie(v2, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v2).get(i).get(j).get(k));
						}
					}
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, rhs, "Constraint51");
				}
			}
		}
	}

	private void constraint52() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzFirstLaunchSecondRetrieval().get(v2).get(v).get(k));
					lhs.addTerm(1, this.variables.getzFirstRetrievalSecondLaunch().get(v).get(v2).get(k));
					lhs.addConstant(1);
					
					GRBLinExpr rhs = new GRBLinExpr();
					for(Node i : this.network.getN0()) {
						if(i == k) continue;
						for(Node j : this.network.getC()) {
							if(!isSortie(v, i, j, k)) continue;
							rhs.addTerm(1, this.variables.getY().get(v).get(i).get(j).get(k));
						}
					}
					for(Node l : this.network.getC()) {
						if(l == k) continue;
						for(Node m : this.network.getNPlus()) {
							if(!isSortie(v2, k, l, m)) continue;
							rhs.addTerm(1, this.variables.getY().get(v2).get(k).get(l).get(m));
						}
					}
					
					this.model.addConstr(lhs, GRB.GREATER_EQUAL, rhs, "Constraint52");
				}
			}
		}
	}

	private void constraint53() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzFirstLaunchSecondRetrieval().get(v2).get(v).get(k));
					lhs.addTerm(1, this.variables.getzFirstRetrievalSecondLaunch().get(v).get(v2).get(k));
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, 1, "Constraint53");
				}
			}
		}
	}
	
	private void constraint54() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzFirstLaunchSecondRetrieval().get(v2).get(v).get(k));
					lhs.addTerm(1, this.variables.getzFirstLaunchSecondRetrieval().get(v).get(v2).get(k));
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, 1, "Constraint54");
				}
			}
		}
	}
	
	private void constraint55() throws GRBException {
		for(Drone v : this.network.getV()) {
			for(Drone v2 : this.network.getV()) {
				if(v == v2) continue;
				for(Node k : this.network.getC()) {
					GRBLinExpr lhs = new GRBLinExpr();
					lhs.addTerm(1, this.variables.getzFirstRetrievalSecondLaunch().get(v2).get(v).get(k));
					lhs.addTerm(1, this.variables.getzFirstRetrievalSecondLaunch().get(v).get(v2).get(k));
					
					this.model.addConstr(lhs, GRB.LESS_EQUAL, 1, "Constraint54");
				}
			}
		}
	}
}

