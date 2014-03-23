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
	resetAll();
	
	// Mise a jour des marqueurs
	miseAjourDesMarqueurs();
}

