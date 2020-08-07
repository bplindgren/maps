INSERT INTO maps.authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO maps.authority (name) VALUES ('ROLE_USER');

INSERT INTO maps.users (id, email, first_name, last_name, username, password_hash, created_by, created_date, last_modified_by, last_modified_date) VALUES 
    (1, 'brad@brad.com', 'brad', 'lindgren', 'admin', 'di2938d9s5di2938d9s5di2938d9s5di2938d9s5di2938d9s5di2938d9s5', 'brad', '2020-08-03 20:00:35.22', 'brad', '2020-08-03 20:00:35.22'),
    (2, 'user@user.com', 'user_fn', 'user_ln', 'user', 'di2938d9s5di2938d9s5di2938d9s5di2938d9s5di2938d9s5di2938d9s5', 'user', '2020-08-03 20:01:35.22', 'user', '2020-08-03 20:01:35.22');

INSERT INTO maps.user_authority (user_id_fk, authority_name) VALUES (1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER');