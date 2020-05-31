CREATE TABLE users
(
    id           serial       NOT NULL,
    nick_name    varchar(100) NOT NULL,
    email        varchar(100) NOT NULL,
    hash_pwd     varchar(255) NOT NULL,
    last_connect timestamp    NOT NULL,
    avatar_url   varchar(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE likes
(
    id            serial      NOT NULL,
    id_users_from integer     NOT NULL,
    id_users_to   integer     NOT NULL,
    type_action   varchar(50) NOT NULL,
    CONSTRAINT likes_pkey PRIMARY KEY (id),
    CONSTRAINT likes_type_action_check CHECK ((((type_action)::text = 'like'::text) OR
                                               ((type_action)::text = 'dislike'::text))),
    CONSTRAINT likes_id_users_from_fkey FOREIGN KEY (id_users_from) REFERENCES users (id),
    CONSTRAINT likes_id_users_to_fkey FOREIGN KEY (id_users_to) REFERENCES users (id)
);

CREATE TABLE messages
(
    id            serial    NOT NULL,
    id_messages   int       not null,
    message       varchar(400),
    datetime_send timestamp NOT NULL,
    id_users_from integer   NOT NULL,
    id_users_to   integer   NOT NULL,
    CONSTRAINT messages_pkey PRIMARY KEY (id),
    CONSTRAINT likes_id_users_from_fkey FOREIGN KEY (id_users_from) REFERENCES users (id),
    CONSTRAINT likes_id_users_to_fkey FOREIGN KEY (id_users_to) REFERENCES users (id)
);