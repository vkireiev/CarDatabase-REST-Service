-- VehicleMake
ALTER SEQUENCE IF EXISTS "makes_id_seq" RESTART WITH 1;
INSERT INTO public."makes" (id, name) VALUES(1, 'Audi');
INSERT INTO public."makes" (id, name) VALUES(2, 'Chevrolet');
INSERT INTO public."makes" (id, name) VALUES(3, 'Cadillac');
INSERT INTO public."makes" (id, name) VALUES(4, 'Acura');
INSERT INTO public."makes" (id, name) VALUES(5, 'BMW');
INSERT INTO public."makes" (id, name) VALUES(6, 'GMC');
INSERT INTO public."makes" (id, name) VALUES(7, 'Honda');
INSERT INTO public."makes" (id, name) VALUES(8, 'Hyundai');
INSERT INTO public."makes" (id, name) VALUES(9, 'Jaguar');
INSERT INTO public."makes" (id, name) VALUES(10, 'Lexus');
INSERT INTO public."makes" (id, name) VALUES(11, 'Porsche');
INSERT INTO public."makes" (id, name) VALUES(12, 'Volvo');
INSERT INTO public."makes" (id, name) VALUES(13, 'Ford');
INSERT INTO public."makes" (id, name) VALUES(14, 'Lincoln');
INSERT INTO public."makes" (id, name) VALUES(15, 'Mercedes-Benz');
INSERT INTO public."makes" (id, name) VALUES(16, 'Volkswagen');
INSERT INTO public."makes" (id, name) VALUES(17, 'Bentley');
INSERT INTO public."makes" (id, name) VALUES(18, 'Ferrari');
INSERT INTO public."makes" (id, name) VALUES(19, 'INFINITI');
INSERT INTO public."makes" (id, name) VALUES(20, 'Rolls-Royce');
INSERT INTO public."makes" (id, name) VALUES(21, 'Toyota');
INSERT INTO public."makes" (id, name) VALUES(22, 'Subaru');
INSERT INTO public."makes" (id, name) VALUES(23, 'Saab');
INSERT INTO public."makes" (id, name) VALUES(24, 'Dodge');
INSERT INTO public."makes" (id, name) VALUES(25, 'HUMMER');
INSERT INTO public."makes" (id, name) VALUES(26, 'Nissan');

ALTER SEQUENCE IF EXISTS "makes_id_seq" RESTART WITH 101;


