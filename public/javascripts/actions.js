/**
 * -----------------------------------------------------------------------------
 * Ce fichier contient de maniere EXHAUSTIVE les actions a mener lors d'un clic
 * sur un element de la vue. Il permet de detailler clairement les actions a
 * mener
 * -----------------------------------------------------------------------------
 */

/** Cette action reinitialise un filtre et met a jour les marqueurs */
function action_reset(bouton_reset_ID) {
	// Reinitialisation du filtre en question
	reset(bouton_reset_ID);
	
	// Mise a jour des marqueurs
	miseAjourDesMarqueurs();
}

/** Cette action reinitialise tous les filtres et met a jour les marqueurs */
function action_resetAll() {
	// Reinitialisation de tous les filtres
	resetAll();

	// Mise a jour des marqueurs
	miseAjourDesMarqueurs();
}

/** Cette action met a jour les filtres et les marqueurs */
function action_modificationFiltre(filtre_ID){
	// Mise a jour des filtres
	miseAJourDesFiltres(filtre_ID);
	
	// Mise a jour des marqueurs
	miseAjourDesMarqueurs();
}

/** Cette action met a jour les filtres EcoleOuEntreprise et les marqueurs*/
function action_miseAJourEcoleOuEntreprise(){
	// Mise a jour du filtre
	miseAJourEcoleOuEntreprise();
	
	// Mise a jour des marqueurs
	miseAjourDesMarqueurs();
}

/** Cette action met a jour les filtres et les marqueurs*/
function action_historique(){
	// Mise a jour des filtres
	resetAll()
	
	// Mise a jour des marqueurs
	miseAjourDesMarqueurs();
}

