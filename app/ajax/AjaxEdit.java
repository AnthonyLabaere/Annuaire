package ajax;

import help.Parse;
import models.Person;
import models.School;
import play.mvc.Controller;
import play.mvc.Result;

public class AjaxEdit extends Controller{

	public static Result save(String arg, String value){
		Person profile = Person.find.byId(session("uid"));
		if(profile==null)	return unauthorized();
		switch(arg){
		case "name":
			profile.name=value;
			break;
		case "surname":
			profile.surname=value;
			break;
		case "birthday":
			profile.birthday=Parse.parseDateHyphen(value);
			break;
		case "mail":
			profile.mail=value;
			break;
		case "phone":
			profile.phone=value;
			break;
		case "skype":
			profile.skype=value;
			break;
		case "school":
			profile.schoolOfOrigin=School.find.byId(Parse.parseIntSchool(value));
			break;
		default :
			return badRequest("Haven't found specified argument");
		}
		profile.save();
		return ok("Edited with success");
	}
}
