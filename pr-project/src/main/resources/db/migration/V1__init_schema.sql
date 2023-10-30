CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    password varchar(100)  NOT NULL,
    first_name varchar(100)  NOT NULL,
    last_name varchar(100)  NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    user_role varchar NOT NULL DEFAULT 1,
    created_at timestamp  NOT NULL,
    updated_at timestamp  NOT NULL
    );

CREATE TABLE IF NOT EXISTS phone_numbers (
    id SERIAL PRIMARY KEY,
    phone_number varchar(50) NOT NULL,
    user_id integer NOT NULL,
    created_at timestamp  NOT NULL,
    updated_at timestamp  NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
    );
