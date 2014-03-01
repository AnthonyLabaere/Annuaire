package geography;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

import constantes.IConstantes;

/**
 * Cette classe liste des fonctions appelant le webservice "Google geocoder"
 * (Utilisation de classes internes (Coords et non LatLng) afin de garder une
 * meilleur independance (pour pouvoir aisement changer de webservices))
 * 
 * @author Anthony
 * 
 */
public class GeocoderUtil {

	/** Appel au webservice Geocoder */
	private static GeocodeResponse appelWebserviceGeocoder(String adresse) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
		        .setAddress(adresse).setLanguage("en").getGeocoderRequest();
		return geocoder.geocode(geocoderRequest);
	}

	/**
	 * Retourne le couple de coordonnees GPS (longitude, latitude) en fonction
	 * d'une adresse passee en parametre. Si erreur lors de la transmission, la
	 * Coords renvoyee est vide
	 */
	private static Coordonnees getCoordonneesSelonGeocodeResponse(
	        GeocodeResponse geocoderResponse) {
		LatLng latlng;
		Coordonnees coordonnees;
		// Si le status est "OK"
		if (geocoderResponse.getStatus().toString()
		        .equals(IConstantes.GEOCODER_STATUS_OK)) {
			latlng = geocoderResponse.getResults().get(0).getGeometry()
			        .getLocation();
			coordonnees = new Coordonnees(latlng.getLng().doubleValue(), latlng
			        .getLat().doubleValue());
		} else {
			coordonnees = new Coordonnees();
		}

		return coordonnees;
	}

	/**
	 * Fonction renvoyant le couple de coordonnees GPS avec comme parametre une
	 * adresse
	 */
	private static Coordonnees getCoordonneesSelonAdresse(String adresse) {
		GeocodeResponse geocoderResponse = appelWebserviceGeocoder(adresse);
		Coordonnees coordonnees = getCoordonneesSelonGeocodeResponse(geocoderResponse);

		System.out.println("lat : " + coordonnees.getLatitude() + "\nlong : "
		        + coordonnees.getLongitude());

		return coordonnees;
	}

	/**
	 * Fonction renvoyant le couple de coordonnees GPS avec comme parametre un
	 * pays
	 */
	public static Coordonnees getCoordonneesSelonPays(String pays) {
		return getCoordonneesSelonAdresse(pays);
	}

	/**
	 * Fonction renvoyant le couple de coordonnees GPS avec comme parametre une
	 * ville et un pays
	 */
	public static Coordonnees getCoordonneesSelonVillePays(String ville,
	        String pays) {
		return getCoordonneesSelonAdresse(ville + ", " + pays);
	}
	
	public static String test() {
		return "test";
	}
}
