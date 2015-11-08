package finra;

import java.util.*;

public class Trip {
	String time, dist;
	float long_initial, lat_initial, long_final, lat_final;

	public Trip (String time, String dist, float long_initial, float lat_initial, 
			float long_final, float lat_final) {
		this.time = time;
		this.dist = dist;
		this.lat_initial = lat_initial;
		this.lat_final = lat_final;
		this.long_initial = long_initial;
		this.long_final = long_final;
	}
	
	@Override
	public String toString() {
		String comma = ",";
		return time + comma + dist + comma + long_initial + comma + lat_initial + comma + long_final +
				comma + lat_final;
	}
}

class CustomComparator implements Comparator<Trip> {
    @Override
    public int compare(Trip t1, Trip t2) {
        if (t1.lat_initial > t2.lat_initial) {
        	return 1;
        } else return -1;
    }
}
