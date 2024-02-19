CREATE TABLE public.users
(
    "userId" integer NOT NULL,
    login varchar(100) NOT NULL,
    password varchar(64) NOT NULL,
    constraint users_id PRIMARY KEY ("userId")
);

CREATE TABLE public.task_status
(
    "statusId" integer NOT NULL,
    title varchar(64) NOT NULL,
    constraint status_id PRIMARY KEY ("statusId")
);

CREATE TABLE public.tasks
(
    "taskId" integer NOT NULL,
    title varchar(100) NOT NULL,
    userId integer NOT NULL,
    description varchar(1000),
    statusId integer NOT NULL,
    deadline date NULL,

    constraint tasks_id PRIMARY KEY ("taskId"),
    constraint tasks_fk_user_id foreign key (userId) references "public".users("userId"),
    constraint tasks_fk_status_id foreign key (statusId) references "public".task_status("statusId")

);

CREATE SEQUENCE public.sequence_task_id
    INCREMENT 1
    START 1
    MINVALUE 1
    OWNED BY tasks."taskId";

ALTER SEQUENCE public.sequence_task_id
    OWNER TO postgres;

CREATE SEQUENCE public.sequence_user_id
    INCREMENT 1
    START 1
    MINVALUE 1
    OWNED BY users."userId";

ALTER SEQUENCE public.sequence_user_id
    OWNER TO postgres;

