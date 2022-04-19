package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import TSP.TSPModel;
import gurobi.GRBException;

public class main {

	public static void main(String[] args) throws IOException, GRBException {
		// TODO Auto-generated method stub
//		Network network = new Network(0);
//		Model model = new Model(network);
//		int x = 0;
		int[] nArray = {12, 14};
		int iTimes[] = {1, 2, 3, 4, 5};
		int[] drones = {5};
		int[] penaltyMins = {10, 15, 20, 25, 30, 35, 40};
		runAll(nArray, iTimes, drones, penaltyMins);
	}

	public static void runAll(int[] nArray, int[] iTimes, int[] drones, int[] penaltyMins) throws IOException, GRBException {	
		 for(int n : nArray){
			HashMap<Integer, ArrayList<double[]>> allResults = new HashMap<>();
			String tableResultFolder = String.format("..\\NewModel\\resultsTable_%d.csv", n);
			for(int penaltyMin : penaltyMins){
				ArrayList<double[]> results = new ArrayList<>();
				for(int droneNumber : drones) {
					for(int i : iTimes){
						String fileLocation = String.format("..\\Python\\JavaData\\Data_%d\\Data_%d_%d", n, n, i);
						File folder = new File(String.format("..\\NewModel\\Penalty_%dMins", penaltyMin));
						if(!Files.exists(Paths.get(String.format("..\\NewModel\\Penalty_%dMins", penaltyMin)))) {
							folder.mkdir();
						}
						folder = new File(String.format("..\\NewModel\\Penalty_%dMins\\Data_%d_%d_%d", penaltyMin, n, droneNumber, i));
						if(!Files.exists(Paths.get(String.format("..\\NewModel\\Penalty_%dMins\\Data_%d_%d_%d", penaltyMin, n, droneNumber, i)))) {
							folder.mkdir();
						}
						
						String solutionWriteFolder = String.format("..\\NewModel\\Penalty_%dMins\\Data_%d_%d_%d", penaltyMin, n, droneNumber, i);
						Network network = new Network(droneNumber, fileLocation, penaltyMin);
						network.sigmaPenalty = 1;
						network.sigmaTime = 1;
						
						String tspLocation = String.format("..\\NewModel\\Penalty_%dMins\\Data_%d_%d_%d\\TSP", penaltyMin, n, droneNumber, i);
						folder = new File(tspLocation);
						if(!Files.exists(Paths.get(tspLocation))) {
							folder.mkdir();
						}
						TSPModel tspModel = new TSPModel(network, tspLocation);
						Model model = new Model(network, solutionWriteFolder, tspModel.objectiveValue);
						
						double[] result = {(double)i, model.objective, model.runTime, tspModel.objectiveValue};
						results.add(result);
					}
				}
				allResults.put(penaltyMin, results);
			}
			tableTests(allResults, tableResultFolder);
		}
	}
	
	private static void tableTests(HashMap<Integer, ArrayList<double[]>> allResults, String folderLocation) throws FileNotFoundException {
		ArrayList<Integer> orderedKeys = new ArrayList<>(allResults.keySet());
		Collections.sort(orderedKeys);
		
		PrintWriter out = new PrintWriter(new File(folderLocation));
		out.println("Input,Objective,Runtime,TSP");
		
		for(int penaltyMin : orderedKeys) {
			out.println("PenaltyMin: " + penaltyMin);
			ArrayList<double[]> results = allResults.get(penaltyMin);
			for(double[] result : results) {
				int index = (int) result[0];
				double objective = result[1];
				double runTime = result[2];
				double tspObjective = result[3];
				
				out.println(String.format(Locale.ROOT, "%d,%f,%f,%f", index, objective, runTime, tspObjective));	
			}
			out.println();
		}
		
		out.close();
		
	}
}
