package no.hvl.dat100ptc.oppgave3;

import java.sql.Time;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] numberList) {
		double max = numberList[0];
		
		for (double d : numberList) {
			max = d > max ? d : max;
		}
		
		return max;
	}

	public static double findMin(double[] numberList) {
		double min = numberList[0];
		
		for (int i = 1; i < numberList.length; i++) {
			min = numberList[i] < min ? numberList[i] : min;
		}
		
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double[] output = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			output[i] = gpspoints[i].getLatitude();
		}
		
		return output;
	}

	//laget ekstra metode for å hente alle elevations for bruk i andre metoder
	public static double[] getElevations(GPSPoint[] gpspoints) {
		double[] output = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			double change = gpspoints[i+1].getElevation() - gpspoints[i].getElevation();
			output[i] = change > 0 ? change : 0;
		}
		
		return output;
	}
	
	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		double[] output = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			output[i] = gpspoints[i].getLongitude();
		}
		
		return output;
	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		double lat1 = gpspoint1.getLatitude();
		double lon1 = gpspoint1.getLongitude();
		
		double lat2 = gpspoint2.getLatitude();
		double lon2 = gpspoint2.getLongitude();
		
		
		double rLat = Math.toRadians(lat2 - lat1);
		double rLon = Math.toRadians(lon2 - lon1);
		
		double a = (Math.pow(Math.sin(rLat/2),2)
                +(Math.cos((Math.toRadians(lat1)))
                *(Math.cos(Math.toRadians(lat2)))
                *(Math.pow(Math.sin(rLon/2),2))));
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return R * c;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		//gets distance using distance function and divides on delta time
		return Math.abs(distance(gpspoint1, gpspoint2) / (gpspoint2.getTime() - gpspoint1.getTime()) * 3.6);
		
	}

	public static String formatTime(int time) {
		String TIMESEP = ":";
		int hrs = time / 3600;
		time -= (hrs * 3600);
		int mins = time / 60;
		time -= (mins * 60);
		int secs = time;
		
		String clockString = String.format(("%02d" + TIMESEP + "%02d" + TIMESEP + "%02d"), hrs, mins, secs);
		return String.format("%10s", clockString);

	}
	
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {	
		return String.format("%" + TEXTWIDTH + "s", String.format("%.2f", d));
	}
}
