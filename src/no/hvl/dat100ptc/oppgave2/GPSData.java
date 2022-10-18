package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {
		this.gpspoints = new GPSPoint[n];
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {
		if (antall >= gpspoints.length) {
			return false;
		}
		
		gpspoints[this.antall] = gpspoint;
		this.antall++;
		
		return true;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {
		return this.insertGPS(GPSDataConverter.convert(time, latitude, longitude, elevation));
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for (GPSPoint iGpsPoint : this.gpspoints) {
			System.out.println(iGpsPoint.toString());
		}
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
