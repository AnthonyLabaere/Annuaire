package service;

import java.util.List;

import models.Secteur;
import dao.SecteurDao;

public class SecteurService {

	public static List<Secteur> listeDesSecteurs() {
		return SecteurDao.find.orderBy("nom asc").findList();
	}

}
