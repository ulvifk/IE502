package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import TSP.TSPModel;
import gurobi.GRBException;

public class main {

	public static void main(String[] args) throws IOException, GRBException {
		// TODO Auto-generated method stub
//		Network network = new Network(0);
//		Model model = new Model(network);
//		int x = 0;
		int[] nArray = {8, 10, 12};
		int iTimes[] = {1, 2, 3, 4};
		int[] drones = {5};
		int[] penaltyMins = {25, 30, 35, 40};
		runAll(nArray, iTimes, drones, penaltyMins);
	}

	public static void runAll(int[] nArray, int[] iTimes, int[] drones, int[] penaltyMins) throws IOException, GRBException {
		for(int n : nArray) {
			for(int i : iTimes) {
				for(int droneNumber : drones) {
					for(int penaltyMin : penaltyMins) {
						String fileLocation = String.format("..\\Python\\JavaData\\Data_%d\\Data_%d_%d", n, n, i);
						File folder = new File(String.format("..\\NewModel\\Data_%d_%d_%d", n, droneNumber, i));
						if(!Files.exists(Paths.get(String.format("..\\NewModel\\Data_%d_%d_%d", n, droneNumber, i)))) {
							folder.mkdir();
						}
						folder = new File(String.format("..\\NewModel\\Data_%d_%d_%d\\Penalty_%dMins", n, droneNumber, i, penaltyMin));
						if(!Files.exists(Paths.get(String.format("..\\NewModel\\Data_%d_%d_%d\\Penalty_%dMins", n, droneNumber, i, penaltyMin)))) {
							folder.mkdir();
						}
						
						String solutionWriteFolder = String.format("..\\NewModel\\Data_%d_%d_%d\\Penalty_%dMins", n, droneNumber, i, penaltyMin);
						Network network = new Network(droneNumber, fileLocation, penaltyMin);
						network.sigmaPenalty = 1;
						network.sigmaTime = 1;
						
						TSPModel tspModel = new TSPModel(network, fileLocation);
						Model model = new Model(network, solutionWriteFolder, tspModel.objectiveValue);
						
					}
				}
			}
		}
	}
}
