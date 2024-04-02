CREATE TABLE models (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) CONSTRAINT models_name_length_check CHECK(LENGTH(name) > 0 AND LENGTH(name) <= 50) NOT NULL,
    make_id BIGINT NOT NULL,
    CONSTRAINT models_makes_make_id_fkey FOREIGN KEY(make_id) REFERENCES makes(id)
);

CREATE UNIQUE INDEX index_unique_models_on_make_id_and_name ON models(make_id, UPPER(name));
