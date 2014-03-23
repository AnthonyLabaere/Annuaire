package geography;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;

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
	
	public ThreadGeocoder(){
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
		String sql = "SELECT pays_ID, pays_nomMinuscule FROM Pays";
		sql += " WHERE ";
		sql += "(pays_latitude IS NULL OR pays_latitude = 0)";
		sql += " OR ";
		sql += "pays_longitude IS NULL OR pays_longitude = 0";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();

		List<String[]> listeDesPays = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("pays_ID").toString();
			String nom = sqlRow.get("pays_nomMinuscule").toString();
			listeDesPays.add(new String[] { identifiant, nom });
		}

		for (String[] pays : listeDesPays) {
			Coordonnees coordonnees = GeocoderUtil
			        .getCoordonneesSelonPays(pays[1]);

			sql = "UPDATE Pays SET pays_latitude = :latitude, pays_longitude = :longitude WHERE pays_ID = :identifiant";
			SqlUpdate sqlUpdate = Ebean.createSqlUpdate(sql);
			sqlUpdate.setParameter("latitude", coordonnees.getLatitude());
			sqlUpdate.setParameter("longitude", coordonnees.getLongitude());
			sqlUpdate.setParameter("identifiant", Integer.parseInt(pays[0]));
			sqlUpdate.execute();

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
		String sql = "SELECT ville_ID, ville_nom FROM Ville WHERE ville_latitude IS NULL OR ville_longitude IS NULL";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();

		List<String[]> listeDesVilles = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("ville_ID").toString();
			String nom = sqlRow.get("ville_nom").toString();
			listeDesVilles.add(new String[] { identifiant, nom });
		}

		for (String[] ville : listeDesVilles) {
			Coordonnees coordonnees = GeocoderUtil
			        .getCoordonneesSelonPays(ville[1]);

			sql = "UPDATE Ville SET ville_latitude = :latitude, ville_longitude = :longitude WHERE ville_ID = :identifiant";
			SqlUpdate sqlUpdate = Ebean.createSqlUpdate(sql);
			sqlUpdate.setParameter("latitude", coordonnees.getLatitude());
			sqlUpdate.setParameter("longitude", coordonnees.getLongitude());
			sqlUpdate.setParameter("identifiant", Integer.parseInt(ville[0]));
			sqlUpdate.execute();

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
