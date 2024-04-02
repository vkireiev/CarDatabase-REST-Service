CREATE TABLE models_model_years (
    model_id BIGINT NOT NULL,
    model_year_id BIGINT NOT NULL,
    PRIMARY KEY (model_id, model_year_id),
    CONSTRAINT models_model_years_model_id_fkey FOREIGN KEY(model_id) REFERENCES models(id),
    CONSTRAINT models_model_years_model_year_id_fkey FOREIGN KEY(model_year_id) REFERENCES model_years(id)
);
