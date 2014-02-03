package service;

import java.util.List;

import models.Personne;
import dao.PersonneDao;

public class PersonneService {

	public static List<Personne> listeDesPersonnes() {
		return PersonneDao.find.orderBy("nom asc").findList();
	}

}
