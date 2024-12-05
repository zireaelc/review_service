-- V002__add_fkeys.sql

-- Добавление внешних ключей
ALTER TABLE subcategory ADD CONSTRAINT subcategory_category_id_fkey FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE review ADD CONSTRAINT review_subcategory_id_fkey FOREIGN KEY (subcategory_id) REFERENCES subcategory(id);