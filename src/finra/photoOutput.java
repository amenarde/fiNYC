package finra;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class photoOutput {
	static void createPicture(ArrayList<ArrayList<Integer>> pixels) throws IOException
	{	
		BufferedImage b = ImageIO.read(new File("images/NYC_portrait.jpg"));
		int i = 0;
		for(i = 0; i < pixels.get(0).size(); i++){
			try {
				b.setRGB(pixels.get(0).get(i), pixels.get(1).get(i), Color.red.getRGB());
			} catch (Exception e) {
				// ignore the edge case
			}
		}
		ImageIO.write(b, "jpg", new File("images/output"));
	}
}
