-- V003__add_indexes.sql

-- Добавление уникальных индексов
CREATE UNIQUE INDEX category_name_unique ON category(name);
CREATE UNIQUE INDEX subcategory_name_unique ON subcategory(name);
CREATE UNIQUE INDEX users_username_unique ON users(username);
CREATE UNIQUE INDEX users_email_unique ON users(email);