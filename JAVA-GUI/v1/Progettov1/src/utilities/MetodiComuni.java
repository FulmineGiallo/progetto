package utilities;

import java.time.LocalDate;

public class MetodiComuni {
	
	public int controlloStringaPattern(String check, String pattern) {
		if(check.isBlank())
    		return 1;
    	else {
    		if(!(check.matches(pattern)))
    			return 2;
    	}
		
		return 0;
	}
	
	public int controlloStringaUguale(String check, String uguale) {
		if(check.isBlank())
    		return 1;
    	else {
    		if(check.equals(uguale))
    			return 2;
    	}
		
		return 0;
	}
	
	public int controlloPassword(String password) {
		if(password.isBlank())
    		return 1;
    	else {
    		if(password.length() < 4)
    			return 2;
    	}
		
		return 0;
	}
	
	public int controlloData(LocalDate check, LocalDate dataDiOggi, LocalDate dataSupportata) {
		if(check == null)
			return 1;
		else {
			if(check.isAfter(dataDiOggi))
				return 2;
			
			if(dataSupportata != null && check.isAfter(dataSupportata))
				return 3;
		}
		
		return 0;
	}
}
