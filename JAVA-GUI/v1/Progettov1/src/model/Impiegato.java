package model;

import java.util.ArrayList;
import java.util.Date;

public class Impiegato
{
    private int					IdImpiegato;
    private String				CF;
    private String				nome;
    private String				cognome;
    private String				genere;
    private Date				dataNascita;
    private String				comuneNascita;
    private String				email;
    private String 				password;
    private String				grado;
    private ArrayList<Skill>	listaSkill = new ArrayList<Skill>();
    
    //private int idGrado; le associazioni in java non si risolvono come le associazioni nei DB,
    //					   invece di 'int idGrado' mettiamo direttamente 'String grado';

    public Impiegato(String CF) {
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

    public int getIdImpiegato() {
        return IdImpiegato;
    }
    

    public void setIdImpiegato(int IdImpiegato) {
        this.IdImpiegato = IdImpiegato;
    }


    public String getCF()
    {
        /* metodo da implemantare */


        return CF;
    }

    public void setCF(String CF)
    {
        /* metodo da implemantare */

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

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
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
}
