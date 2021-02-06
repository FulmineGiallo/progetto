package model;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public Impiegato(String nome, String cognome)
    {
        setNome(nome);
        setCognome(cognome);
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
    	String nomeUpperCase = "";
    	
    	for(String s: nome.split("\s")) {
    		if (!s.isBlank()) {
	    		nomeUpperCase = nomeUpperCase 								+
	            				String.valueOf(s.charAt(0)).toUpperCase() 	+
	            				s.substring(1).toLowerCase() + " ";
    		}
    	}
    	
    	this.nome = nomeUpperCase.substring(0, nomeUpperCase.length()-1);
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
    	String cognomeUpperCase = "";
    	
    	for(String s: cognome.split("\s")) {
    		if (!s.isBlank()) {
				cognomeUpperCase = cognomeUpperCase 						 +
								   String.valueOf(s.charAt(0)).toUpperCase() +
								   s.substring(1).toLowerCase() + " ";
			}
    	}
    	
    	this.cognome = cognomeUpperCase.substring(0, cognomeUpperCase.length()-1);
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
    }
}
