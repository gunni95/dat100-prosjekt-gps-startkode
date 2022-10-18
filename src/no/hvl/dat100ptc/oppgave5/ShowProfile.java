package no.hvl.dat100ptc.oppgave5;

import easygraphics.EasyGraphics;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import javax.swing.JOptionPane;

public class ShowProfile extends EasyGraphics {

	private static final int MARGIN = 50;  // margin on the sides 
	
	private static int MAXBARHEIGHT = 500; // assume no height above 500 meters
	
	private GPSPoint[] gpspoints;

	public ShowProfile() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		GPSComputer gpscomputer =  new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length; // number of data points

		makeWindow("Height profile", 2 * MARGIN + 3 * N, 2 * MARGIN + MAXBARHEIGHT);

		// top margin + height of drawing area
		showHeightProfile(MARGIN + MAXBARHEIGHT); 
	}

	public void showHeightProfile(int ybase) {
		int x = MARGIN;

		for (int i = 0; i < gpspoints.length-1; i++) {
			int xStart = x;
			
			int yStart = ybase;
			
			int xEnd = x;
			
			double heightPercentageOfBar = gpspoints[i].getElevation() / GPSUtils.findMax(GPSUtils.getElevations(gpspoints));
			int yEnd = yStart - (int)Math.abs(MAXBARHEIGHT * (int)heightPercentageOfBar);
			
			drawLine(xStart, yStart, xEnd, yEnd);
			x += 3;
		}
	}

}
