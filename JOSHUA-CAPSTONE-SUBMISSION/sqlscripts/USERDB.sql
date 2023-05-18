--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-05-18 18:46:48

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

--
-- TOC entry 214 (class 1259 OID 49250)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 49256)
-- Name: token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.token (
    id integer NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token character varying(255),
    token_type character varying(255),
    user_id integer,
    token_string character varying(255)
);


ALTER TABLE public.token OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 49264)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    user_email character varying(255),
    user_password character varying(255),
    role character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 49263)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3339 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 3178 (class 2604 OID 49267)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3331 (class 0 OID 49256)
-- Dependencies: 215
-- Data for Name: token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.token (id, expired, revoked, token, token_type, user_id, token_string) FROM stdin;
46	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb3NodWFAZ21haWwuY29tIiwiaWF0IjoxNjg0MTcxMDI5LCJleHAiOjE2ODQ3NzU4Mjl9.4MkuMA0or1IxFOAPJZ8vxBsHpWLJAj8wxlpYAN5b24I	BEARER	17	\N
47	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1AZ21haWwuY29tIiwiaWF0IjoxNjg0MTcxMDQ0LCJleHAiOjE2ODQ3NzU4NDR9.NL6YViZH8Gqmi6rJeMAz5BVby86Yg0hlkaAkPCCU4ns	BEARER	18	\N
48	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1AZ21haWwuY29tIiwiaWF0IjoxNjg0MjE5NjM2LCJleHAiOjE2ODQ4MjQ0MzZ9.5J4Ah7PyQRFauIsy4D_7rTK2Daqg5kUwAfFNXhvlhv4	BEARER	18	\N
49	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1AZ21haWwuY29tIiwiaWF0IjoxNjg0MjU1NDI5LCJleHAiOjE2ODQ4NjAyMjl9.SoK1boAoCQo8DrRkM0AapDXFFdZXEnpFJ7Bu-jACF2k	BEARER	18	\N
50	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb3NodWFAZ21haWwuY29tIiwiaWF0IjoxNjg0MjU5MTg2LCJleHAiOjE2ODQ4NjM5ODZ9.-uvGGje1sfkiCgxDUPAeR_2SVT5scBsfNyxvOtSHCl8	BEARER	17	\N
51	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1AZ21haWwuY29tIiwiaWF0IjoxNjg0MjU5Njk5LCJleHAiOjE2ODQ4NjQ0OTl9.HxRVscUw8Ot14KkWrs3sZ2ZYB1pbJZM6CBRqgKLUmBI	BEARER	18	\N
52	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1AZ21haWwuY29tIiwiaWF0IjoxNjg0MjkxNzY3LCJleHAiOjE2ODQ4OTY1Njd9.zBlMPYkJhbazPLUQ1ZwqMSUY5ZPxIf6md_O0GhLgSes	BEARER	18	\N
53	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb3NodWFAZ21haWwuY29tIiwiaWF0IjoxNjg0MzM4ODk0LCJleHAiOjE2ODQ5NDM2OTR9.h6kUiu-usNEsPcXEBRdxGumvEpvVlsBZCS9pmhX3PgM	BEARER	17	\N
54	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lQGdtYWlsLmNvbSIsImlhdCI6MTY4NDMzOTEzNCwiZXhwIjoxNjg0OTQzOTM0fQ.gA_U5vF-zQSsVcPOhMmTFPTmR8BYV7fmePiI4D__XMU	BEARER	19	\N
55	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lQGdtYWlsLmNvbSIsImlhdCI6MTY4NDMzOTE1NSwiZXhwIjoxNjg0OTQzOTU1fQ.F4HzvqyTg4vT7zB_ax9lqSHTeA8tBjx79EAEHVdVDCc	BEARER	19	\N
56	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lQGdtYWlsLmNvbSIsImlhdCI6MTY4NDM3ODMzOSwiZXhwIjoxNjg0Mzc4Mzk5fQ.6hZqbQPkGncGXqHWnrGtkNWUQiu_-3SGpCBsSKA-ZrE	BEARER	19	\N
57	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lQGdtYWlsLmNvbSIsImlhdCI6MTY4NDM5NTY3MCwiZXhwIjoxNjg1MDAwNDcwfQ.cQegvFy2_AdhKg5kT8GuYz6RKRGwZyjzmd5l9nydXnE	BEARER	19	\N
58	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lQGdtYWlsLmNvbSIsImlhdCI6MTY4NDM5NTc0NCwiZXhwIjoxNjg0Mzk1ODA0fQ.I0QNOv22syZdUlZFJkXjSy5xPjGiDgdSR8TA6ZE_VIU	BEARER	19	\N
59	t	t	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBnbWFpbC5jb20iLCJpYXQiOjE2ODQzOTU5MDYsImV4cCI6MTY4NTAwMDcwNn0.UJraiWk1jO5yqemxHHh8ZF8_auh7mnvRwR3pgfT0PdI	BEARER	21	\N
60	f	f	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lQGdtYWlsLmNvbSIsImlhdCI6MTY4NDM5NTk1MiwiZXhwIjoxNjg1MDAwNzUyfQ.dAxCUEA1cbUF3R73WvGGj-HUaXZT3l_8xY12ZTBitSQ	BEARER	19	\N
\.


--
-- TOC entry 3333 (class 0 OID 49264)
-- Dependencies: 217
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, user_email, user_password, role) FROM stdin;
17	joshua@gmail.com	$2a$10$Lr.4RsKGeAtmdWSFvEgUf.pHM/fezhHG6VVGwXiU62lcBZgOQRIRi	USER
18	kim@gmail.com	$2a$10$S1flY8xFAEJdBi2sBWJhjOpJGCuyYvdjbnX9sJc/kGENgX5JK04Pe	ADMIN
19	jane@gmail.com	$2a$10$EESOPm5K5Ex6ZwFJxKQ39.CbDqFgrZZF.YISfAms38Ea2Jim0wT2W	ADMIN
20	john@gmail.com	$2a$10$YQk9OUEaGExcCkCxDnJVX.tsJJBAVS2S/RB6QStMfgmRvpJ3yTJhK	USER
21	user1@gmail.com	$2a$10$6Q3.dLgZmLQTHyOe9bFsx.r0ybHW3XSPtAY4UjqJRygnb5C8G3D2u	USER
\.


--
-- TOC entry 3340 (class 0 OID 0)
-- Dependencies: 214
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 60, true);


--
-- TOC entry 3341 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 21, true);


--
-- TOC entry 3180 (class 2606 OID 49262)
-- Name: token token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);


--
-- TOC entry 3182 (class 2606 OID 49496)
-- Name: token uk_e9qf4aya8mk7fs0t2mry6h7yf; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT uk_e9qf4aya8mk7fs0t2mry6h7yf UNIQUE (token_string);


--
-- TOC entry 3184 (class 2606 OID 49273)
-- Name: token uk_pddrhgwxnms2aceeku9s2ewy5; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT uk_pddrhgwxnms2aceeku9s2ewy5 UNIQUE (token);


--
-- TOC entry 3186 (class 2606 OID 49271)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3187 (class 2606 OID 49274)
-- Name: token fkj8rfw4x0wjjyibfqq566j4qng; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT fkj8rfw4x0wjjyibfqq566j4qng FOREIGN KEY (user_id) REFERENCES public.users(user_id);


-- Completed on 2023-05-18 18:46:49

--
-- PostgreSQL database dump complete
--

