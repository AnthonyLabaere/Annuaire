var ARRAY_FILTRE_CENTRALIEN=new Array();var ARRAY_FILTRE_ANNEEPROMOTION=new Array();var ARRAY_FILTRE_ECOLE=new Array();var ARRAY_FILTRE_ENTREPRISE=new Array();var ARRAY_FILTRE_SECTEUR=new Array();var ARRAY_FILTRE_PAYS=new Array();var ARRAY_FILTRE_VILLE=new Array();ARRAY_FILTRE_CENTRALIEN.push('filtre_centralien');ARRAY_FILTRE_ANNEEPROMOTION.push('filtre_anneePromotion');ARRAY_FILTRE_ECOLE.push('filtre_ecole');ARRAY_FILTRE_ENTREPRISE.push('filtre_entreprise');ARRAY_FILTRE_SECTEUR.push('filtre_secteur');ARRAY_FILTRE_PAYS.push('filtre_pays');ARRAY_FILTRE_VILLE.push('filtre_ville');ARRAY_FILTRE_CENTRALIEN.push('S&eacute;lectionnez le Centralien recherch&eacute;');ARRAY_FILTRE_ANNEEPROMOTION.push('S&eacute;lectionnez l\'Ann&eacute;e de promotion recherch&eacute;e');ARRAY_FILTRE_ECOLE.push('S&eacute;lectionnez l\'&Eacute;cole recherch&eacute;e');ARRAY_FILTRE_ENTREPRISE.push('S&eacute;lectionnez l\'Entreprise recherch&eacute;e');ARRAY_FILTRE_SECTEUR.push('S&eacute;lectionnez le Secteur recherch&eacute;');ARRAY_FILTRE_PAYS.push('S&eacute;lectionnez le Pays recherch&eacute;');ARRAY_FILTRE_VILLE.push('S&eacute;lectionnez la Ville recherch&eacute;e');var ORDRE_ACTIVATION_DERNIERE_VALEUR=1;var ORDRE_ACTIVATION_PAR_DEFAUT=-1;ARRAY_FILTRE_CENTRALIEN.push(ORDRE_ACTIVATION_PAR_DEFAUT);ARRAY_FILTRE_ANNEEPROMOTION.push(ORDRE_ACTIVATION_PAR_DEFAUT);ARRAY_FILTRE_ECOLE.push(ORDRE_ACTIVATION_PAR_DEFAUT);ARRAY_FILTRE_ENTREPRISE.push(ORDRE_ACTIVATION_PAR_DEFAUT);ARRAY_FILTRE_SECTEUR.push(ORDRE_ACTIVATION_PAR_DEFAUT);ARRAY_FILTRE_PAYS.push(ORDRE_ACTIVATION_PAR_DEFAUT);ARRAY_FILTRE_VILLE.push(ORDRE_ACTIVATION_PAR_DEFAUT);var ARRAY_FILTRE_ID=0;var ARRAY_FILTRE_OPTION_PAR_DEFAUT=1;var ARRAY_FILTRE_ORDRE_ACTIVATION=2;var ARRAY_FILTRES=new Array();ARRAY_FILTRES.push(ARRAY_FILTRE_CENTRALIEN);ARRAY_FILTRES.push(ARRAY_FILTRE_ANNEEPROMOTION);ARRAY_FILTRES.push(ARRAY_FILTRE_ECOLE);ARRAY_FILTRES.push(ARRAY_FILTRE_ENTREPRISE);ARRAY_FILTRES.push(ARRAY_FILTRE_SECTEUR);ARRAY_FILTRES.push(ARRAY_FILTRE_PAYS);ARRAY_FILTRES.push(ARRAY_FILTRE_VILLE);var ARRAY_FILTRES_CENTRALIEN=0;var ARRAY_FILTRES_ANNEEPROMOTION=1;var ARRAY_FILTRES_ECOLE=2;var ARRAY_FILTRES_ENTREPRISE=3;var ARRAY_FILTRES_SECTEUR=4;var ARRAY_FILTRES_PAYS=5;var ARRAY_FILTRES_VILLE=6;var ECOLE_OU_ENTREPRISE_INACTIF="ECOLE_OU_ENTREPRISE_INACTIF";var CHECKBOX_HISTORIQUE_ID="historique";var CARTE;var DIV_CARTE_ID="carte";var POSITION_INITIALE=new OpenLayers.LonLat(-1.583,47.233);var ZOOM_INITIAL=6;var NOMBRE_LIGNES=2;var VIDE="";