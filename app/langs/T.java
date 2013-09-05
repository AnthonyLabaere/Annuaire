package langs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import play.Play;
import play.mvc.Controller;

public class T extends Controller{
	
	private static TreeMapLang<String,Lang> langList = new TreeMapLang<String,Lang>();
	
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
	
	public static String ranslate(String str){
		String language = session().get("language");
		if(language==null)	return langList.get("en").get(str);
		else				return langList.get(language).get(str);
	}
}
