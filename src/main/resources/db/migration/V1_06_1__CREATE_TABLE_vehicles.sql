CREATE TABLE vehicles (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    object_id VARCHAR(10) NOT NULL,
    model_id BIGINT NOT NULL,
    model_year_id BIGINT NOT NULL,
    CONSTRAINT vehicles_models_model_id_fkey FOREIGN KEY(model_id) REFERENCES models(id),
    CONSTRAINT vehicles_model_years_model_year_id_fkey FOREIGN KEY(model_year_id) REFERENCES model_years(id)
);

CREATE UNIQUE INDEX index_unique_vehicles_on_name ON vehicles(UPPER(object_id));
