/****************************************************************************

	This is a web application developed for the ACCENTS club from the
	Ecole Centrale de Nantes aiming to facilitate contact between travelling
	students.
	
    Copyright (C) 2013  Malik Olivier Boussejra

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.

******************************************************************************/

package langs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import play.Play;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Major class that manages translation processes.
 * Just use T.ranslate("foo")
 * Lang is a class for which each instance is a locale for the application
 * (fr or en for example)
 * @author malik
 *
 */
public class T extends Controller{
	//Links language abbreviation String to the actual Lang object.
	private static TreeMapLang<String,Lang> langList = new TreeMapLang<String,Lang>();
	
	/**
	 * This must be run when the server starts.
	 * It takes all the file stored in the localization folder and load them
	 * into memory.
	 * The English language ("en") must be present.
	 * Other languages present in the directory will be added to the application
	 * @throws IOException id "en" is not found
	 */
	public static void init() throws IOException{
		String dirPath = Play.application().configuration().getString("loc.fol");
		File dir = new File(dirPath);
		ArrayList<String> filenames = new ArrayList<String>(Arrays.asList(dir.list()));
		if(!filenames.contains("en"))	throw new IOException("Couldn't find the main file named 'en'");
		Lang tempLang = new Lang(dirPath+"/en");
		filenames.remove("en");
		for(String filename : filenames){
			langList.put(filename, new Lang(dirPath+"/"+filename,tempLang));
		}
		langList.put("en",new Lang(tempLang));
	}
	
	/**
	 * Translate the string str into the language stored in session
	 * @param str
	 * @return translated string
	 */
	public static String ranslate(String str){
		String language = session().get("language");
		if(language==null)	return langList.get("en").get(str);
		else				return langList.get(language).get(str);
	}
	
	/**
	 * Change language.
	 * Used via Ajax in main.scala.html template.
	 * @param language
	 * @return
	 */
	public static Result setLanguage(String language){
		if(langList.containsValue(language)){
			session("language",language);
			return ok("Locale set to: "+language);
		}
		else	return badRequest("The local wasn't changed as this language does not exist: "+language);
	}
	
	/**
	 * Returns the link to change the locale on the web page.
	 * Edit there if you want to change that html string.
	 * @return
	 */
	public static String displayLanguages(){
		String disp = "<p>Change language: ";
		for(String key : langList.keySet()){
			disp+="<span class=\"button\" onClick=\"changeLocale('"+key+"')\">"+key.toUpperCase()+"</span> ";
		}
		disp+="</p>";
		return disp;
	}
}
