package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Impiegato
{
	private int					idImpiegato;
    private String				CF;
    private String				nome;
    private String				cognome;
    private String				genere;
    private LocalDate			dataNascita;
    private String				comuneNascita; //Implementare oggetto Comune
    private String				email;
    private String 				password;
    private String				grado;   //Implementare Grado grado
    private ArrayList<Skill>	listaSkill = new ArrayList<Skill>();
    private List<CoppiaProgettiRuolo<Progetto, Ruolo>> ruoliProgetti = new ArrayList<>();
    private ArrayList<Riunione> riunioni = new ArrayList<>();
    private ArrayList<Valutazione> valutazioni = new ArrayList<>();


    public Impiegato(String CF)
    {
        this.CF = CF;
    }
    
    public Impiegato() {
    	
    }
    
    public ArrayList<Skill> getListaSkill() {
		return listaSkill;
	}

	public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }


    public String getCF()
    {
        return CF;
    }

    public void setCF(String CF)
    {
        this.CF = CF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(String comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
	public int getIdImpiegato() {
		return idImpiegato;
	}

	public void setIdImpiegato(int idImpiegato) {
		this.idImpiegato = idImpiegato;
	}

    @Override
    public String toString()
    {
        return cognome + " " + nome;
    	/*String s = getCF() + " " +
    			   getCognome() + " " +
    			   getComuneNascita() + " " +
    			   getEmail() + " " +
    			   getGenere() + " " +
    			   getGrado()  + " " +
    			   getNome() + " " +
    			   getPassword() + " " +
    			   getDataNascita();
    	
    	for(Skill sk: getListaSkill()) {
    		s = s + " " + sk.toString(); 
    	}
    	
    	return s;*/
        	   
    }
}
