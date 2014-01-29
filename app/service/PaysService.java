package service;

import java.util.List;

import models.Pays;
import dao.PaysDao;

public class PaysService {
	
    // TODO trier par ordre alphabétique
	public static List<Pays> listeDesPays(){
		return PaysDao.find.all();
	}

}
