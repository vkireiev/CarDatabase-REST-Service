-- VehicleCategory
ALTER SEQUENCE IF EXISTS "categories_id_seq" RESTART WITH 1;
INSERT INTO public."categories" (id, name) VALUES(1, 'SUV');
INSERT INTO public."categories" (id, name) VALUES(2, 'Sedan');
INSERT INTO public."categories" (id, name) VALUES(3, 'Coupe');
INSERT INTO public."categories" (id, name) VALUES(4, 'Pickup');
INSERT INTO public."categories" (id, name) VALUES(5, 'Hatchback');
INSERT INTO public."categories" (id, name) VALUES(6, 'Convertible');
INSERT INTO public."categories" (id, name) VALUES(7, 'Van/Minivan');
INSERT INTO public."categories" (id, name) VALUES(8, 'Wagon');

ALTER SEQUENCE IF EXISTS "categories_id_seq" RESTART WITH 101;
