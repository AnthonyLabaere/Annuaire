package service;

import java.util.List;

import com.avaje.ebean.Expr;

import models.Pays;
import dao.PaysDao;
import dao.VilleDao;

public class PaysService {
	
	public static List<Pays> listeDesPays(){
		return PaysDao.find.orderBy("nom asc").findList();
	}
	
	public static Pays PaysDeNom(String nom){
		return PaysDao.find.where(Expr.eq("nom", nom)).findUnique();
	}

}
