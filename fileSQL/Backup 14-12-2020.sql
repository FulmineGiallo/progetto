--
-- PostgreSQL database dump
--

-- Dumped from database version 12.4
-- Dumped by pg_dump version 13.1

-- Started on 2020-12-14 23:20:30

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'ISO_8859_8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 217 (class 1255 OID 16629)
-- Name: inserimentodatacorrente(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserimentodatacorrente() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	NEW.DataV = current_date;
	RETURN NEW;
END
$$;


ALTER FUNCTION public.inserimentodatacorrente() OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16652)
-- Name: id_ambito_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_ambito_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_ambito_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 16515)
-- Name: ambito; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ambito (
    idambito integer DEFAULT nextval('public.id_ambito_seq'::regclass) NOT NULL,
    tipoambito character varying(255),
    CONSTRAINT cktipoambito CHECK (((tipoambito)::text = ANY ((ARRAY['Economica'::character varying, 'Medicina'::character varying, 'Informatica'::character varying, 'Fisica'::character varying, 'Matematica'::character varying, 'Chimica'::character varying, 'Biologia'::character varying, 'Musica'::character varying])::text[])))
);


ALTER TABLE public.ambito OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16530)
-- Name: compambiti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.compambiti (
    idprogetto integer,
    idambito integer
);


ALTER TABLE public.compambiti OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16447)
-- Name: compskill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.compskill (
    cf character varying(255) NOT NULL,
    idskill integer NOT NULL
);


ALTER TABLE public.compskill OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16655)
-- Name: id_progetto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.id_progetto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_progetto_seq OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16615)
-- Name: idriunioneseq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.idriunioneseq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.idriunioneseq OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16649)
-- Name: idvalutazioneseq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.idvalutazioneseq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.idvalutazioneseq OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16423)
-- Name: impiegato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.impiegato (
    nome character varying(30) NOT NULL,
    cognome character varying(30) NOT NULL,
    salario integer NOT NULL,
    cf character varying(16) NOT NULL,
    datan date NOT NULL,
    comunen character varying(30) NOT NULL,
    indirizzo character varying(50),
    email character varying(50) NOT NULL,
    grado character varying(30) NOT NULL,
    CONSTRAINT ckcodicefiscale CHECK (((cf)::text ~* '^[A-Z]{3}[A-Z]{3}[0-9]{2}[A-EHLMPR-T][0-9]{2}[A-Z][0-9]{3}[A-Z]$'::text)),
    CONSTRAINT ckcognome CHECK (((cognome)::text ~* '^[A-Za-z]+$'::text)),
    CONSTRAINT ckdatan CHECK ((date_part('year'::text, datan) <= (date_part('year'::text, CURRENT_DATE) - (18)::double precision))),
    CONSTRAINT ckemail CHECK (((email)::text ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'::text)),
    CONSTRAINT ckgrado CHECK (((grado)::text = ANY (ARRAY[('Dirigente'::character varying)::text, ('Vice-Dirigente'::character varying)::text, ('Stagista'::character varying)::text, ('CEO'::character varying)::text, ('Direttore Risorse Umane'::character varying)::text, ('Impiegato'::character varying)::text, ('Ricercatore'::character varying)::text]))),
    CONSTRAINT cknome CHECK (((nome)::text ~* '^[A-Za-z]+$'::text)),
    CONSTRAINT cksalario CHECK (((salario >= 0) AND (salario <= 15000)))
);


ALTER TABLE public.impiegato OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16483)
-- Name: progetto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.progetto (
    idprogetto integer DEFAULT nextval('public.id_progetto_seq'::regclass) NOT NULL,
    titolo character varying(255) NOT NULL,
    descrizione character varying(255),
    datainizio date,
    datafine date,
    scadenza date,
    tipoprogetto character varying(255),
    CONSTRAINT ckdatafine CHECK ((datafine > datainizio)),
    CONSTRAINT ckdatafineinizio CHECK ((datainizio < datafine)),
    CONSTRAINT ckdatainizio CHECK ((datainizio < scadenza)),
    CONSTRAINT ckprogetto CHECK (((tipoprogetto)::text = ANY ((ARRAY['Ricerca di Base'::character varying, 'Ricerca Industriale'::character varying, 'Ricerca Sperimentale'::character varying, 'Sviluppo Sperimentale'::character varying, 'Ricerca Medica'::character varying, 'Sperimentazione Fisica'::character varying, 'Sviluppo Software'::character varying, 'Ricerca Musicale'::character varying, 'Ricerca Economica'::character varying, 'Analisi Statistica'::character varying, 'Ricerca Ambientale'::character varying, 'Sperimentazione Chimica'::character varying, 'Ricerca Quantistica'::character varying, 'Sviluppo Algoritmi'::character varying])::text[]))),
    CONSTRAINT cktitolo CHECK (((titolo)::text ~* '^[A-Za-z]+$'::text))
);


ALTER TABLE public.progetto OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16630)
-- Name: registrazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registrazione (
    ruolo character varying(255),
    cf character varying(16),
    idambito integer,
    CONSTRAINT ckruolo CHECK (((ruolo)::text = ANY ((ARRAY['Project Manager'::character varying, 'Partecipante'::character varying, 'Programmatore'::character varying, 'Grafico'::character varying, 'Analista'::character varying, 'Tecnico'::character varying, 'Consulente'::character varying])::text[])))
);


ALTER TABLE public.registrazione OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16471)
-- Name: riunione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.riunione (
    idriunione integer DEFAULT nextval('public.idriunioneseq'::regclass) NOT NULL,
    orarioinizio timestamp without time zone,
    orariofine timestamp without time zone,
    data date,
    titolo character varying(30),
    descrizione character varying(100),
    CONSTRAINT ckdata CHECK ((date_part('year'::text, data) <= (date_part('year'::text, CURRENT_DATE) - (18)::double precision))),
    CONSTRAINT ckdatafine CHECK ((orariofine > orarioinizio)),
    CONSTRAINT cktitolo CHECK (((titolo)::text ~* '^[A-Za-z]+$'::text))
);


ALTER TABLE public.riunione OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16506)
-- Name: riunionefisica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.riunionefisica (
    sede character varying(20),
    piano character varying(3),
    nomestanza character varying(30),
    idriunionef integer
);


ALTER TABLE public.riunionefisica OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16498)
-- Name: riunionetelematica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.riunionetelematica (
    piattaforma character varying(20),
    codiceaccesso character varying(10),
    "idriunioneT" integer
);


ALTER TABLE public.riunionetelematica OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16438)
-- Name: skill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skill (
    idskill integer NOT NULL,
    descrizione character varying(1000),
    tiposkill character varying(10) NOT NULL,
    CONSTRAINT cktiposkill CHECK (((tiposkill)::text = ANY ((ARRAY['Soft-Skill'::character varying, 'Hard-Skill'::character varying])::text[])))
);


ALTER TABLE public.skill OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16543)
-- Name: valutazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.valutazione (
    idvalutazione integer DEFAULT nextval('public.idvalutazioneseq'::regclass) NOT NULL,
    recensione character varying(1000) NOT NULL,
    stelle integer NOT NULL,
    datav date DEFAULT '1940-01-01'::date NOT NULL,
    recensore character varying(16) NOT NULL,
    recensito character varying(16) NOT NULL,
    CONSTRAINT ckstelle CHECK (((stelle >= 0) AND (stelle <= 5)))
);


ALTER TABLE public.valutazione OWNER TO postgres;

--
-- TOC entry 3767 (class 2606 OID 16520)
-- Name: ambito ambito_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ambito
    ADD CONSTRAINT ambito_pkey PRIMARY KEY (idambito);


--
-- TOC entry 3757 (class 2606 OID 16573)
-- Name: impiegato impiegato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato
    ADD CONSTRAINT impiegato_pkey PRIMARY KEY (cf);


--
-- TOC entry 3765 (class 2606 OID 16491)
-- Name: progetto progetto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.progetto
    ADD CONSTRAINT progetto_pkey PRIMARY KEY (idprogetto);


--
-- TOC entry 3763 (class 2606 OID 16475)
-- Name: riunione riunione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riunione
    ADD CONSTRAINT riunione_pkey PRIMARY KEY (idriunione);


--
-- TOC entry 3759 (class 2606 OID 16446)
-- Name: skill skill_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill
    ADD CONSTRAINT skill_pkey PRIMARY KEY (idskill);


--
-- TOC entry 3761 (class 2606 OID 16451)
-- Name: compskill uniquecfskill; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compskill
    ADD CONSTRAINT uniquecfskill UNIQUE (cf, idskill);


--
-- TOC entry 3769 (class 2606 OID 16552)
-- Name: valutazione valutazione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.valutazione
    ADD CONSTRAINT valutazione_pkey PRIMARY KEY (idvalutazione);


--
-- TOC entry 3780 (class 2620 OID 16644)
-- Name: valutazione datavalutazione; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER datavalutazione BEFORE INSERT ON public.valutazione FOR EACH ROW EXECUTE FUNCTION public.inserimentodatacorrente();


--
-- TOC entry 3774 (class 2606 OID 16533)
-- Name: compambiti fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compambiti
    ADD CONSTRAINT fk1 FOREIGN KEY (idprogetto) REFERENCES public.progetto(idprogetto);


--
-- TOC entry 3778 (class 2606 OID 16634)
-- Name: registrazione fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registrazione
    ADD CONSTRAINT fk1 FOREIGN KEY (cf) REFERENCES public.impiegato(cf);


--
-- TOC entry 3775 (class 2606 OID 16538)
-- Name: compambiti fk2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compambiti
    ADD CONSTRAINT fk2 FOREIGN KEY (idambito) REFERENCES public.ambito(idambito);


--
-- TOC entry 3779 (class 2606 OID 16639)
-- Name: registrazione fk2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registrazione
    ADD CONSTRAINT fk2 FOREIGN KEY (idambito) REFERENCES public.ambito(idambito);


--
-- TOC entry 3771 (class 2606 OID 16575)
-- Name: compskill fkcodicefiscale; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compskill
    ADD CONSTRAINT fkcodicefiscale FOREIGN KEY (cf) REFERENCES public.impiegato(cf);


--
-- TOC entry 3776 (class 2606 OID 16663)
-- Name: valutazione fkrecensito; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.valutazione
    ADD CONSTRAINT fkrecensito FOREIGN KEY (recensito) REFERENCES public.impiegato(cf);


--
-- TOC entry 3777 (class 2606 OID 16658)
-- Name: valutazione fkrecensore; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.valutazione
    ADD CONSTRAINT fkrecensore FOREIGN KEY (recensore) REFERENCES public.impiegato(cf);


--
-- TOC entry 3770 (class 2606 OID 16457)
-- Name: compskill fkskill; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compskill
    ADD CONSTRAINT fkskill FOREIGN KEY (idskill) REFERENCES public.skill(idskill);


--
-- TOC entry 3773 (class 2606 OID 16509)
-- Name: riunionefisica riunionefisica_idriunionef_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riunionefisica
    ADD CONSTRAINT riunionefisica_idriunionef_fkey FOREIGN KEY (idriunionef) REFERENCES public.riunione(idriunione);


--
-- TOC entry 3772 (class 2606 OID 16501)
-- Name: riunionetelematica riunionetelematica_idriunione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riunionetelematica
    ADD CONSTRAINT riunionetelematica_idriunione_fkey FOREIGN KEY ("idriunioneT") REFERENCES public.riunione(idriunione);


--
-- TOC entry 3912 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM rdsadmin;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2020-12-14 23:20:38

--
-- PostgreSQL database dump complete
--

