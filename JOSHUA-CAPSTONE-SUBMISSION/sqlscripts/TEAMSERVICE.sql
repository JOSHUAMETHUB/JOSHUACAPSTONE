--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-05-18 18:44:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 49195)
-- Name: players; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.players (
    player_id integer NOT NULL,
    country character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    team_id integer
);


ALTER TABLE public.players OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 49194)
-- Name: players_player_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.players_player_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.players_player_id_seq OWNER TO postgres;

--
-- TOC entry 3336 (class 0 OID 0)
-- Dependencies: 216
-- Name: players_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.players_player_id_seq OWNED BY public.players.player_id;


--
-- TOC entry 215 (class 1259 OID 49183)
-- Name: teams; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teams (
    team_id integer NOT NULL,
    team_name character varying(255)
);


ALTER TABLE public.teams OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 49182)
-- Name: teams_team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.teams_team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teams_team_id_seq OWNER TO postgres;

--
-- TOC entry 3337 (class 0 OID 0)
-- Dependencies: 214
-- Name: teams_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.teams_team_id_seq OWNED BY public.teams.team_id;


--
-- TOC entry 3179 (class 2604 OID 49198)
-- Name: players player_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.players ALTER COLUMN player_id SET DEFAULT nextval('public.players_player_id_seq'::regclass);


--
-- TOC entry 3178 (class 2604 OID 49186)
-- Name: teams team_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teams ALTER COLUMN team_id SET DEFAULT nextval('public.teams_team_id_seq'::regclass);


--
-- TOC entry 3330 (class 0 OID 49195)
-- Dependencies: 217
-- Data for Name: players; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.players (player_id, country, first_name, last_name, team_id) FROM stdin;
9	PHILIPPINES	Son	Goku	1
10	PHILIPPINES	Kaneki	Ken	1
11	PHILIPPINES	Ichigo	Kurosaki	1
7	USA	Jack	Sparrow	2
13	USA	Russel	Sauli	2
16	USA	Kylie	Jenner	2
17	USA	Sasuke	Uchiha	2
12	USA	Uncle	Ben	2
37	USA	John	Doe	3
38	USA	John	Doe	3
39	USA	John	Doe	3
40	USA	John	Doe	3
42	CHINA	TEST	DOE	3
43	CHINA	TEST	DOE	3
15	PHILIPPINES	Joshua	Salimbao	1
18	PHILIPPINES	Saitama	Panot	1
\.


--
-- TOC entry 3328 (class 0 OID 49183)
-- Dependencies: 215
-- Data for Name: teams; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.teams (team_id, team_name) FROM stdin;
1	KEPKEP
3	test_team
2	\N
\.


--
-- TOC entry 3338 (class 0 OID 0)
-- Dependencies: 216
-- Name: players_player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.players_player_id_seq', 43, true);


--
-- TOC entry 3339 (class 0 OID 0)
-- Dependencies: 214
-- Name: teams_team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.teams_team_id_seq', 16, true);


--
-- TOC entry 3183 (class 2606 OID 49202)
-- Name: players players_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.players
    ADD CONSTRAINT players_pkey PRIMARY KEY (player_id);


--
-- TOC entry 3181 (class 2606 OID 49188)
-- Name: teams teams_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teams
    ADD CONSTRAINT teams_pkey PRIMARY KEY (team_id);


--
-- TOC entry 3184 (class 2606 OID 49203)
-- Name: players fk5nglidr00c4dyybl171v6kask; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.players
    ADD CONSTRAINT fk5nglidr00c4dyybl171v6kask FOREIGN KEY (team_id) REFERENCES public.teams(team_id);


-- Completed on 2023-05-18 18:44:29

--
-- PostgreSQL database dump complete
--

