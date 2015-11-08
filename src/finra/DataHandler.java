package finra;

import java.io.*;
import java.util.*;

/* A class for sorting and handling the raw data */
public class DataHandler {
	
	static float minLat, maxLat, minLong, maxLong;

	public static void main(String[] args) throws IOException {
		updateParams();
		sortRaw();
	}
	
	@SuppressWarnings("resource")
	public static void updateParams() {
		try {
			File file = new File("param1.csv");
			BufferedReader br = new BufferedReader(new FileReader(file));
			Scanner sc = new Scanner(br);
			sc.nextLine();
			String line = sc.nextLine();
			Scanner lineSc = new Scanner(line).useDelimiter(",");
			minLat = Float.parseFloat(lineSc.next()); 
			maxLat = Float.parseFloat(lineSc.next());
			minLong = Float.parseFloat(lineSc.next()); 
			maxLong = Float.parseFloat(lineSc.next());
		} catch (IOException io) {
			System.out.println("file i/o error in getParams (DataHandler.java)");
		}
	}
	
	@SuppressWarnings("resource")
	public static void sortRaw() throws IOException {
		File raw = new File("FINRA/trip_data_1.csv");
		File sorted = new File("FINRA/sorted_data.csv");
		BufferedReader br = new BufferedReader(new FileReader(raw));
		Scanner sc = new Scanner(br);
		sc.nextLine(); // skip first line
		ArrayList<Trip> trips = new ArrayList<Trip>();
		int ct = 0;
		
		while (trips.size() < 10_000_000) {
			ct++;
			if (ct % 1_000_000 == 0) System.out.println(ct);
			String line = sc.nextLine();
			Scanner lineSc = new Scanner(line).useDelimiter(",");
			// throw out unnecessary data; sort by latitude
			try {
				for (int i = 0; i < 8; i++) {
					lineSc.next();
				}
				Trip t = new Trip(lineSc.next(), lineSc.next(), Float.parseFloat(lineSc.next()), 
						Float.parseFloat(lineSc.next()), Float.parseFloat(lineSc.next()), 
						Float.parseFloat(lineSc.next()));
				if (t.long_initial > maxLong || t.long_initial < minLong || t.lat_initial < minLat ||
						t.lat_initial > maxLat || t.long_final > maxLong || t.long_final < minLong ||
						t.lat_final < minLat || t.lat_final > maxLat) { 
					// excluding all points outside Manhattan
					continue;
				} else {
					trips.add(t);
				}
			} catch (Exception e) {
				// error in data is ignored
			}
		}
		sc.close();
		// sort 14 million trips by initial latitude
		Collections.sort(trips, new CustomComparator());
		System.out.println("sorted all trips");
		// output to file
		BufferedWriter bw = new BufferedWriter(new FileWriter(sorted));
		for (int i = 0; i < trips.size(); i++) {
			bw.write(trips.get(i).toString());
			bw.newLine();
		}
		
		bw.close();
	}

}
