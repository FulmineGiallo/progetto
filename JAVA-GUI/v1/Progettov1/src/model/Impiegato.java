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
    private int idgrado;
    private int idimpegato;
    private String grado;

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    private String password;

    public Impiegato( String CF)
    {
        this.CF = CF;
    }

    public int getIdgrado() {
        return idgrado;
    }

    public void setIdgrado(int idgrado) {
        this.idgrado = idgrado;
    }

    public int getIdimpegato() {
        return idimpegato;
    }
    

    public void setIdimpegato(int idimpegato) {
        this.idimpegato = idimpegato;
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
