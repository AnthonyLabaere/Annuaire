package help;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parse {
	
	public static Date parseDate(String date){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date_ = null;
		try {
			date_ = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date_;
	}
	
	public static Integer parseIntSchool(String school){
		return Integer.parseInt(school.substring(0,school.indexOf('.')));
	}
}
