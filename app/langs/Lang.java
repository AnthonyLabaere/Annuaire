package langs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Lang {

	private String name; 
	
	private TreeMap<String,String> texts = new TreeMap<String,String>();
	
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
	public Lang(Lang tempEnglish){
		name = "en";
		for(Map.Entry<String,String> entry : tempEnglish.texts.entrySet()){
			texts.put(entry.getValue(), entry.getValue());
		}
	}
	
	public String get(String str){
		return texts.get(str);
	}
	@Override
	public String toString(){
		return name;
	}
}
