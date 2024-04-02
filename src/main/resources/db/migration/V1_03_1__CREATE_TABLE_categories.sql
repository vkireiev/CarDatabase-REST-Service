CREATE TABLE categories (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(25) CONSTRAINT categories_name_length_check CHECK(LENGTH(name) >= 2 AND LENGTH(name) <= 25) NOT NULL
);

CREATE UNIQUE INDEX index_unique_categories_on_name ON categories(UPPER(name));
