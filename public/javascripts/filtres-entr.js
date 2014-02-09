/**
 * Cette fonction est appelee lorsque le filtre pays est modifie : les autres
 * filtres sont alors modifies en consequence en interrogant la BDD. Si le
 * filtre pays n'etait pas renseigne alors le filtre ville apparait. La carte
 * zoome sur le pays concerne. Les marqueurs des villes où des centraliens sont
 * presents apparaissent
 */
function onChangeFiltrePays() {
	console.log("onChangeFiltrePays");

	if (! HTML('filtre_ville')) {
		villeTd1 = document.createElement('td');
		villeTd1.innerHTML = 'Ville';

		filtre_ville = document.createElement('select');
		filtre_ville.setAttribute('name', 'Ville');
		filtre_ville.setAttribute('id', 'filtre_ville');
		
		filtre_ville_option_par_defaut = document.createElement('option');
		filtre_ville_option_par_defaut.innerHTML = 'S&eacute;lectionnez le Ville recherch&eacute;e';
		
		filtre_ville.appendChild(filtre_ville_option_par_defaut);

		villeTd2 = document.createElement('td');
		villeTd2.appendChild(filtre_ville);

		villeTr = document.createElement('tr');
		villeTr.appendChild(villeTd1);
		villeTr.appendChild(villeTd2);

		HTML('tableau_critere').appendChild(villeTr);
	}	

	miseAJourDesFiltres();
}

/**
 * Cette fonction est appelee lorsque l'un des filtres a ete modifie. Les autres
 * filtres sont mis a jour en consequence apres requetage de la BDD
 */
function miseAJourDesFiltres() {
	alimentation_filtreVille(HTML('filtre_pays').value);

}
