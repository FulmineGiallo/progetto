package model;

import java.util.Date;
import java.util.Scanner;

public class calcoloCF {

		
		String cognome;
		String nome;
		char sesso;
		int giornoDiNascita;
		int meseDiNascita;
		String annoDiNascita;
		String comune;
		//String luogo;
		
		
		public calcoloCF(String cognome, String nome, char sesso, int giornoDiNascita, int meseDiNascita,String annoDiNascita, String comune) {
			this.cognome = cognome.toUpperCase();
			this.nome = nome.toUpperCase();
			this.sesso = sesso;
			this.giornoDiNascita = giornoDiNascita;
			this.meseDiNascita = meseDiNascita;
			this.annoDiNascita = annoDiNascita;
			this.comune = comune.toUpperCase();

	    	
//			this.luogo = luogo.toUpperCase();
		}
		
		//FUNZIONE PER COMPORRE IL CODICE ASSOCIATO AL NOME
		public String check_nome(String controllo, int cont) {
	        final int dim = 3;
	        char data[] = new char[dim];
	        int data_pos = 0;
	        int num_cons = 0;
	        if(cont >= 4){
	            for(int i=0; i<controllo.length(); i++){
	                if(data_pos < dim){
	                    char c = controllo.charAt(i);
	                    if(isConsonant(c)){
	                        num_cons++;
	                        data[data_pos] = c;
	                        data_pos++;
	                    }
	                }
	            }
	            
	            if(num_cons<3) {
	            	for(int i=0; i<controllo.length(); i++){
	            	    char c = controllo.charAt(i);
	                    if(isVowel(c)){
	                        data[data_pos] = c;
	                        data_pos++;
	                        if(data_pos==3)
	                        	break;
	                    }
	            	}
	            }
	            
	        }
	        else if(cont <= 3) {
	        	check(controllo, data, data_pos);
	        }
	        return new String(data);
	    }
		
		private String check_cognome(String controllo) {
	        final int dim = 3;
	        char data[] = new char[dim];
	        int data_pos = 0;
	        check(controllo, data, data_pos);
	        return new String(data);
		}
		
		//FUNZIONE PER LA SCELTA DEI CARATTERI PER IL CODICE DEL COGNOME E IL CODICE DEL NOME
		private void check(String controllo, char data[], int k) {
			for(int i=0; i<controllo.length(); i++){
	            if(k<3){
	                char c = controllo.charAt(i);
	                if(isConsonant(c)){
	                    data[k] = c;
	                    k++;
	                }
	            }
	        }
	        for(int i=0; i<controllo.length(); i++){
	            if(k<3){
	                char c = controllo.charAt(i);
	                if(isVowel(c)){
	                    data[k] = c;
	                    k++;
	                }
	            }
	        }
	        for(int i=0; i<data.length; i++){
	            if(data[i] == '\u0000'){
	                data[i] = 'X';
	            }
	        }
		}
		
		
		//FUNZIONE PER CAPIRE SE IL CARATTERE INSERITO È UNA CONSONANTE
	    private boolean isConsonant(char lettera){
	        String consonant = "BCDFGHJKLMNPQRSTVWXYZ";
	        char cons[] = consonant.toCharArray();
	        for(int i=0; i<cons.length; i++){
	            if(cons[i] == lettera){
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    //FUNZIONE PER CAPIRE SE IL CARATTERE INSERITO È UNA VOCALE
	    private boolean isVowel(char lettera) {
	        String vowels = "AEIOU";
	        char vow[] = vowels.toCharArray();
	        for (int i = 0; i < vow.length; i++) {
	            if (vow[i] == lettera) {
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    //FUNZIONE PER CONTARE IL NUMERO DI CONSONANTI NELLA STRINGA
	    private int consonantCount(String controllo, int cont){
	        for(int i=0; i<controllo.length(); i++){
	            if(isConsonant(controllo.charAt(i))){
	                cont++;
	            }
	        }
	        return cont;
	    }
	    
	    //FUNZIONE PER COMPORRE IL CODICE ASSOCIATO AL GIORNO
	    private String check_giorno(int giorno, char sesso) {
	    	if(sesso == 'F') {
	    		return String.valueOf(giorno+ 40);
	    	}
	    	if(giorno < 10) {
	    		return "0"+String.valueOf(giorno);
	    	}
	    	return String.valueOf(giorno);
	    }
	    
	  //FUNZIONE PER COMPORRE IL CODICE ASSOCIATO AL MESE
	    private char check_mese(int mese) {
	    	switch(mese) {
	    		case 1:
	    			return 'A';
	    		case 2:
	    			return 'B';
	    		case 3:
	    			return 'C';
	    		case 4:
	    			return 'D';
	    		case 5:
	    			return 'E';
	    		case 6:
	    			return 'H';
	    		case 7:
	    			return 'L';
	    		case 8:
	    			return 'M';
	    		case 9:
	    			return 'P';
	    		case 10:
	    			return 'R';
	    		case 11:
	    			return 'S';
	    		case 12:
	    			return 'T';
				default:
					return 'X';
	    	}
	    }
	    
	    
	  //FUNZIONE PER SOSTITUIRE IL NUMERO DEL MESE IN PAROLE
	    private String stringMese(int mese) {
	    	switch(mese) {
	    		case 1:
	    			return "Gennaio";
	    		case 2:
	    			return "Febbraio";
	    		case 3:
	    			return "Marzo";
	    		case 4:
	    			return "Aprile";
	    		case 5:
	    			return "Maggio";
	    		case 6:
	    			return "Giugno";
	    		case 7:
	    			return "Luglio";
	    		case 8:
	    			return "Agosto";
	    		case 9:
	    			return "Settembre";
	    		case 10:
	    			return "Ottobre";
	    		case 11:
	    			return "Novembre";
	    		case 12:
	    			return "Dicembre";
				default:
					return " ";
	    	}
	    }
	    
	    
	    //FUNZIONE PER SOSTITUIRE IL CODICE DELLA PROVINCIA IN PAROLE
	    private String check_provincia(String provincia) {
	    	switch(provincia) {
	    		case "AVELLINO":
	    			return "A509";
	    		case "BENEVENTO":
	    			return "A783";
	    		case "CASERTA":
	    			return "B963";
	    		case "NAPOLI":
	    			return "F839";
	    		case "SALERNO":
	    			return "H703";
				default:
					return "Città non specificata";
	    	}
	    }
	    
	    //FUNZIONE PER SOSTITUIRE IL CARATTERE DEL SESSO IN PAROLE
	    private String check_sesso(char sesso) {
	    	switch(sesso) {
	    		case 'M':
	    			return "Uomo";
	    		case 'F':
	    			return "Donna";
				default:
					return "Sesso non specificato";
	    	}
	    }
	    
	    //FUNZIONE PER CALCOLARE IL CARATTERE DI CONTROLLO DEL CODICE FISCALE
	    public char check_lettera(String codiceFiscale) {
			int sommapari=0;
			int sommadispari=0;
			for (int i=1;i<codiceFiscale.length();i+=2) 
			{
				switch (codiceFiscale.toUpperCase().charAt(i)){
					case '0': {sommapari= sommapari+0;break;}
					case '1': {sommapari= sommapari+1;break;}
					case '2': {sommapari= sommapari+2;break;}
					case '3': {sommapari= sommapari+3;break;}
					case '4': {sommapari= sommapari+4;break;}
					case '5': {sommapari= sommapari+5;break;}
					case '6': {sommapari= sommapari+6;break;}
					case '7': {sommapari= sommapari+7;break;}
					case '8': {sommapari= sommapari+8;break;}
					case '9': {sommapari= sommapari+9;break;}
					case 'A': {sommapari= sommapari+0;break;}
					case 'B': {sommapari= sommapari+1;break;}
					case 'C': {sommapari= sommapari+2;break;}
					case 'D': {sommapari= sommapari+3;break;}
					case 'E': {sommapari= sommapari+4;break;}
					case 'F': {sommapari= sommapari+5;break;}
					case 'G': {sommapari= sommapari+6;break;}
					case 'H': {sommapari= sommapari+7;break;}
					case 'I': {sommapari= sommapari+8;break;}
					case 'J': {sommapari= sommapari+9;break;}
					case 'K': {sommapari= sommapari+10;break;}
					case 'L': {sommapari= sommapari+11;break;}
					case 'M': {sommapari= sommapari+12;break;}
					case 'N': {sommapari= sommapari+13;break;}
					case 'O': {sommapari= sommapari+14;break;}
					case 'P': {sommapari= sommapari+15;break;}
					case 'Q': {sommapari= sommapari+16;break;}
					case 'R': {sommapari= sommapari+17;break;}
					case 'S': {sommapari= sommapari+18;break;}
					case 'T': {sommapari= sommapari+19;break;}
					case 'U': {sommapari= sommapari+20;break;}
					case 'V': {sommapari= sommapari+21;break;}
					case 'W': {sommapari= sommapari+22;break;}
					case 'X': {sommapari= sommapari+23;break;}
					case 'Y': {sommapari= sommapari+24;break;}
					case 'Z': {sommapari= sommapari+25;break;}
				}
			}
			for (int i=0;i<codiceFiscale.length();i+=2) 
			{
				switch (codiceFiscale.toUpperCase().charAt(i)) {
					case '0': {sommadispari=sommadispari+1;break;}
					case '1': {sommadispari=sommadispari+0;break;}
					case '2': {sommadispari=sommadispari+5;break;}
					case '3': {sommadispari=sommadispari+7;break;}
					case '4': {sommadispari=sommadispari+9;break;}
					case '5': {sommadispari=sommadispari+13;break;}
					case '6': {sommadispari=sommadispari+15;break;}
					case '7': {sommadispari=sommadispari+17;break;}
					case '8': {sommadispari=sommadispari+19;break;}
					case '9': {sommadispari=sommadispari+21;break;}
					case 'A': {sommadispari=sommadispari+1;break;}
					case 'B': {sommadispari=sommadispari+0;break;}
					case 'C': {sommadispari=sommadispari+5;break;}
					case 'D': {sommadispari=sommadispari+7;break;}
					case 'E': {sommadispari=sommadispari+9;break;}
					case 'F': {sommadispari=sommadispari+13;break;}
					case 'G': {sommadispari=sommadispari+15;break;}
					case 'H': {sommadispari=sommadispari+17;break;}
					case 'I': {sommadispari=sommadispari+19;break;}
					case 'J': {sommadispari=sommadispari+21;break;}
					case 'K': {sommadispari=sommadispari+2;break;}
					case 'L': {sommadispari=sommadispari+4;break;}
					case 'M': {sommadispari=sommadispari+18;break;}
					case 'N': {sommadispari=sommadispari+20;break;}
					case 'O': {sommadispari=sommadispari+11;break;}
					case 'P': {sommadispari=sommadispari+3;break;}
					case 'Q': {sommadispari=sommadispari+6;break;}
					case 'R': {sommadispari=sommadispari+8;break;}
					case 'S': {sommadispari=sommadispari+12;break;}
					case 'T': {sommadispari=sommadispari+14;break;}
					case 'U': {sommadispari=sommadispari+16;break;}
					case 'V': {sommadispari=sommadispari+10;break;}
					case 'W': {sommadispari=sommadispari+22;break;}
					case 'X': {sommadispari=sommadispari+25;break;}
					case 'Y': {sommadispari=sommadispari+24;break;}
					case 'Z': {sommadispari=sommadispari+23;break;}
				}
			}
			int indice = (sommapari+sommadispari)%26;
			char carattere;
			switch (indice) {
				case 0:{carattere='A';break;}
				case 1:{carattere='B';break;}
				case 2:{carattere='C';break;}
				case 3:{carattere='D';break;}
				case 4:{carattere='E';break;}
				case 5:{carattere='F';break;}
				case 6:{carattere='G';break;}
				case 7:{carattere='H';break;}
				case 8:{carattere='I';break;}
				case 9:{carattere='J';break;}
				case 10:{carattere='K';break;}
				case 11:{carattere='L';break;}
				case 12:{carattere='M';break;}
				case 13:{carattere='N';break;}
				case 14:{carattere='O';break;}
				case 15:{carattere='P';break;}
				case 16:{carattere='Q';break;}
				case 17:{carattere='R';break;}
				case 18:{carattere='S';break;}
				case 19:{carattere='T';break;}
				case 20:{carattere='U';break;}
				case 21:{carattere='V';break;}
				case 22:{carattere='W';break;}
				case 23:{carattere='X';break;}
				case 24:{carattere='Y';break;}
				case 25:{carattere='Z';break;}
				default:{carattere='.';break;}
			}
			
			return carattere;
		}

		@Override
		public String toString() {
			
			String cf = check_cognome(cognome)+check_nome(nome, nome.length())+ annoDiNascita.substring(annoDiNascita.length() - 2)  + check_mese(meseDiNascita) + check_giorno(giornoDiNascita, sesso) + comune;
			
			return cf+check_lettera(cf);
		}
	    
	    
	}
		

