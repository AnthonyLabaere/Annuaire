package service;

import java.util.List;

import models.Secteur;
import dao.SecteurDao;

public class ServiceSecteur {

	public static List<Secteur> listeDesSecteurs() {
		return SecteurDao.find.orderBy("nom asc").findList();
	}

}
