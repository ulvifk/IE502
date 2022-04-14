package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

import TSP.TSPModel;
import gurobi.GRBException;

public class main {

	public static void main(String[] args) throws IOException, GRBException {
		// TODO Auto-generated method stub
//		Network network = new Network(0);
//		Model model = new Model(network);
//		int x = 0;
		int[] nArray = {8};
		int iTimes[] = {1, 2, 3, 4, 5};
		int[] drones = {5};
		int[] penaltyMins = {40};
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
						
						String tspLocation = String.format("..\\NewModel\\Data_%d_%d_%d\\Penalty_%dMins\\TSP", n, droneNumber, i, penaltyMin);
						folder = new File(tspLocation);
						if(!Files.exists(Paths.get(tspLocation))) {
							folder.mkdir();
						}
						TSPModel tspModel = new TSPModel(network, tspLocation);
						Model model = new Model(network, solutionWriteFolder, tspModel.objectiveValue);
						
						ArrayList<double[]> results = new ArrayList<>();
						String tableResultFolder = String.format("..\\NewModel\\Data_%d_%d_%d", n, droneNumber, i);
					}
				}
			}
		}
	}
	
	private void tableTests(ArrayList<double[]> results, String folderLocation) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new File(folderLocation + "\\ResultTable.csv"));
		out.println("Input,Objective,Runtime,TSP");
		
		for(double[] result : results) {
			int index = (int) result[0];
			double objective = result[1];
			double runTime = result[2];
			double tspObjective = result[3];
			
			out.println(String.format(Locale.ROOT, "%d,%f,%f,%f", index, objective, runTime, tspObjective));

		}
		
		out.close();
		
	}
}
