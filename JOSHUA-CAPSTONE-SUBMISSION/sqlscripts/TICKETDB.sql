--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-05-18 18:46:24

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
-- TOC entry 215 (class 1259 OID 41056)
-- Name: matches; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.matches (
    match_id integer NOT NULL,
    date_time timestamp without time zone,
    participants_id character varying(255),
    teams_id character varying(255),
    field_id integer,
    tournament_id integer
);


ALTER TABLE public.matches OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 41055)
-- Name: matches_match_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.matches_match_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.matches_match_id_seq OWNER TO postgres;

--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 214
-- Name: matches_match_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.matches_match_id_seq OWNED BY public.matches.match_id;


--
-- TOC entry 217 (class 1259 OID 41065)
-- Name: sportfields; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sportfields (
    f_id integer NOT NULL,
    f_capacity integer,
    f_address character varying(255),
    f_name character varying(255)
);


ALTER TABLE public.sportfields OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 41064)
-- Name: sportfields_f_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sportfields_f_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sportfields_f_id_seq OWNER TO postgres;

--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 216
-- Name: sportfields_f_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sportfields_f_id_seq OWNED BY public.sportfields.f_id;


--
-- TOC entry 219 (class 1259 OID 41074)
-- Name: tickets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tickets (
    ticket_id integer NOT NULL,
    customer_name character varying(255),
    ticket_price real,
    match_id integer
);


ALTER TABLE public.tickets OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 41073)
-- Name: tickets_ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tickets_ticket_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tickets_ticket_id_seq OWNER TO postgres;

--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 218
-- Name: tickets_ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tickets_ticket_id_seq OWNED BY public.tickets.ticket_id;


--
-- TOC entry 221 (class 1259 OID 41081)
-- Name: tournaments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tournaments (
    tournament_id integer NOT NULL,
    sports_category character varying(255),
    tournament_name character varying(255),
    tournament_style character varying(255)
);


ALTER TABLE public.tournaments OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 41080)
-- Name: tournaments_tournament_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tournaments_tournament_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tournaments_tournament_id_seq OWNER TO postgres;

--
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 220
-- Name: tournaments_tournament_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tournaments_tournament_id_seq OWNED BY public.tournaments.tournament_id;


--
-- TOC entry 3188 (class 2604 OID 41059)
-- Name: matches match_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.matches ALTER COLUMN match_id SET DEFAULT nextval('public.matches_match_id_seq'::regclass);


--
-- TOC entry 3189 (class 2604 OID 41068)
-- Name: sportfields f_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sportfields ALTER COLUMN f_id SET DEFAULT nextval('public.sportfields_f_id_seq'::regclass);


--
-- TOC entry 3190 (class 2604 OID 41077)
-- Name: tickets ticket_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets ALTER COLUMN ticket_id SET DEFAULT nextval('public.tickets_ticket_id_seq'::regclass);


--
-- TOC entry 3191 (class 2604 OID 41084)
-- Name: tournaments tournament_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tournaments ALTER COLUMN tournament_id SET DEFAULT nextval('public.tournaments_tournament_id_seq'::regclass);


--
-- TOC entry 3346 (class 0 OID 41056)
-- Dependencies: 215
-- Data for Name: matches; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.matches (match_id, date_time, participants_id, teams_id, field_id, tournament_id) FROM stdin;
18	2024-02-01 08:00:00	\N	1, 3	14	7
19	2024-02-01 08:00:00	10, 7, 9	\N	14	7
\.


--
-- TOC entry 3348 (class 0 OID 41065)
-- Dependencies: 217
-- Data for Name: sportfields; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sportfields (f_id, f_capacity, f_address, f_name) FROM stdin;
14	10000	Squatters area 51, Elm Street , Tondo	KALYE
\.


--
-- TOC entry 3350 (class 0 OID 41074)
-- Dependencies: 219
-- Data for Name: tickets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tickets (ticket_id, customer_name, ticket_price, match_id) FROM stdin;
16	JOSHUA V SALIMBAO	2000	18
17	JANE V SALIMBAO	2000	18
18	J V SALIMBAO	2000	19
19	Jane V SALIMBAO	2000	19
\.


--
-- TOC entry 3352 (class 0 OID 41081)
-- Dependencies: 221
-- Data for Name: tournaments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tournaments (tournament_id, sports_category, tournament_name, tournament_style) FROM stdin;
7	PATINTERO	DUMAAN PATAY	DEATHMATCH
\.


--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 214
-- Name: matches_match_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.matches_match_id_seq', 19, true);


--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 216
-- Name: sportfields_f_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sportfields_f_id_seq', 14, true);


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 218
-- Name: tickets_ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tickets_ticket_id_seq', 19, true);


--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 220
-- Name: tournaments_tournament_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tournaments_tournament_id_seq', 7, true);


--
-- TOC entry 3193 (class 2606 OID 41063)
-- Name: matches matches_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.matches
    ADD CONSTRAINT matches_pkey PRIMARY KEY (match_id);


--
-- TOC entry 3195 (class 2606 OID 41072)
-- Name: sportfields sportfields_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sportfields
    ADD CONSTRAINT sportfields_pkey PRIMARY KEY (f_id);


--
-- TOC entry 3197 (class 2606 OID 41079)
-- Name: tickets tickets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_pkey PRIMARY KEY (ticket_id);


--
-- TOC entry 3199 (class 2606 OID 41088)
-- Name: tournaments tournaments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tournaments
    ADD CONSTRAINT tournaments_pkey PRIMARY KEY (tournament_id);


--
-- TOC entry 3200 (class 2606 OID 41089)
-- Name: matches fk5iumr5ro1hjxtn68sp1gnhkik; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.matches
    ADD CONSTRAINT fk5iumr5ro1hjxtn68sp1gnhkik FOREIGN KEY (field_id) REFERENCES public.sportfields(f_id);


--
-- TOC entry 3202 (class 2606 OID 41099)
-- Name: tickets fkd7hrhwnu3y4qifjp75htxnlbi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT fkd7hrhwnu3y4qifjp75htxnlbi FOREIGN KEY (match_id) REFERENCES public.matches(match_id);


--
-- TOC entry 3201 (class 2606 OID 41094)
-- Name: matches fkeeniokyjgo5k6rmhjujatn27i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.matches
    ADD CONSTRAINT fkeeniokyjgo5k6rmhjujatn27i FOREIGN KEY (tournament_id) REFERENCES public.tournaments(tournament_id);


-- Completed on 2023-05-18 18:46:25

--
-- PostgreSQL database dump complete
--

