package finra;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

// see Zoom.java
public class GUI {
 
	public static void main(String[] args) throws IOException {
		//makeWindow(); 
		// y, then x
		ArrayList<ArrayList<Integer>> startPoints = EndpointFinder.startpoints(100, 100);
		System.out.println(startPoints.get(0).toString());
		System.out.println(startPoints.get(1).toString());
		photoOutput.createPicture(startPoints);
	}
	
	public static void makeWindow() {
		try {
			ImagePanel panel = new ImagePanel();
	        ImageZoom zoom = new ImageZoom(panel);
	        JFrame f = new JFrame();
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.getContentPane().add(zoom.getUIPanel(), "North");
	        f.getContentPane().add(new JScrollPane(panel));
	        f.setSize(2400,1561);
	        f.setLocation(40,40);
	        f.setVisible(true);
		} catch (IOException io) {
			System.out.println("i/o error in getCoords (GUI.java)");
		}
	}
	
	public static int[] getCoords() {
		try {
			File file = new File("coords.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			Scanner sc = new Scanner(br);
			int counter = 0, x = -1, y = -1;
			while (sc.hasNextLine()) {
				int coord = Integer.parseInt(sc.nextLine());
				if (counter % 2 == 0) {
					x = coord;
				} else {
					y = coord;
				}
				counter++;
			}
			sc.close();
			return new int[] {x, y};
		} catch (IOException io) {
			System.out.println("no point selected!");
		}
        return new int[] {-1, -1};
	}

}
