insert into users (id, password, first_name, last_name, email, user_role, created_at, updated_at)
values (2, '$2a$12$GsclbUlaGVPFoGj3IsT0te1j9QE4sjO7y4NjrAnWcWFFXbRBVfQnu', 'Vasya', 'Humus', 'humus_vasya@gmail.com', 'USER', now(), now());
insert into users (id, password, first_name, last_name, email, user_role, created_at, updated_at)
values (3, '$2a$12$GsclbUlaGVPFoGj3IsT0te1j9QE4sjO7y4NjrAnWcWFFXbRBVfQnu', 'Abignail', 'Hammer', 'a.hamm@gmail.com', 'USER', now(), now());
insert into users (id, password, first_name, last_name, email, user_role, created_at, updated_at)
values (4, '$2a$12$GsclbUlaGVPFoGj3IsT0te1j9QE4sjO7y4NjrAnWcWFFXbRBVfQnu', 'Gori', 'La', 'lagori@gmail.com', 'USER', now(), now());
insert into users (id, password, first_name, last_name, email, user_role, created_at, updated_at)
values (5, '$2a$12$GsclbUlaGVPFoGj3IsT0te1j9QE4sjO7y4NjrAnWcWFFXbRBVfQnu', 'Momo', 'Brown', 'momob@gmail.com', 'USER', now(), now());
insert into users (id, password, first_name, last_name, email, user_role, created_at, updated_at)
values (6, '$2a$12$GsclbUlaGVPFoGj3IsT0te1j9QE4sjO7y4NjrAnWcWFFXbRBVfQnu', 'Viscoplast', 'Optima', 'opt_visco@gmail.com', 'USER', now(), now());

ALTER SEQUENCE users_id_seq RESTART WITH 7;

insert into phone_numbers (id, phone_number, user_id, created_at, updated_at)
values (1, '+341646515656', 2, now(), now());
insert into phone_numbers (id, phone_number, user_id, created_at, updated_at)
values (2, '+614641661461', 2, now(), now());
insert into phone_numbers (id, phone_number, user_id, created_at, updated_at)
values (3, '+16156841646', 4, now(), now());
insert into phone_numbers (id, phone_number, user_id, created_at, updated_at)
values (4, '+561065145415', 5, now(), now());
insert into phone_numbers (id, phone_number, user_id, created_at, updated_at)
values (5, '+651464196854', 5, now(), now());
insert into phone_numbers (id, phone_number, user_id, created_at, updated_at)
values (6, '+561968602559', 5, now(), now());

ALTER SEQUENCE phone_numbers_id_seq RESTART WITH 7;