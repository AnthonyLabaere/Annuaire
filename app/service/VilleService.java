package service;

import java.util.List;

import models.Pays;
import models.Ville;
import dao.VilleDao;

public class VilleService {
	
	public static List<Ville> listeDesVilles(){
		return VilleDao.find.orderBy("nom desc").findList();
	}

//	public static List<Ville> listeDesVillesDuPays(Pays pays){
//		return VilleDao.find.filter().findList().all();
//	}
}
