package finra;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
	static boolean finishedChoosingCoords = false;
	
	public static void main(String[] args) throws IOException {
		GUI.makeWindow(); 
		
		while(!finishedChoosingCoords){
			System.out.println(".");
		}
		
		int[] xyCoords = GUI.getCoords();
		ArrayList<ArrayList<Integer>> startPoints = EndpointFinder.startpoints(xyCoords[1], xyCoords[0]);
		
		System.out.println(startPoints.get(0).toString());
		System.out.println(startPoints.get(1).toString());
		
		photoOutput.createPicture(startPoints);
	}
}
