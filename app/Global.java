import java.io.IOException;

import langs.T;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings{
	@Override
	public void onStart(Application app) {
		try {
			T.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
