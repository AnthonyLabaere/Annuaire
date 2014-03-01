package geography;

/**
 * Cette classe permet de manipuler des coordonnees GPS
 * 
 * @author Anthony
 *
 */
public class Coordonnees {

	/** Longitude GPS */
	private double longitude;

	/** Latitude GPS */
	private double latitude;

	public Coordonnees() {

	}

	public Coordonnees(double longitudeParam, double latitudeParam) {
		this.longitude = longitudeParam;
		this.latitude = latitudeParam;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
