import java.io.FileNotFoundException;
import java.io.IOException;

import gurobi.GRBException;

public class main {

	public static void main(String[] args) throws IOException, GRBException {
		// TODO Auto-generated method stub
//		Network network = new Network(0);
//		Model model = new Model(network);
//		int x = 0;
		int[] nArray = {10, 20, 40};
		int iTimes = 1;
		
		runAll(nArray, iTimes);
	}

	public static void runAll(int[] nArray, int iTimes) throws IOException, GRBException {
		for(int n : nArray) {
			for(int i = 1; i<=iTimes; i++) {
				String fileLocation = String.format("..\\Python\\JavaData\\Data_%d\\Data_%d_%d.csv", n, n, i);
				Network network = new Network(1, fileLocation);

				String solutionWriteFolder = String.format("..\\Python\\SolutionData\\Data_%d_%d", n, i);
				Model model = new Model(network, solutionWriteFolder);
			}
		}
	}
}
