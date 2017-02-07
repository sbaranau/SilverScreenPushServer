-- password SU root
-- Table: public.notifications

-- DROP TABLE public.notifications;

CREATE TABLE public.notifications
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    films character varying(300) COLLATE pg_catalog."default",
    tikets character varying(300) COLLATE pg_catalog."default",
    startnotif bigint,
    endnotif bigint,
    wantrecieve boolean,
    morningSend boolean,
    filmreminder boolean,
    time bigint,
    login character varying COLLATE pg_catalog."default",
    CONSTRAINT uniq_login PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.notifications
    OWNER to postgres;

-- Table: public.phone

-- DROP TABLE public.phone;

CREATE TABLE public.phone
(
    data_id uuid NOT NULL,
    token character varying(200) COLLATE pg_catalog."default" NOT NULL,
    login character varying COLLATE pg_catalog."default",
    password character varying(50) COLLATE pg_catalog."default",
    system character varying(30) COLLATE pg_catalog."default",
    version character varying(5) COLLATE pg_catalog."default",
    date bigint,
    birth bigint,
    isman bigint,
    name character varying COLLATE pg_catalog."default",
    CONSTRAINT phone_pk PRIMARY KEY (data_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.phone
    OWNER to postgres;

-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
    username character varying(45) COLLATE pg_catalog."default" NOT NULL,
    password character varying(45) COLLATE pg_catalog."default" NOT NULL,
    enabled integer NOT NULL DEFAULT 1,
    CONSTRAINT users_pkey PRIMARY KEY (username)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;

-- Table: public.user_roles

-- DROP TABLE public.user_roles;
CREATE SEQUENCE public.user_id_seq
INCREMENT 1
START 2
MINVALUE 1
MAXVALUE 9223372036854775807
CACHE 1;

CREATE TABLE public.user_roles
(
    user_role_id smallint NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    username character varying(45) COLLATE pg_catalog."default" NOT NULL,
    role character varying(45) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id),
    CONSTRAINT user_roles_role_username_key UNIQUE (role, username)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.user_roles
    OWNER to postgres;