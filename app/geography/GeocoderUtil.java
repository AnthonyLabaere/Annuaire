package geography;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;
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
	
	public static void alimenterPays() {
		String sql = "SELECT pays_ID, pays_nom FROM Pays WHERE pays_latitude IS NULL OR pays_latitude IS NULL";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();

		List<String[]> listeDesPays = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("pays_ID").toString();
			String nom = sqlRow.get("pays_nom").toString();
			listeDesPays.add(new String[] { identifiant, nom });
		}
		
		for (String[] pays : listeDesPays){
			Coordonnees coordonnees = getCoordonneesSelonPays(pays[1]);
			
			sql = "UPDATE Pays SET pays_latitude = :latitude, pays_longitude = :longitude WHERE pays_ID = :identifiant";
			SqlUpdate sqlUpdate = Ebean.createSqlUpdate(sql);
			sqlUpdate.setParameter("latitude", coordonnees.getLatitude());
			sqlUpdate.setParameter("longitude", coordonnees.getLongitude());
			sqlUpdate.setParameter("identifiant", Integer.parseInt(pays[0]));
			sqlUpdate.execute();	
		}
		
		
	}
}
