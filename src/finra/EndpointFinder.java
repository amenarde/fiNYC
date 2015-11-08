package finra;

//@Author Antonio Menarde

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class EndpointFinder {
	static float[][] dataArray = new float[10_000_000][4];
	
	static ArrayList<ArrayList<Integer>> startpoints(int yVal, int xVal) throws IOException
	{	
		//coordinates of geographic boundary box
		float minLat = -1;
		float maxLat = -1;
		float minLong = -1;
		float maxLong = -1;
		
		float squareRadius = -1;
		
		//size of photo of geographic space
		int photoX = -1;
		int photoY = -1;
		
		//read parameters from file
		File paramFile = new File("param1.csv");
		BufferedReader paramBR = new BufferedReader(new FileReader(paramFile));
		Scanner paramSC = new Scanner(paramBR);
		paramSC.nextLine();
		String info = paramSC.nextLine();
		@SuppressWarnings("resource")
		Scanner infoSort = new Scanner(info).useDelimiter(",");
		try{
			minLat = Float.parseFloat(infoSort.next());
			maxLat = Float.parseFloat(infoSort.next());
			minLong = Float.parseFloat(infoSort.next());
			maxLong = Float.parseFloat(infoSort.next());
				
			photoX = Integer.parseInt(infoSort.next());
			photoY = Integer.parseInt(infoSort.next());
				
			squareRadius = Integer.parseInt(infoSort.next());
		}
		catch(Exception ex){
			System.out.println("Error with data in param file");
			System.exit(1);
		}
		paramSC.close();
	
		//convert map pixels to coordinates
		float latLength = maxLat - minLat; // vertical
		float longLength = maxLong - minLong; // horizontal
		
		float currLat = (((1 - (float)yVal/photoY)) * latLength) + minLat;
		float currLong = (((float)xVal/photoX) * longLength) + minLong;
		
		//we make sure we are not generating values outside our box
		if(currLat < minLat){
			currLat = minLat;
		}
		if(currLat > maxLat){
			currLat = maxLat;
		}	
		if(currLong < minLong){
			currLong = minLong;
		}
		if(currLong > maxLong){
			currLong = maxLong;
		}
		
		//find the "square" around the click point with radius
		//we use metrics that align closely with the New York area
		//would have to be changed for other geographic areas.
		//we use one degree of latitude = 111030m
		//we use longitude at 40 degrees north, one degree of longitude = 85390m
		int latScale = 111030;
		int longScale = 85390;
		
		float squareRadiusLat = ((float)squareRadius/latScale);
		float squareRadiusLong = ((float)squareRadius/longScale);
		
		float minSquareLat = currLat - squareRadiusLat;
		float maxSquareLat = currLat + squareRadiusLat;
		float minSquareLong = currLong - squareRadiusLong;
		float maxSquareLong = currLong + squareRadiusLong;
		
		//we make sure we are not generating values outside our box
		if(minSquareLat < minLat){
			minSquareLat = minLat;
		}
		if(maxSquareLat > maxLat){
			maxSquareLat = maxLat;
		}	
		if(minSquareLong < minLong){
			minSquareLong = minLong;
		}
		if(maxSquareLong > maxLong){
			maxSquareLong = maxLong;
		}
		
		//returns the index of the first data point with a start latitude above our minimum latitude
		int goodIndex = binSetup(minSquareLat, maxSquareLat, minSquareLong, maxSquareLong);
		
		ArrayList<Integer> endXPixels = new ArrayList<Integer>();
		ArrayList<Integer> endYPixels = new ArrayList<Integer>();
		
		while(dataArray[goodIndex][1] < maxSquareLat){
			if(minSquareLong < dataArray[goodIndex][0] && dataArray[goodIndex][0] < maxSquareLong){
				//end latitude to pixel
				int y = (int) (((1 - ((dataArray[goodIndex][3] - minLat) / latLength)) * photoY));
				int x = (int) ((dataArray[goodIndex][2] - minLong) / longLength * photoX);
				endYPixels.add(y);
				endXPixels.add(x);
			}
			else{
				// doesn't match (too far east or west)
			}
			goodIndex++;
		}
		
		ArrayList<ArrayList<Integer>> returnArray = new ArrayList<ArrayList<Integer>>();
		returnArray.add(endXPixels);
		returnArray.add(endYPixels);
		return returnArray;
	}
	
	static int binSetup(float minSquareLat, float maxSquareLat, float minSquareLong, float maxSquareLong) throws FileNotFoundException
	{
		//read data from file
		File dataFile = new File("FINRA/sorted_data.csv");
		BufferedReader dataBR = new BufferedReader(new FileReader(dataFile));
		Scanner dataSC = new Scanner(dataBR);
		
		int i = 0;
		while(dataSC.hasNext()){
			String info = dataSC.nextLine();
			@SuppressWarnings("resource")
			Scanner infoSort = new Scanner(info).useDelimiter(",");
			try{
				infoSort.next();
				infoSort.next();
				// long_initial, lat_initial, long_final, lat_final
				dataArray[i][0] = Float.parseFloat(infoSort.next());
				dataArray[i][1] = Float.parseFloat(infoSort.next());
				dataArray[i][2] = Float.parseFloat(infoSort.next());
				dataArray[i][3] = Float.parseFloat(infoSort.next());
					
				i++;
			}
			catch(Exception ex){
				System.out.println("Error with data in sorted file");
				System.exit(1);
			}
		}
		dataSC.close();
		
		//binary search for minimum starting latitude
		//modified binary search that finds smallest value greater than our value
		int minLatPointer = 0;
		int maxLatPointer = 9_999_999;
		int minGoodDataPointer = binSearch(minLatPointer, maxLatPointer, minSquareLat);
		return minGoodDataPointer;
	}
	
	static int binSearch(int minLatPointer, int maxLatPointer, float minSquareLat)
	{
		int mid = (minLatPointer + maxLatPointer)/2;
		
		if(dataArray[mid][1] >= minSquareLat) {
			if (mid == 0) {
				return 0;
			} else if (dataArray[mid - 1][1] < minSquareLat){
				return mid;
			} else{
				return binSearch(minLatPointer, mid - 1, minSquareLat);
			}		
		}
		else{
			return binSearch(mid + 1, maxLatPointer, minSquareLat);
		}
	}
	
}
