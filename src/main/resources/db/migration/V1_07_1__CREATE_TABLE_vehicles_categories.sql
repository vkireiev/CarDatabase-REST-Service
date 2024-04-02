CREATE TABLE vehicles_categories (
    vehicle_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (vehicle_id, category_id),
    CONSTRAINT vehicles_categories_vehicle_id_fkey FOREIGN KEY(vehicle_id) REFERENCES vehicles(id),
    CONSTRAINT vehicles_categories_category_id_fkey FOREIGN KEY(category_id) REFERENCES categories(id)
);
