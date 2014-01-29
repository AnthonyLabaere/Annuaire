package service;

import java.util.List;

import models.Pays;
import dao.PaysDao;

public class PaysService {
	
	public static List<Pays> listeDesPays(){
		return PaysDao.find.orderBy("nom asc").findList();
	}

}
