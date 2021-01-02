package model;

import java.util.Date;

public class Impiegato
{
    private String CF;
    private String nome;
    private String cognome;
    private String genere;
    private Date dataNascita;
    private String comuneNascita;
    private String email;
    private String passowrd;

    public Impiegato(String CF, String nome, String cognome, String genere, Date dataNascita, String comuneNascita, String email, String passowrd)
    {
        this.CF = CF;
        this.nome = nome;
        this.cognome = cognome;
        this.genere = genere;
        this.dataNascita = dataNascita;
        this.comuneNascita = comuneNascita;
        this.email = email;
        this.passowrd = passowrd;
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

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }
}
