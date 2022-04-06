package main;
import java.io.FileNotFoundException;
import java.io.IOException;

import TSP.TSPModel;
import gurobi.GRBException;

public class main {

	public static void main(String[] args) throws IOException, GRBException {
		// TODO Auto-generated method stub
//		Network network = new Network(0);
//		Model model = new Model(network);
//		int x = 0;
		int[] nArray = {10};
		int iTimes = 2;
		int[] drones = {2};
		runAll(nArray, iTimes, drones);
	}

	public static void runAll(int[] nArray, int iTimes, int[] drones) throws IOException, GRBException {
		for(int n : nArray) {
			for(int i = 1; i<=iTimes; i++) {
				for(int droneNumber : drones) {
					String fileLocation = String.format("..\\Python\\JavaData\\Data_%d\\Data_%d_%d", n, n, i);
					Network network = new Network(droneNumber, fileLocation);
					
					TSPModel TSPmodel = new TSPModel(network, "");
					
					String solutionWriteFolder = String.format("..\\Python\\SolutionData\\Data_%d_%d_%d", n, droneNumber, i);
					Model model = new Model(network, solutionWriteFolder, TSPmodel.objectiveValue);
				}
			}
		}
	}
}
