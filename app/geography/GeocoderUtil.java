/****************************************************************************

Copyright 2014 Anthony Labaere

Contributeurs : 
François Neber francois.neber@centraliens-nantes.net
Malik Olivier Boussejra malik.boussejra@centraliens-nantes.net
Anthony Labaere anthony.labaere@centraliens-nantes.net

Ce logiciel est un programme informatique ayant pour but de faciliter 
les contacts entre étudiants et diplômés de l'École Centrale Nantes 
à l'étranger comme en France.

Ce logiciel est régi par la licence CeCILL soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant 
donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
pris connaissance de la licence CeCILL et que vous en avez accepté les
termes.

******************************************************************************/

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
			 System.out.println("STATUS EN ERREUR DU WEBSERVICE GEOCODER : " + geocoderResponse.getStatus());
			coordonnees = new Coordonnees();
		}
		// System.out.println("Latitude : " + coordonnees.getLatitude()
		// + " longitude : " + coordonnees.getLongitude());

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
		System.out.println("Pays : " + pays);

		return getCoordonneesSelonAdresse(pays);
	}

	/**
	 * Fonction renvoyant le couple de coordonnees GPS avec comme parametre une
	 * ville et un pays
	 */
	public static Coordonnees getCoordonneesSelonVillePays(String ville,
	        String pays) {
		System.out.println("Ville : " + ville);
		
		return getCoordonneesSelonAdresse(ville + ", " + pays);
	}

}
