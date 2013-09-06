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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * An instance of this class contains the a language (English, French or anything else)
 * @author malik
 *
 */
class Lang {
	//the symbol of the language 
	private String name; 
	//All the localization data.
	//The key is the English expression. The value is the translated one.
	private TreeMap<String,String> texts = new TreeMap<String,String>();
	
	/**
	 * Load English language.
	 * The object instanced by this one will only be used to build all the
	 * instances in any language using the next constructors.
	 * @param path : string path of the file
	 */
	public Lang(String path){
		File lang = new File(path);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(lang));
			name="en";
			String input;
			Integer i = 0;
			while((input = br.readLine())!=null){
				texts.put(i.toString(), input);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates a Lang instance of any Language using the English
	 * temporary instance created by the above constructor.
	 * @param path : string path of the file
	 * @param tempEnglish : the Lang instance built by the above constructor
	 */
	public Lang(String path, Lang tempEnglish){
		File lang = new File(path);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(lang));
			name=lang.getName();
			String input;
			Integer i = 0;
			while((input = br.readLine())!=null){
				texts.put(tempEnglish.get(i.toString()), input);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Creates the English usable instance from the temporary instance so that it can be used for
	 * the application
	 * @param tempEnglish : the Lang instance built by the first constructor
	 */
	public Lang(Lang tempEnglish){
		name = "en";
		for(Map.Entry<String,String> entry : tempEnglish.texts.entrySet()){
			texts.put(entry.getValue(), entry.getValue());
		}
	}
	
	/**
	 * Translate the string in the language of the Lang object.
	 * @param str : to be translated string
	 * @return translated string
	 */
	public String get(String str){
		return texts.get(str);
	}
	/**
	 * Used in TreeMapLang to look for the language inside a TreeMap
	 */
	@Override
	public String toString(){
		return name;
	}
}
