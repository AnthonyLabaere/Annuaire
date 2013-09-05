package langs;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings({ "hiding", "serial" })
public class TreeMapLang<String,Lang> extends TreeMap<String,Lang>{
	@Override
	public boolean containsValue(Object o){
		for(Map.Entry<String,Lang> entry : this.entrySet()){
			if(entry.getValue().toString().equals(o))	return true;
		}
		return false;
	}

}
