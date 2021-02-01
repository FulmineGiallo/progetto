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
			
			if(check.isBefore(dataDiOggi))
				return 3;
			
			if(dataSupportata != null && check.isAfter(dataSupportata))
				return 4;
			
			if(dataSupportata != null && check.isBefore(dataSupportata))
				return 5;
		}
		
		return 0;
	}
	
	public String getMessaggioErrore(Exception errore) {
		String dettagliErrore = "";
		
    	if(errore != null) {	
    		dettagliErrore = errore.toString() + "\n";
    		
			for(StackTraceElement STE: errore.getStackTrace())
				dettagliErrore = dettagliErrore + "at " + STE.toString() + "\n";
			
    	} else {
    		dettagliErrore = "Nessun dettaglio da mostrare";
    	}
    	
    	return dettagliErrore;
	}
}
