package finra;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	static boolean finishedChoosingCoords = false;
	
	public static void main(String[] args) throws IOException {
		GUI.makeWindow(); 
		
		while(!finishedChoosingCoords){
			System.out.print(".");
		}
		
		int[] xyCoords = GUI.getCoords();
		System.out.println("\ngot coordinates");
		ArrayList<ArrayList<Integer>> startPoints = EndpointFinder.startpoints(xyCoords[1], xyCoords[0]);
		System.out.println("calculated trip endpoints");
		photoOutput.createPicture(startPoints);
		System.out.println("saved map to file"); 
		
		Desktop.getDesktop().open(new File("images/output.jpg"));
	}
}
