-- V1__create_tables.sql

-- Таблица category
CREATE TABLE category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Таблица subcategory
CREATE TABLE subcategory (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    category_id UUID,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Таблица review
CREATE TABLE review (
    id UUID PRIMARY KEY,
    text TEXT,
    rating INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    subcategory_id UUID,
    FOREIGN KEY (subcategory_id) REFERENCES subcategory(id)
);

-- Таблица users
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);