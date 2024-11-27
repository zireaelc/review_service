-- V002__add_fkeys.sql

-- Добавление столбца category_id в таблицу subcategory
ALTER TABLE subcategory ADD COLUMN category_id UUID;

-- Добавление столбца subcategory_id в таблицу review
ALTER TABLE review ADD COLUMN subcategory_id UUID;

-- Добавление внешних ключей
ALTER TABLE subcategory ADD CONSTRAINT subcategory_category_id_fkey FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE review ADD CONSTRAINT review_subcategory_id_fkey FOREIGN KEY (subcategory_id) REFERENCES subcategory(id);