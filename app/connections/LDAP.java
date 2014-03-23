/****************************************************************************

Copyright 2014 Anthony Labaere

Contributeurs : 
François Neber francois.neber@centraliens-nantes.net
Malik Olivier Boussejra malik.boussejra@centraliens-nantes.net
Anthony Labaere anthony.labaere@centraliens-nantes.net

Ce logiciel est un programme informatique ayant pour but de faciliter 
les contacts entre étudiants et diplômés de l'École Centrale Nantes 
à l'étranger comme en France.

Ce logiciel est régi par la licence CeCILL soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant 
donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
pris connaissance de la licence CeCILL et que vous en avez accepté les
termes.

******************************************************************************/

package connections;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import play.Play;

/**
 * Requêtes LDAP pour se connecter
 * Attention ! On ne peut pas se connecter à LDAP si on utilise le réseau Wifi de Centrale, il faut
 * être branché en Ethernet !
 * @author Admin
 *
 */
public class LDAP{
	
	private static String serveur = Play.application().configuration().getString("ldap.url");
	
	/**
	 * Vérifie que le login et le mot de passe sont corrects et sont ceux d'un professeur.
	 * @param login
	 * @param mdp
	 * @return VRAI si le login et le mot de passe sont corrects, FAUX sinon.
	 */
	public static boolean verification(String login, String mdp){
		if(Play.application().configuration().getString("developpeur.mode").equals("on") && login.startsWith("test")){
			return true;
		}
		Hashtable<String,String> properties = new Hashtable<String,String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		properties.put(Context.PROVIDER_URL, serveur);
		properties.put(Context.SECURITY_AUTHENTICATION, "simple");
		properties.put(Context.SECURITY_PRINCIPAL, "uid="+login+", ou=people, dc=ec-nantes, dc=fr");
		properties.put(Context.SECURITY_CREDENTIALS, mdp);
        try {
        	System.out.println("en cours d'identification...");
        	DirContext ctx = new InitialDirContext(properties);
        	System.out.println("identifie");
            ctx.close();
		    return true;
        } catch (NamingException e) {
        	System.out.println(e.getMessage());
            return false;
        }
	}
}
