package no.hvl.dat100ptc.oppgave4;

import java.lang.System.Logger;

import javax.print.DocFlavor.BYTE_ARRAY;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {
		
		double distance = 0;
		for (int i = 1; i < gpspoints.length; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i-1]);
		}
		
		return distance;
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length-1; i++) {
			double change = gpspoints[i+1].getElevation() - gpspoints[i].getElevation();
			elevation += change > 0 ? change : 0;
		}
		
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		return gpspoints[gpspoints.length -1].getTime() - gpspoints[0].getTime();
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		double[] output = new double[gpspoints.length-1];
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			output[i] = GPSUtils.speed(gpspoints[i+1], gpspoints[i]);
		}
		
		return output;
	}
	
	//la til en egen funskjon i tilfelle vi vil finne alle hastighetene
	public int[] times() {
		int[] output = new int[gpspoints.length-1];
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			output[i] = gpspoints[i+1].getTime() - gpspoints[i].getTime();
		}
		
		return output;
	}
	
	public double maxSpeed() {
		double maxspeed = 0;
		
		for (int i = 1; i < gpspoints.length; i++) {
			Double currSpeed = GPSUtils.speed(gpspoints[i-1], gpspoints[i]);
			maxspeed = currSpeed > maxspeed ? currSpeed : maxspeed;
		}
		
		return maxspeed;
		
	}

	public double averageSpeed() {
		return totalDistance() / totalTime() * 3.6;
		
	}

	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {
		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;
		
		
		if (speedmph > 20) {
			met = 16.0;
		} else if (speed > 16) {
			met = 12.0;
		} else if (speed > 14) {
			met = 10.0;
		} else if (speed > 12) {
			met = 8.0;
		} else if (speed > 10) {
			met = 6.0;
		} else if (speed > 0) {
			met = 4.0;
		}

		return (secs / 3600.0) * met * weight;
		
	}

	public double totalKcal(double weight) {

		double output = 0;
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			double speed = GPSUtils.speed(gpspoints[i], gpspoints[i+1]) / 3.6;
			int time = Math.abs(gpspoints[i +1].getTime() - gpspoints[i].getTime());
			
			
			double speedInMS = speed * 0.6;
			output += kcal(weight, time, speedInMS);
		}
		
		return output;
	}
	
	private static double WEIGHT = 80.0;
	
	//lagt til en egen funskjon for å simplifisere kall for å hente data siden den både skal skrives ut i terminal og skrives i java vinduet.
	public String[] statistics() {
		//Lager en datastruktur noe nærmere hva man hadde fått fra en ekte database + gjøres mer dynamisk;
		String[][] data = {
			{"Total Time", GPSUtils.formatTime(this.totalTime())},
			{"Total distance", GPSUtils.formatDouble(this.totalDistance()) + " km"},
			{"Total elevation",GPSUtils.formatDouble(this.totalElevation()) + " m" },
			{"Max speed", GPSUtils.formatDouble(this.maxSpeed()) + " km/t"},
			{"Average speed", GPSUtils.formatDouble(this.averageSpeed()) + " km/t"},
			{"Energy", GPSUtils.formatDouble(this.totalKcal(WEIGHT)) + " kcal"}
		};
		
		String[] stats = new String[data.length];
		
		int spacing = 3;
		
		int lDesc = data[0][0].length();
		int lData = data[0][1].length();
		
		for (String[] stat : data) {
			lDesc = stat[0].length() > lDesc ? stat[0].length() : lDesc;
			lData = stat[1].length() > lData ? stat[1].length() : lData;
		}
		
		for (int i = 0; i < data.length; i++) {
			//formats label and string to fit its longest counterpart + 3 spacing;
			String labelString = String.format("%-" + (lDesc + spacing) + "s", data[i][0]);
			String dataString = String.format("%" + (lData + spacing) + "s", data[i][1]);
			
			
			stats[i] = labelString + ":" + dataString;
		}
		
		return stats;
	}
	
	
	public void displayStatistics() {

		System.out.println("==============================================");
		
		for (String stat : statistics()) {
			System.out.println(stat);
		}
		
		System.out.println("==============================================");
	}

}
