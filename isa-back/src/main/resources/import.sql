INSERT INTO isadb."user" (city, company_information, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession) VALUES ('Novi Sad', 'Gaming INC', 'Serbia', 'slobodanmilosevic@gmail.com', true, 'Slobodan', 'Milosevic', '2023-10-01 21:58:58.508-07', '$2a$10$81IYoYJ.5JaYyImNOZMz0OSx9h1SH9BUz4HEURVx5dM7Us9yEmOde', '+381 123 312123', 'Gamer');
INSERT INTO isadb."user" (city, company_information, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession) VALUES ('Novi Sad', 'Gaming INC', 'Serbia', 'petarpetrovic@gmail.com', true, 'Petar', 'Petrovic', '2023-10-01 21:58:58.508-07', '$2a$10$81IYoYJ.5JaYyImNOZMz0OSx9h1SH9BUz4HEURVx5dM7Us9yEmOde', '+381 123 312123', 'Gamer');
INSERT INTO isadb."user" (city, company_information, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession) VALUES ('Novi Sad', 'Gaming INC', 'Serbia', 'jovanajovanovic@gmail.com', true, 'Jovana', 'Jovanovic', '2023-10-01 21:58:58.508-07', '$2a$10$81IYoYJ.5JaYyImNOZMz0OSx9h1SH9BUz4HEURVx5dM7Us9yEmOde', '+381 123 312123', 'Gamer');

INSERT INTO isadb."role" (name) VALUES ('ROLE_USER');
INSERT INTO isadb."role" (name) VALUES ('ROLE_SYSADMIN');
INSERT INTO isadb."role" (name) VALUES ('ROLE_COMPADMIN');

INSERT INTO isadb."user_role" (user_id, role_id) VALUES (1, 1);
INSERT INTO isadb."user_role" (user_id, role_id) VALUES (2, 2);
INSERT INTO isadb."user_role" (user_id, role_id) VALUES (3, 3);

INSERT INTO isadb."address" (city, country, street, zip_code) VALUES ('Novi Sad', 'Serbia', 'Bulevar Oslobodjenja 12', 21000);
INSERT INTO isadb."address" (city, country, street, zip_code) VALUES ('Novi Sad', 'Serbia', 'Bulevar Oslobodjenja 12', 21000);
INSERT INTO isadb."address" (city, country, street, zip_code) VALUES ('Novi Sad', 'Serbia', 'Bulevar Oslobodjenja 12', 21000);

INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('Gaming INC', 'Gaming company', 4.5, 1);

INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Playstation 5', 'Console', 'Playstation 5', 500);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Xbox Series X', 'Console', 'Xbox Series X', 500);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Nintendo Switch', 'Console', 'Nintendo Switch', 300);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Playstation 4', 'Console', 'Playstation 4', 300);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Bandage pack x10', 'Bandages', 'Bandages', 100);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Bandage pack x50', 'Bandages', 'Bandages', 400);

INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (1, 1);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (1, 2);