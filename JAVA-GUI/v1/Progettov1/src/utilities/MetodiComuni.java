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
	
	public int controlloOrario(int oreInizio, int minutiInizio,
							   int oreFine,   int minutiFine)
	{
		if(oreFine < oreInizio)
			return 1;
		else if (oreFine == oreInizio) {
			if(minutiFine < minutiInizio)
				return 2;
			if(minutiFine == minutiInizio)
				return 3;
		}
		
		return 0;
	}
	
	public String getOrario(int ore, int minuti) {
		String orario;
		
		if(ore >= 0 && ore <= 9)
			orario = "0" + String.valueOf(ore);
		else
			orario = String.valueOf(ore);
		
		orario = orario + ":";
		
		if(minuti >= 0 && minuti <= 9)
			orario = orario + "0" + String.valueOf(minuti);
		else
			orario = orario + String.valueOf(minuti);
		
		return orario;
	}
}
