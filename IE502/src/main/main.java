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
		int[] nArray = {8};
		int iTimes = 1;
		int[] drones = {2};
		int[] penaltyMins = {10};
		runAll(nArray, iTimes, drones, penaltyMins);
	}

	public static void runAll(int[] nArray, int iTimes, int[] drones, int[] penaltyMins) throws IOException, GRBException {
		for(int n : nArray) {
			for(int i = 1; i<=iTimes; i++) {
				for(int droneNumber : drones) {
					for(int penaltyMin : penaltyMins) {
						String fileLocation = String.format("..\\Python\\JavaData\\Data_%d\\Data_%d_%d", n, n, i);
						File folder = new File(String.format("..\\NewModel\\Data_%d_%d_%d", n, droneNumber, i));
						if(!Files.exists(Paths.get(String.format("..\\NewModel\\Data_%d_%d_%d", n, droneNumber, i)))) {
							folder.mkdir();
						}
						folder = new File(String.format("..\\NewModel\\Data_%d_%d_%d\\Penalt_%dMins", n, droneNumber, i, penaltyMin));
						if(!Files.exists(Paths.get(String.format("..\\NewModel\\Data_%d_%d_%d\\Penalt_%dMins", n, droneNumber, i, penaltyMin)))) {
							folder.mkdir();
						}
						
						String solutionWriteFolder = String.format("..\\NewModel\\Data_%d_%d_%d\\Penalt_%dMins", n, droneNumber, i, penaltyMin);
						Network network = new Network(droneNumber, fileLocation, penaltyMin);
						network.sigmaPenalty = 1;
						network.sigmaTime = 1;
						Model model = new Model(network, solutionWriteFolder, 0);
						
					}
				}
			}
		}
	}
}
