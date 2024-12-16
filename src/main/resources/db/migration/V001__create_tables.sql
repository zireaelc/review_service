-- V001__create_tables.sql

-- Таблица category
CREATE TABLE category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Таблица subcategory
CREATE TABLE subcategory (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category_id UUID
);

-- Таблица review
CREATE TABLE review (
    id UUID PRIMARY KEY,
    text TEXT,
    rating INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    subcategory_id UUID
);

-- Таблица users
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Добавление внешних ключей
ALTER TABLE subcategory ADD CONSTRAINT subcategory_category_id_fkey FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE review ADD CONSTRAINT review_subcategory_id_fkey FOREIGN KEY (subcategory_id) REFERENCES subcategory(id);

-- Добавление уникальных индексов
CREATE UNIQUE INDEX category_name_unique ON category(name);
CREATE UNIQUE INDEX subcategory_name_unique ON subcategory(name);
CREATE UNIQUE INDEX users_username_unique ON users(username);
CREATE UNIQUE INDEX users_email_unique ON users(email);