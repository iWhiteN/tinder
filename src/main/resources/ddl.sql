CREATE TABLE users (
	id serial NOT NULL,
	nick_name varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	hash_pwd varchar(255) NOT NULL,
	last_connect timestamp NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE likes (
	id serial NOT NULL,
	id_users_from serial NOT NULL,
	id_users_to serial NOT NULL,
	type_action varchar(50) NOT NULL,
	CONSTRAINT likes_pkey PRIMARY KEY (id),
	CONSTRAINT likes_type_action_check CHECK ((((type_action)::text = 'like'::text) AND ((type_action)::text = 'dislike'::text))),
	CONSTRAINT likes_id_users_from_fkey FOREIGN KEY (id_users_from) REFERENCES users(id),
	CONSTRAINT likes_id_users_to_fkey FOREIGN KEY (id_users_to) REFERENCES users(id)
);

CREATE TABLE messages (
	id serial NOT NULL,
	message varchar(400) NOT NULL,
	datetime_send timestamp NOT NULL,
	id_users serial NOT NULL,
	CONSTRAINT messages_pkey PRIMARY KEY (id),
	CONSTRAINT messages_id_users_fkey FOREIGN KEY (id_users) REFERENCES users(id)
);