package utilities;

import java.time.LocalDate;

public class MetodiComuni {
	
	public int controlloStringa(String check, String string) {
		if(check.isBlank())
    		return 1;
    	else {
    		if(!(check.matches(string)))
    			return 2;
    		if(check.equals(string))
    			return 3;
    	}
		
		return 0;
	}
	
	public int controlloPassword(String password, int lunghezza) {
		if(password.isBlank())
    		return 1;
    	else {
    		if(password.length() < lunghezza)
    			return 2;
    	}
		
		return 0;
	}
	
	public int controlloData(LocalDate check, LocalDate dataSupportata) {
		if(check == null)
			return 1;
		else {
			if(dataSupportata != null) {
				if(check.isAfter(dataSupportata))
					return 2;
				
				if(check.isBefore(dataSupportata))
					return 3;
			} else
				return 4;
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
