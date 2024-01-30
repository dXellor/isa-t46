INSERT INTO isadb."user" (city, company_information, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession, pending_password_reset, penal_points) VALUES ('Novi Sad', 'Gaming INC', 'Serbia', 'slobodanmilosevic@gmail.com', true, 'Slobodan', 'Milosevic', '2023-10-01 21:58:58.508-07', '$2a$10$81IYoYJ.5JaYyImNOZMz0OSx9h1SH9BUz4HEURVx5dM7Us9yEmOde', '+381 123 312123', 'Gamer', false, 3);
INSERT INTO isadb."user" (city, company_information, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession, pending_password_reset, penal_points) VALUES ('Novi Sad', 'Gaming INC', 'Serbia', 'petarpetrovic@gmail.com', true, 'Petar', 'Petrovic', '2023-10-01 21:58:58.508-07', '$2a$10$81IYoYJ.5JaYyImNOZMz0OSx9h1SH9BUz4HEURVx5dM7Us9yEmOde', '+381 123 312123', 'Gamer', false, 0);
INSERT INTO isadb."user" (city, company_information, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession, pending_password_reset, penal_points) VALUES ('Novi Sad', 'Gaming INC', 'Serbia', 'jovanajovanovic@gmail.com', true, 'Jovana', 'Jovanovic', '2023-10-01 21:58:58.508-07', '$2a$10$81IYoYJ.5JaYyImNOZMz0OSx9h1SH9BUz4HEURVx5dM7Us9yEmOde', '+381 123 312123', 'Gamer', false, 0);
INSERT INTO isadb."user" (city, company_information, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession, pending_password_reset, penal_points) VALUES ('Novi Sad', 'Gaming INC', 'Serbia', 'nikola7simic@gmail.com', true, 'Nikola', 'Simic', '2023-10-01 21:58:58.508-07', '$2a$10$81IYoYJ.5JaYyImNOZMz0OSx9h1SH9BUz4HEURVx5dM7Us9yEmOde', '+381 123 312123', 'Gamer', false, 0);

INSERT INTO isadb."role" (name) VALUES ('ROLE_USER');
INSERT INTO isadb."role" (name) VALUES ('ROLE_SYSADMIN');
INSERT INTO isadb."role" (name) VALUES ('ROLE_COMPADMIN');

INSERT INTO isadb."user_role" (user_id, role_id) VALUES (1, 1);
INSERT INTO isadb."user_role" (user_id, role_id) VALUES (2, 2);
INSERT INTO isadb."user_role" (user_id, role_id) VALUES (3, 3);
INSERT INTO isadb."user_role" (user_id, role_id) VALUES (4, 1);

INSERT INTO isadb."address" (city, country, street, zip_code) VALUES ('Novi Sad', 'Serbia', 'Bulevar Oslobodjenja 12', 21000);
INSERT INTO isadb."address" (city, country, street, zip_code) VALUES ('Novi Sad', 'Serbia', 'Bulevar Oslobodjenja 12', 21000);
INSERT INTO isadb."address" (city, country, street, zip_code) VALUES ('Novi Sad', 'Serbia', 'Bulevar Oslobodjenja 12', 21000);

INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('Gaming INC', 'Gaming company', 4.5, 1, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('Actual Medical Facility', 'Gaming not allowed', 5, 2, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('MediTech Solutions', 'Leading provider of medical technology', 4.5, 1, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('HealthBridge Pharmaceuticals', 'Specializes in the development and manufacture of pharmaceuticals', 4.7, 2, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('BioHeal Laboratories', 'Biotechnology company focused on the development of new medical treatments', 4.9, 2, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('TheraMed Therapeutics', 'Develops and markets a wide range of therapeutic products', 4.4, 3, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('ProLife Clinics', 'Network of clinics providing a variety of healthcare services', 4.3, 3, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('GenomeGen Biotech', 'Specializes in genetic testing and personalized medicine', 4.7, 1, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('NeuroSphere Neurology', 'Provides comprehensive neurological services', 4.5, 1, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('CardioPure Heart Centers', 'Specializes in cardiovascular health', 4.6, 1, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('Tech Giants', 'Leading provider of technology solutions', 4.5, 1, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('HealthCare Plus', 'Specializes in healthcare services', 4.7, 2, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('EduTech Innovations', 'Leading provider of educational technology', 4.6, 3, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('Green Energy Solutions', 'Provider of renewable energy solutions', 4.8, 3, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('Foodie Restaurants', 'Network of restaurants providing high-quality food', 4.9, 3, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('AutoMoto Vehicles', 'Specializes in the development and manufacture of vehicles', 4.4, 1, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('Fashionista Clothing', 'Network of stores providing a variety of clothing items', 4.3, 1, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('TravelWorld Agencies', 'Specializes in travel and tourism', 4.7, 2, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('FitLife Gyms', 'Provides comprehensive fitness services', 4.5, 2, '08:00:00', '16:00:00');
INSERT INTO isadb."company" (name, description, average_rating, address_id, start_work, end_work) VALUES ('HomeDecor Interiors', 'Specializes in interior design and decoration', 4.6, 1, '08:00:00', '16:00:00');

INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Playstation 5', 'Console', 'Playstation 5', 500);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Xbox Series X', 'Console', 'Xbox Series X', 500);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Nintendo Switch', 'Console', 'Nintendo Switch', 300);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Playstation 4', 'Console', 'Playstation 4', 300);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Bandage pack x10', 'Bandages', 'Bandages', 100);
INSERT INTO isadb."equipment" (name, type, description, price) VALUES ('Bandage pack x50', 'Bandages', 'Bandages', 400);

INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (1, 1);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (1, 2);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (2, 1);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (2, 2);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (2, 3);
INSERT INTO isadb."company_equipment" (company_id, equipment_id) VALUES (2, 5);

INSERT INTO isadb."company_admin"(company_id, admin_id) VALUES (2, 3);
INSERT INTO isadb."company_admin"(company_id, admin_id) VALUES (2, 2);

INSERT INTO isadb."inventory" (equipment_id, count, company_id, version) VALUES (1, 10, 2, 0);
INSERT INTO isadb."inventory" (equipment_id, count, company_id, version) VALUES (2, 10, 2, 0);
INSERT INTO isadb."inventory" (equipment_id, count, company_id, version) VALUES (5, 40, 2, 0);
INSERT INTO isadb."inventory" (equipment_id, count, company_id, version) VALUES (6, 10, 2, 0);

-- predefined appointments
INSERT INTO isadb."reservation" (date_time, duration, status, admin_id, company_id, version) VALUES ('2024-02-02 11:29:20.376', 15, 0, 3, 1, 0);
INSERT INTO isadb."reservation" (date_time, duration, status, admin_id, company_id, version) VALUES ('2024-02-02 12:00:00.0', 20, 0, 3, 1, 0);
INSERT INTO isadb."reservation" (date_time, duration, status, admin_id, company_id, version) VALUES ('2024-02-02 12:00:00.0', 20, 0, 3, 2, 0);
INSERT INTO isadb."reservation" (date_time, duration, status, admin_id, company_id, version) VALUES ('2024-02-01 12:00:00.0', 20, 0, 3, 2, 0);

-- made reservations
INSERT INTO isadb."reservation" (date_time, duration, status, employee_id, admin_id, company_id, note, version) VALUES ('2023-02-12 12:00:00.0', 20, 1, 1, 3, 1, 'Call me 10min earlier pls', 0);
INSERT INTO isadb."reservation" (date_time, duration, status, employee_id, admin_id, company_id, note, version) VALUES ('2023-02-13 12:00:00.0', 20, 5, 1, 3, 1, 'Dgdgdgdgdg', 0);
INSERT INTO isadb."reservation" (date_time, duration, status, employee_id, admin_id, company_id, note, version) VALUES ('2022-02-14 12:00:00.0', 20, 4, 1, 3, 1, 'I am an old reservation', 0);
INSERT INTO isadb."reservation" (date_time, duration, status, employee_id, admin_id, company_id, note, version) VALUES ('2023-02-16 18:00:00.0', 20, 2, 1, 3, 1, 'Last month reservation', 0);
INSERT INTO isadb."reservation" (date_time, duration, status, employee_id, admin_id, company_id, note, version) VALUES ('2023-02-17 10:00:00.0', 40, 4, 1, 3, 1, 'Last week reservation', 0);

INSERT INTO isadb."reservation_item" (inventory_item_id, count, reservation_id) VALUES (1, 2, 5);
INSERT INTO isadb."reservation_item" (inventory_item_id, count, reservation_id) VALUES (2, 4, 5);
INSERT INTO isadb."reservation_item" (inventory_item_id, count, reservation_id) VALUES (3, 4, 5);
INSERT INTO isadb."reservation_item" (inventory_item_id, count, reservation_id) VALUES (4, 4, 6);

INSERT INTO isadb."reservation_item" (inventory_item_id, count, reservation_id) VALUES (3, 1, 7);
INSERT INTO isadb."reservation_item" (inventory_item_id, count, reservation_id) VALUES (3, 1, 8);
INSERT INTO isadb."reservation_item" (inventory_item_id, count, reservation_id) VALUES (3, 1, 9);
INSERT INTO isadb."reservation_item" (inventory_item_id, count, reservation_id) VALUES (3, 1, 9);
