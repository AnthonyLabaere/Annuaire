function reset(bouton_reset_ID){var filtre_ID="filtre_"+bouton_reset_ID.split("_")[2];HTML(filtre_ID).selectedIndex=0;miseAJourDesFiltres(filtre_ID)}function resetAll(){if(HTML(ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID]).selectedIndex!=0||HTML(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]).selectedIndex!=0||(HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])&&HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]).selectedIndex!=0)||(HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])&&HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]).selectedIndex!=0)||HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]).selectedIndex!=0||HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex!=0||(HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID])&&HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]).selectedIndex!=0)){for(ARRAY_FILTRES_ID in ARRAY_FILTRES){var filtre=HTML((ARRAY_FILTRES[ARRAY_FILTRES_ID])[ARRAY_FILTRE_ID]);if(!((ARRAY_FILTRES_ID==ARRAY_FILTRES_ECOLE||ARRAY_FILTRES_ID==ARRAY_FILTRES_ENTREPRISE||ARRAY_FILTRES_ID==ARRAY_FILTRES_VILLE)&&!filtre)){filtre.selectedIndex=0}}miseAJourDesFiltres();ORDRE_ACTIVATION_DERNIERE_VALEUR=1;ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_PAR_DEFAUT;ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_PAR_DEFAUT;ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_PAR_DEFAUT;ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_PAR_DEFAUT;ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_PAR_DEFAUT;ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_PAR_DEFAUT;ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_PAR_DEFAUT}}function miseAJourEcoleOuEntreprise(){var filtre_ecoleOuEntreprise=HTML('filtre_ecoleOuEntreprise').value;var td_ecoleOuEntreprise=HTML('td_ecoleOuEntreprise');td_ecoleOuEntreprise.innerHTML='';var filtre=document.createElement('select');var filtre_option_par_defaut=document.createElement('option');if(filtre_ecoleOuEntreprise=='filtre_entreprise'){filtre.setAttribute('name','Entreprise');filtre.setAttribute('id',ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]);filtre_option_par_defaut.innerHTML=ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT]}else{filtre.setAttribute('name','Ecole');filtre.setAttribute('id',ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]);filtre_option_par_defaut.innerHTML=ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT]}filtre.setAttribute('onChange','action_modificationFiltre(this.id)');filtre.appendChild(filtre_option_par_defaut);HTML('td_ecoleOuEntreprise').appendChild(filtre);var bouton_reset=document.createElement('img');bouton_reset.setAttribute('src','/assets/images/reset.png');if(filtre_ecoleOuEntreprise=='filtre_entreprise'){bouton_reset.setAttribute('id','bouton_reset_entreprise');bouton_reset.setAttribute('alt','bouton_reset_entreprise');bouton_reset.setAttribute('title','R&eacute;initialisation du champ Entreprise')}else{bouton_reset.setAttribute('id','bouton_reset_ecole');bouton_reset.setAttribute('alt','bouton_reset_ecole');bouton_reset.setAttribute('title','R&eacute;initialisation du champ Ecole')}bouton_reset.setAttribute('onClick','action_reset(this.id)');bouton_reset.setAttribute('class','bouton_reset');var td_ecoleOuEntreprise_reset=HTML('td_ecoleOuEntreprise_reset');td_ecoleOuEntreprise_reset.innerHTML="";td_ecoleOuEntreprise_reset.appendChild(bouton_reset);resetAll();if(filtre_ecoleOuEntreprise=='filtre_entreprise'){initialisationFiltreEntreprise()}else{initialisationFiltreEcole()}}function selectionneArrayFiltreSelonID(filtre_ID){if(ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRE_CENTRALIEN}else if(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRE_ANNEEPROMOTION}else if(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRE_ECOLE}else if(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRE_ENTREPRISE}else if(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRE_SECTEUR}else if(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRE_PAYS}else if(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRE_VILLE}}function selectionneNumeroFiltreSelonID(filtre_ID){if(ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRES_CENTRALIEN}else if(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRES_ANNEEPROMOTION}else if(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRES_ECOLE}else if(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRES_ENTREPRISE}else if(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRES_SECTEUR}else if(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRES_PAYS}else if(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]==filtre_ID){return ARRAY_FILTRES_VILLE}}function calculTableauOrdreActivation(){var tableau_ordre_maj=new Array();var tableau_ordre_maj_idFiltre=new Array();for(id_array_filtres in ARRAY_FILTRES){var ordre_activation=ARRAY_FILTRES[id_array_filtres][ARRAY_FILTRE_ORDRE_ACTIVATION];if(ordre_activation>0){var taille_tableau_ordre_maj=tableau_ordre_maj.length;if(taille_tableau_ordre_maj==0){tableau_ordre_maj.push(ordre_activation);tableau_ordre_maj_idFiltre.push(id_array_filtres)}else{var increment=0;while(ordre_activation>tableau_ordre_maj[increment]&&increment<taille_tableau_ordre_maj){increment++}if(increment!=taille_tableau_ordre_maj){var precedent_ordre=0;var precedent_id=0;for(i=increment;i<taille_tableau_ordre_maj;i++){precedent_tmp=tableau_ordre_maj[i];tableau_ordre_maj[i]=precedent_ordre;precedent_ordre=precedent_tmp;precedent_tmp=tableau_ordre_maj_idFiltre[i];tableau_ordre_maj_idFiltre[i]=precedent_id;precedent_id=precedent_tmp}tableau_ordre_maj.push(precedent_ordre);tableau_ordre_maj_idFiltre.push(precedent_id);tableau_ordre_maj[increment]=ordre_activation;tableau_ordre_maj_idFiltre[increment]=id_array_filtres}else{tableau_ordre_maj.push(ordre_activation);tableau_ordre_maj_idFiltre.push(id_array_filtres)}}}}for(id_array_filtres in ARRAY_FILTRES){var ordre_activation=ARRAY_FILTRES[id_array_filtres][ARRAY_FILTRE_ORDRE_ACTIVATION];if(ordre_activation<0){tableau_ordre_maj_idFiltre.push(id_array_filtres)}}return tableau_ordre_maj_idFiltre}function miseAJourDesFiltres(filtre_ID){if(!filtre_ID){var centralien_ID=null;var anneePromotion_ID=null;var ecole_ID=null;var entreprise_ID=null;var secteur_ID=null;var pays_ID=null;var ville_ID=null;if(HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])){entreprise_ID=ECOLE_OU_ENTREPRISE_INACTIF}else{ecole_ID=ECOLE_OU_ENTREPRISE_INACTIF}miseAJourDuFiltreCentralien(anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID,ville_ID);miseAJourDuFiltreAnneePromotion(centralien_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID,ville_ID);if(HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])){miseAJourDuFiltreEcole(centralien_ID,anneePromotion_ID,secteur_ID,pays_ID,ville_ID)}else{miseAJourDuFiltreEntreprise(centralien_ID,anneePromotion_ID,secteur_ID,pays_ID,ville_ID)}miseAJourDuFiltreSecteur(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,pays_ID,ville_ID);miseAJourDuFiltrePays(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID);miseAJourDuFiltreVille(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID)}else{var arrayDuFiltreModifie=selectionneArrayFiltreSelonID(filtre_ID);var ordreActivationDuFiltreReinitialise=ARRAY_FILTRES.length+1;if(arrayDuFiltreModifie[ARRAY_FILTRE_ORDRE_ACTIVATION]!=ORDRE_ACTIVATION_PAR_DEFAUT){ordreActivationDuFiltreReinitialise=arrayDuFiltreModifie[ARRAY_FILTRE_ORDRE_ACTIVATION]}else{arrayDuFiltreModifie[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_DERNIERE_VALEUR;ORDRE_ACTIVATION_DERNIERE_VALEUR++}tableau_ordre_activation=calculTableauOrdreActivation();var numeroFiltreModifie=selectionneNumeroFiltreSelonID(filtre_ID);var numeroFiltreModifieDansOA=0;while(numeroFiltreModifie!=tableau_ordre_activation[numeroFiltreModifieDansOA]){numeroFiltreModifieDansOA++}var tailleTableauOA=tableau_ordre_activation.length;for(var i=numeroFiltreModifieDansOA+1;i<tailleTableauOA;i++){var centralien_ID;var anneePromotion_ID;var ecole_ID;var entreprise_ID;var secteur_ID;var pays_ID;var ville_ID;if(HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])){entreprise_ID=ECOLE_OU_ENTREPRISE_INACTIF}else{ecole_ID=ECOLE_OU_ENTREPRISE_INACTIF}for(var j=0;j<i;j++){if(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ORDRE_ACTIVATION]!=-1&&HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID])&&HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).selectedIndex!=0){if(tableau_ordre_activation[j]==ARRAY_FILTRES_CENTRALIEN){centralien_ID=HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value}else if(tableau_ordre_activation[j]==ARRAY_FILTRES_ANNEEPROMOTION){anneePromotion_ID=HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value}else if(tableau_ordre_activation[j]==ARRAY_FILTRES_ECOLE){ecole_ID=HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value}else if(tableau_ordre_activation[j]==ARRAY_FILTRES_ENTREPRISE){entreprise_ID=HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value}else if(tableau_ordre_activation[j]==ARRAY_FILTRES_SECTEUR){secteur_ID=HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value}else if(tableau_ordre_activation[j]==ARRAY_FILTRES_PAYS){pays_ID=HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value}else if(tableau_ordre_activation[j]==ARRAY_FILTRES_VILLE){ville_ID=HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value}}}if(tableau_ordre_activation[i]==ARRAY_FILTRES_CENTRALIEN){miseAJourDuFiltreCentralien(anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID,ville_ID)}if(tableau_ordre_activation[i]==ARRAY_FILTRES_ANNEEPROMOTION){miseAJourDuFiltreAnneePromotion(centralien_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID,ville_ID)}if(HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])&&tableau_ordre_activation[i]==ARRAY_FILTRES_ECOLE){miseAJourDuFiltreEcole(centralien_ID,anneePromotion_ID,secteur_ID,pays_ID,ville_ID)}if(HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])&&tableau_ordre_activation[i]==ARRAY_FILTRES_ENTREPRISE){miseAJourDuFiltreEntreprise(centralien_ID,anneePromotion_ID,secteur_ID,pays_ID,ville_ID)}if(tableau_ordre_activation[i]==ARRAY_FILTRES_SECTEUR){miseAJourDuFiltreSecteur(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,pays_ID,ville_ID)}if(tableau_ordre_activation[i]==ARRAY_FILTRES_PAYS){miseAJourDuFiltrePays(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID)}if(tableau_ordre_activation[i]==ARRAY_FILTRES_VILLE){miseAJourDuFiltreVille(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID)}}if(HTML(filtre_ID).selectedIndex=='0'){if(ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ORDRE_ACTIVATION]>ordreActivationDuFiltreReinitialise){ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ORDRE_ACTIVATION]--}if(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION]>ordreActivationDuFiltreReinitialise){ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION]--}if(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION]>ordreActivationDuFiltreReinitialise){ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION]--}if(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION]>ordreActivationDuFiltreReinitialise){ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION]--}if(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION]>ordreActivationDuFiltreReinitialise){ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION]--}if(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION]>ordreActivationDuFiltreReinitialise){ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION]--}if(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION]>ordreActivationDuFiltreReinitialise){ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION]--}arrayDuFiltreModifie[ARRAY_FILTRE_ORDRE_ACTIVATION]=-1}}}function miseAJourDuFiltre_AJAXSuccess(data,ARRAY_FILTRE){var filtre_ID=ARRAY_FILTRE[ARRAY_FILTRE_ID];var filtre=HTML(filtre_ID);var valeurPrecedemmentSelectionnee;if(filtre.selectedIndex!=0){valeurPrecedemmentSelectionnee=filtre.value}filtre.innerHTML="";filtre_option_par_defaut=document.createElement('option');filtre_option_par_defaut.innerHTML=ARRAY_FILTRE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];filtre.appendChild(filtre_option_par_defaut);for(var element in data){if(data[element][0]==valeurPrecedemmentSelectionnee){option_precedemment_selectionnee=document.createElement('option');option_precedemment_selectionnee.value=data[element][0];option_precedemment_selectionnee.text=data[element][1];option_precedemment_selectionnee.setAttribute('selected','selected');filtre.appendChild(option_precedemment_selectionnee)}else{var option=document.createElement('option');option.value=data[element][0];option.text=data[element][1];option.id=filtre_ID+"_"+data[element][0];if(filtre_ID==ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]||filtre_ID==ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]){option.setAttribute('latitude',data[element][2]);option.setAttribute('longitude',data[element][3]);option.setAttribute('zoom',data[element][4])}filtre.appendChild(option)}}}function miseAJourDuFiltreCentralien(anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID,ville_ID){jsRoutes.controllers.ServiceCentralien.AJAX_listeDesCentraliensSelonCriteres(HTML(CHECKBOX_HISTORIQUE_ID).checked,anneePromotion_ID?anneePromotion_ID:"",ecole_ID?ecole_ID:"",entreprise_ID?entreprise_ID:"",secteur_ID?secteur_ID:"",pays_ID?pays_ID:"",ville_ID?ville_ID:"").ajax({async:false,success:function(data,textStatus,jqXHR){miseAJourDuFiltre_AJAXSuccess(data,ARRAY_FILTRE_CENTRALIEN)}})}function miseAJourDuFiltreAnneePromotion(centralien_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID,ville_ID){jsRoutes.controllers.ServiceAnneePromotion.AJAX_listeDesAnneesPromotionSelonCriteres(centralien_ID?centralien_ID:"",ecole_ID?ecole_ID:"",entreprise_ID?entreprise_ID:"",secteur_ID?secteur_ID:"",pays_ID?pays_ID:"",ville_ID?ville_ID:"").ajax({async:false,success:function(data,textStatus,jqXHR){miseAJourDuFiltre_AJAXSuccess(data,ARRAY_FILTRE_ANNEEPROMOTION)}})}function miseAJourDuFiltreEcole(centralien_ID,anneePromotion_ID,secteur_ID,pays_ID,ville_ID){jsRoutes.controllers.ServiceEcole.AJAX_listeDesEcolesSelonCriteres(HTML(CHECKBOX_HISTORIQUE_ID).checked,centralien_ID?centralien_ID:"",anneePromotion_ID?anneePromotion_ID:"",secteur_ID?secteur_ID:"",pays_ID?pays_ID:"",ville_ID?ville_ID:"").ajax({async:false,success:function(data,textStatus,jqXHR){miseAJourDuFiltre_AJAXSuccess(data,ARRAY_FILTRE_ECOLE)}})}function miseAJourDuFiltreEntreprise(centralien_ID,anneePromotion_ID,secteur_ID,pays_ID,ville_ID){jsRoutes.controllers.ServiceEntreprise.AJAX_listeDesEntreprisesSelonCriteres(HTML(CHECKBOX_HISTORIQUE_ID).checked,centralien_ID?centralien_ID:"",anneePromotion_ID?anneePromotion_ID:"",secteur_ID?secteur_ID:"",pays_ID?pays_ID:"",ville_ID?ville_ID:"").ajax({async:false,success:function(data,textStatus,jqXHR){miseAJourDuFiltre_AJAXSuccess(data,ARRAY_FILTRE_ENTREPRISE)}})}function miseAJourDuFiltreSecteur(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,pays_ID,ville_ID){jsRoutes.controllers.ServiceSecteur.AJAX_listeDesSecteursSelonCriteres(centralien_ID?centralien_ID:"",anneePromotion_ID?anneePromotion_ID:"",ecole_ID?ecole_ID:"",entreprise_ID?entreprise_ID:"",pays_ID?pays_ID:"",ville_ID?ville_ID:"").ajax({async:false,success:function(data,textStatus,jqXHR){miseAJourDuFiltre_AJAXSuccess(data,ARRAY_FILTRE_SECTEUR)}})}function miseAJourDuFiltrePays(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID){jsRoutes.controllers.ServicePays.AJAX_listeDesPaysSelonCriteres(centralien_ID?centralien_ID:"",anneePromotion_ID?anneePromotion_ID:"",ecole_ID?ecole_ID:"",entreprise_ID?entreprise_ID:"",secteur_ID?secteur_ID:"").ajax({async:false,success:function(data,textStatus,jqXHR){miseAJourDuFiltre_AJAXSuccess(data,ARRAY_FILTRE_PAYS)}})}function miseAJourDuFiltreVille(centralien_ID,anneePromotion_ID,ecole_ID,entreprise_ID,secteur_ID,pays_ID){if(HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex!=0){if(!HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID])){creation_filtreVille(pays_ID)}jsRoutes.controllers.ServiceVille.AJAX_listeDesVillesSelonCriteres(centralien_ID?centralien_ID:"",anneePromotion_ID?anneePromotion_ID:"",ecole_ID?ecole_ID:"",entreprise_ID?entreprise_ID:"",secteur_ID?secteur_ID:"",pays_ID?pays_ID:"").ajax({async:false,success:function(data,textStatus,jqXHR){miseAJourDuFiltre_AJAXSuccess(data,ARRAY_FILTRE_VILLE)}})}else if(HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID])){suppression_filtreVille()}}function suppression_filtreVille(){HTML('tableau_critere').removeChild(HTML('tr_ville'));ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION]=ORDRE_ACTIVATION_PAR_DEFAUT}