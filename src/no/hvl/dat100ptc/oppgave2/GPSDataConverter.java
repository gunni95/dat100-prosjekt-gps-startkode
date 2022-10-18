package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 

	public static int toSeconds(String timestr) {
		int TIME_STARTINDEX = 11;
		
		int hr = Integer.parseInt(timestr.substring(TIME_STARTINDEX, TIME_STARTINDEX+2)) * 3600; //secs in hr
		TIME_STARTINDEX += 3;
		int min = Integer.parseInt(timestr.substring(TIME_STARTINDEX, TIME_STARTINDEX+2)) * 60; //secs in min
		TIME_STARTINDEX += 3;
		int sec = Integer.parseInt(timestr.substring(TIME_STARTINDEX, TIME_STARTINDEX+2));
		
		return hr + min + sec;
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		int sec = toSeconds(timeStr);
		double lat = Double.parseDouble(latitudeStr);
		double lon = Double.parseDouble(longitudeStr);
		double ele = Double.parseDouble(elevationStr);
		
		return new GPSPoint(sec, lat, lon, ele);
	}
	
}
