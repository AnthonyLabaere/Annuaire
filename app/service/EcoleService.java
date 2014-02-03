package service;

import java.util.List;

import models.Ecole;
import dao.EcoleDao;

public class EcoleService {

	public static List<Ecole> listeDesEcoles() {
		return EcoleDao.find.orderBy("nom asc").findList();
	}

}
