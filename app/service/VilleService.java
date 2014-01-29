package service;

import java.util.List;

import models.Ville;
import dao.VilleDao;

public class VilleService {
	
    // TODO trier par ordre alphabétique
	public static List<Ville> listeDesVilles(){
		return VilleDao.find.orderBy("id desc").findList();
	}

    // TODO trier par ordre alphabétique
//	public static List<Ville> listeDesVillesDuPays(Pays pays){
//		return VilleDao.find.filter().findList().all();
//	}
}
