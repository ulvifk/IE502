package main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBVar;

public class WriteData {
	private Network network;
	private Variables variables;
	
	public WriteData(Network network, Variables variables, String folderLocation) throws GRBException, IOException {
		this.network = network;
		this.variables = variables;
		
		writeNodesToCsv(folderLocation);
		writeTruckRouteToCsv(folderLocation);
		writeDroneRouteToCsv(folderLocation);
	}
	
	private void writeNodesToCsv(String folderLocation) throws IOException {
		PrintWriter out = new PrintWriter(new File(folderLocation + "\\Nodes.csv"));
		
		out.println("Index,X,Y,Type");
		for(Node i : this.network.getN()) {
			String s = String.format(Locale.ROOT, "%d,%f,%f,%s", i.getIndex(), i.getPosition().getX(), i.getPosition().getY(), i.getType());
			out.println(s);
		}
		
		out.close();
	}

	private void writeTruckRouteToCsv(String folderLocation) throws GRBException, FileNotFoundException {
		ArrayList<Node> order = new ArrayList<>();
		order.add(network.getStartingDepot());
		orderTruckRoute(order, network.getStartingDepot(), network.getEndingDepot());
		
		PrintWriter out = new PrintWriter(new File(folderLocation + "\\TruckRoute.csv"));
		
		out.println("Index,X,Y,CompletionTime");
		for(Node i : order) {
			String s = String.format(Locale.ROOT, "%d,%f,%f,%f", i.getIndex(), i.getPosition().getX(), i.getPosition().getY(), this.variables.getTruckCompletion().get(i).get(GRB.DoubleAttr.X));
			out.println(s);
		}
		out.close();
	}

	private void writeDroneRouteToCsv(String folderLocation) throws FileNotFoundException, GRBException {
		PrintWriter out = new PrintWriter(new File(folderLocation + "\\DroneRoute.csv"));
		out.println("DroneIndex,iIndex,jIndex,kIndex,iX,iY,jX,jY,kX,kY,Launch[i],Arrival[j],Launch[j],Retrieval[k]");
		
		for(Drone v : this.variables.getY().keySet()) {
			for(Node i : this.variables.getY().get(v).keySet()) {
				for(Node j : this.variables.getY().get(v).get(i).keySet()) {
					for(Node k : this.variables.getY().get(v).get(i).get(j).keySet()) {
						GRBVar y = this.variables.getY().get(v).get(i).get(j).get(k);
						double yValue = y.get(GRB.DoubleAttr.X);
						if(yValue > 0.7) {
							String s = String.format(Locale.ROOT, "%d,%d,%d,%d,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f", v.getIndex(), i.getIndex(), j.getIndex(), k.getIndex(),
																							i.getPosition().getX(), i.getPosition().getY(),
																							j.getPosition().getX(), j.getPosition().getY(),
																							k.getPosition().getX(), k.getPosition().getY(),
																							this.variables.getDroneCompletion().get(v).get(i).get(GRB.DoubleAttr.X),
																							this.variables.getDroneArrival().get(v).get(j).get(GRB.DoubleAttr.X),
																							this.variables.getDroneCompletion().get(v).get(j).get(GRB.DoubleAttr.X),
																							this.variables.getDroneArrival().get(v).get(k).get(GRB.DoubleAttr.X));
							out.println(s);
						}
					}
				}
			}
		}
		out.close();
	}
	private void orderTruckRoute(ArrayList<Node> order, Node i, Node stop) throws GRBException {
		if(i == stop) return;
		
		Node nextNode = null;
		for(Node j : this.variables.getX().get(i).keySet()) {
			GRBVar x = this.variables.getX().get(i).get(j);
			double xValue = x.get(GRB.DoubleAttr.X);
			if(xValue > 0.8) {
				nextNode = j;
				break;
			}
		}
		order.add(nextNode);
		
		orderTruckRoute(order, nextNode, stop);
	}
}
