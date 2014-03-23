package geography;

import java.util.List;

import controllers.ServicePays;
import controllers.ServiceVille;

/**
 * Ce thread appelle le webservice de Google pour alimenter les bases en
 * coordonnees geographiques
 * 
 * @author anthony
 * 
 */
public class ThreadGeocoder extends Thread {

	/**
	 * Temps d'attente entre deux requetes a Geocoder (car nombre de requetes
	 * par seconde limitees)
	 */
	private static final int TEMPS_ENTRE_REQUETE = 500;

	/**
	 * Il ne peut avoir qu'un seul Thread de ce type en instance
	 */
	public static boolean actif = false;

	public ThreadGeocoder() {
		actif = true;
	}

	public void run() {
		alimenterPays();
		alimenterVilles();
		actif = false;
	}

	/**
	 * Cette fonction alimente en coordonnees geographiques les pays qui en sont
	 * depourvus
	 */
	private void alimenterPays() {
		List<String[]> listeDesPays = ServicePays.paysSansCoordonnees();

		for (String[] pays : listeDesPays) {
			ServicePays.updateCoordonneesPays(pays[0],
			        GeocoderUtil.getCoordonneesSelonPays(pays[1]));

			// On attend TEMPS_ENTRE_REQUETE/1000 secondes avant la requete
			// suivante
			try {
				Thread.sleep(TEMPS_ENTRE_REQUETE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Cette fonction alimente en coordonnees geographiques les villes qui en
	 * sont depourvus
	 */
	private void alimenterVilles() {
		List<String[]> listeDesVilles = ServiceVille.villesSansCoordonnees();

		for (String[] ville : listeDesVilles) {
			ServiceVille.updateCoordonneesVille(ville[0],
			        GeocoderUtil.getCoordonneesSelonPays(ville[1]));

			// On attend TEMPS_ENTRE_REQUETE/1000 secondes avant la requete
			// suivante
			try {
				Thread.sleep(TEMPS_ENTRE_REQUETE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
