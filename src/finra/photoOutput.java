package finra;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;

public class photoOutput {
	static void createPicture(ArrayList<ArrayList<Integer>> pixels) throws IOException
	{	
		BufferedImage taxi = ImageIO.read(new File("images/taxi.png"));
		BufferedImage b = ImageIO.read(new File("images/NYC_portrait.jpg"));
		for (int i = 0; i < pixels.get(0).size(); i++){
			try {
				int x = pixels.get(0).get(i);
				int y = pixels.get(1).get(i);
				b.setRGB(x - 15, y - 5, Color.red.getRGB());
			} catch (Exception e) {
				// ignore the edge case
			}
		}
		int x = 0, y = 0;
		try {
			BufferedReader c = new BufferedReader(new FileReader(new File("coords.txt")));
			Scanner sc = new Scanner(c);
			x = Integer.parseInt(sc.nextLine());
			y = Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			System.out.println("error gathering points from coords.txt");
		}
		
		for (int i = 0; i < taxi.getWidth(); i++) {
			for (int j = 0; j < taxi.getHeight(); j++) {
				try {
					b.setRGB(x + i - 45, y + j - 20, taxi.getRGB(i, j)); 
				} catch (ArrayIndexOutOfBoundsException e) {
					// ignore point
				}
			}
		}
		ImageIO.write(b, "jpg", new File("images/output.jpg"));
	}
}
