package TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBVar;
import main.Network;
import main.Node;
import main.Variables;

public class TSPWriteData {
	private Network network;
	private TSPVariables variables;
	
	public TSPWriteData(Network network, TSPVariables variables, String folderLocation) throws GRBException, IOException {
		this.network = network;
		this.variables = variables;
		
		writeTruckRouteToCsv(folderLocation);
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
	
	private void writeTruckRouteToCsv(String folderLocation) throws GRBException, FileNotFoundException {
		ArrayList<Node> order = new ArrayList<>();
		order.add(network.getStartingDepot());
		orderTruckRoute(order, network.getStartingDepot(), network.getEndingDepot());
		
		PrintWriter out = new PrintWriter(new File(folderLocation + "\\TSPTruckRoute.csv"));
		
		out.println("Index,X,Y");
		for(Node i : order) {
			String s = String.format(Locale.ROOT, "%d,%f,%f", i.getIndex(), i.getPosition().getX(), i.getPosition().getY());
			out.println(s);
		}
		out.close();
	}

}
