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
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('Actual Medical Facility', 'Gaming not allowed', 5, 2);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('MediTech Solutions', 'Leading provider of medical technology', 4.5, 1);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('HealthBridge Pharmaceuticals', 'Specializes in the development and manufacture of pharmaceuticals', 4.7, 2);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('LifeCare Hospitals', 'Network of hospitals providing high-quality healthcare', 4.6, 3);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('Vitalis Diagnostics', 'Provider of diagnostic services and medical imaging', 4.8, 4);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('BioHeal Laboratories', 'Biotechnology company focused on the development of new medical treatments', 4.9, 5);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('TheraMed Therapeutics', 'Develops and markets a wide range of therapeutic products', 4.4, 6);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('ProLife Clinics', 'Network of clinics providing a variety of healthcare services', 4.3, 7);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('GenomeGen Biotech', 'Specializes in genetic testing and personalized medicine', 4.7, 8);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('NeuroSphere Neurology', 'Provides comprehensive neurological services', 4.5, 9);
INSERT INTO isadb."company" (name, description, average_rating, address_id) VALUES ('CardioPure Heart Centers', 'Specializes in cardiovascular health', 4.6, 10);

INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Playstation 5', 'Console', 'Playstation 5', 500);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Xbox Series X', 'Console', 'Xbox Series X', 500);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Nintendo Switch', 'Console', 'Nintendo Switch', 300);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Playstation 4', 'Console', 'Playstation 4', 300);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Bandage pack x10', 'Bandages', 'Bandages', 100);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Bandage pack x50', 'Bandages', 'Bandages', 400);

INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (1, 1);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (1, 2);


INSERT INTO isadb."company_admin"(company_id, admin_id) VALUES (1, 3);
INSERT INTO isadb."company_admin"(company_id, admin_id) VALUES (1, 2);

INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (2, 1);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (2, 2);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (2, 3);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (2, 5);