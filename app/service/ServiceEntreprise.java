package service;

import java.util.List;

import models.Entreprise;
import dao.EntrepriseDao;

public class ServiceEntreprise {

	public static List<Entreprise> listeDesEntreprises() {
		return EntrepriseDao.find.orderBy("nom asc").findList();
	}

}
