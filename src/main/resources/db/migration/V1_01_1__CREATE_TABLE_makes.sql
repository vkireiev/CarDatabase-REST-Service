CREATE TABLE makes (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(25) CONSTRAINT makes_name_length_check CHECK(LENGTH(name) >= 2 AND LENGTH(name) <= 25) NOT NULL
);

CREATE UNIQUE INDEX index_unique_makes_on_name ON makes(UPPER(name));
